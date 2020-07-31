package ru.gb.jtwo.chat.client;

import ru.gb.jtwo.chat.client.log.MessageLog;
import ru.gb.jtwo.chat.client.log.impl.FileMessageLog;
import ru.gb.jtwo.chat.common.Library;
import ru.gb.jtwo.chat.common.messages.BroadcastServerMessage;
import ru.gb.jtwo.chat.common.messages.base.CommandWithParameters;
import ru.gb.jtwo.network.SocketThread;
import ru.gb.jtwo.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, SocketThreadListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final int MESSAGES_HISTORY_LIMIT = 100;
    private static final String MANUAL_CHAT_INPUT_COMMAND_DELIMITER = " ";

    private final MessageLog history = new FileMessageLog();
    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top", true);
    private final JTextField tfLogin = new JTextField("ivan");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;
    private SocketThread socketThread;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }

    ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setAlwaysOnTop(true);
        userList.setListData(new String[]{"user1", "user2", "user3", "user4",
                "user5", "user6", "user7", "user8", "user9",
                "user-with-exceptionally-long-name-in-this-chat"});
        JScrollPane scrUser = new JScrollPane(userList);
        JScrollPane scrLog = new JScrollPane(log);
        scrUser.setPreferredSize(new Dimension(100, 0));
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setEditable(false);
        cbAlwaysOnTop.addActionListener(this);
        tfMessage.addActionListener(this);
        btnSend.addActionListener(this);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        panelBottom.setVisible(false);

        add(scrUser, BorderLayout.EAST);
        add(scrLog, BorderLayout.CENTER);
        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnLogin) {
            connect();
        } else if (src == btnDisconnect) {
            socketThread.close();
        } else {
            throw new RuntimeException("Unknown source:" + src);
        }
    }

    private void connect() {
        try {
            Socket socket = new Socket(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread(this, "Client", socket);
        } catch (IOException e) {
            showException(Thread.currentThread(), e);
        }
    }

    private void sendMessage() {
        String msg = tfMessage.getText();
        if ("".equals(msg)) return;
        tfMessage.setText(null);
        tfMessage.requestFocusInWindow();
        Library.parse(msg, MANUAL_CHAT_INPUT_COMMAND_DELIMITER).ifPresentOrElse(
                command -> socketThread.sendMessage(command.toCommandString()),
                () -> socketThread.sendMessage(Library.getBroadcastClient(msg))
        );
    }

    private void putLog(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(() -> {
            log.append(msg + System.lineSeparator());
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    private void showException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length > 0) {
            msg = String.format("Exception in \"%s\" %s: %s%n\tat %s",
                    t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
            JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        showException(t, e);
        System.exit(1);
    }

    /**
     * Socket Thread Listener methods
     * */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        // no-op
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("\nDisconnected\n");
        panelBottom.setVisible(false);
        panelTop.setVisible(true);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        panelBottom.setVisible(true);
        panelTop.setVisible(false);
        String login = tfLogin.getText();
        String password = new String(tfPassword.getPassword());
        thread.sendMessage(Library.getAuthRequest(login, password));
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        handleMessage(msg);
    }

    private void handleMessage(String msg) {
        Library.parse(msg).ifPresentOrElse(
                this::handleCommand,
                () -> showException(Thread.currentThread(),
                        new RuntimeException("Can't extract command from server message " + msg))
        );

    }

    private void handleCommand(CommandWithParameters command) {
        switch (command.getCommand()) {
            case BROADCAST_SERVER:
                final BroadcastServerMessage message = BroadcastServerMessage
                        .constructWithParams(command.getParams());
                history.insert(message);
                putLog(message.toPrettyString());
                break;
            case AUTH_ACCEPT:
                putLog("\nAuthorization success\n");
                final BroadcastServerMessage[] storedMessages = history.read(MESSAGES_HISTORY_LIMIT);
                for (BroadcastServerMessage storeMessage: storedMessages) {
                    putLog(storeMessage.toPrettyString());
                }
                break;
            case AUTH_DENIED:
                showException(Thread.currentThread(), new RuntimeException("Authorization denied"));
                break;
            case MSG_FORMAT_ERROR:
                showException(Thread.currentThread(), new RuntimeException("Server declined our command: " + command));
                break;
            default:
                showException(Thread.currentThread(), new RuntimeException("No handler for command: " + command));
        }
    }

    @Override
    public void onSocketException(SocketThread thread, Throwable throwable) {
        throwable.printStackTrace();
    }
}
