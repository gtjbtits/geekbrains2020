package com.jbtits.geekbrains.lv2.lesson6.chat.client.gui.components;

import com.jbtits.geekbrains.lv2.lesson6.chat.client.gui.listeners.MessageListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Nikolay Zaytsev
 */
public class InputTextArea extends ScrollableTextArea { // NOSONAR

    private final transient MessageListener[] listeners;

    public InputTextArea(int width, int height, MessageListener... listeners) {
        super(width, height, true);
        this.listeners = listeners;
        this.addKeyListener(new InputTextAreaEventHandler());
    }

    private class InputTextAreaEventHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (isSendKeysCombination(e)) {
                this.send();
            } else if (isNewLineKeysCombination(e)) {
                this.addNewLine();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (isSendKeysCombination(e)) {
                InputTextArea.this.setText("");
            }
        }

        private void addNewLine() {
            InputTextArea.this.setText(String.format("%s%n", InputTextArea.this.getText()));
        }

        private void send() {
            for (MessageListener listener: InputTextArea.this.listeners) {
                listener.onNewMessage(InputTextArea.this.getText());
            }
        }

        private boolean isSendKeysCombination(KeyEvent e) {
            return e.getKeyCode() == KeyEvent.VK_ENTER && !e.isShiftDown();
        }

        private boolean isNewLineKeysCombination(KeyEvent e) {
            return e.getKeyCode() == KeyEvent.VK_ENTER && e.isShiftDown();
        }
    }
}
