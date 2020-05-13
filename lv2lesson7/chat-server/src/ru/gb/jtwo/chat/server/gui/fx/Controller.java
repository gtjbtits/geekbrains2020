package ru.gb.jtwo.chat.server.gui.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import ru.gb.jtwo.chat.server.core.ChatServer;
import ru.gb.jtwo.chat.server.core.ChatServerListener;

public class Controller implements ChatServerListener {

    @FXML
    public TextArea taLog;

    private ChatServer server = new ChatServer(this);

    public void start(ActionEvent actionEvent) {
        server.start(8189);
    }

    public void stop(ActionEvent actionEvent) {
        server.stop();
    }

    public void drop(ActionEvent actionEvent) {
        server.dropAllClients();
    }

    @Override
    public void onChatServerMessage(String msg) {
        taLog.appendText(msg + "\n");
    }
}
