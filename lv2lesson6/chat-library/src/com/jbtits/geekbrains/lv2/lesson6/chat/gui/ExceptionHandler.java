package com.jbtits.geekbrains.lv2.lesson6.chat.gui;

import javax.swing.*;

/**
 * @author Nikolay Zaytsev
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final JFrame window;

    private ExceptionHandler(JFrame window) {
        this.window = window;
    }

    public static void attach(JFrame window) {
        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler(window));
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        final StackTraceElement[] stackTrace = e.getStackTrace();
        final String message = String.format("Exception '%s' occurs with message '%s' %s",
                e.getClass().getSimpleName(), e.getMessage(), stackTrace[0]);
        final JOptionPane popup = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        final JDialog dialog = popup.createDialog(window, "Chat Server Error");
        dialog.setVisible(true);
        e.printStackTrace();
        System.exit(1);
    }
}
