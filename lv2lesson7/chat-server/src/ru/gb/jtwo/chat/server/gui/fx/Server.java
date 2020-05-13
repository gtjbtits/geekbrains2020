package ru.gb.jtwo.chat.server.gui.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Server extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("server.fxml"));
        primaryStage.setTitle("Chat Server");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }
}
