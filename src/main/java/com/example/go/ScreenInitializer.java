package com.example.go;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;

public class ScreenInitializer {

  public static FXMLLoader initialize(String type, String view, String title, Socket socket) throws IOException {
    MessageController.sendMessage(type, socket);
    FXMLLoader loader = new FXMLLoader(ScreenInitializer.class.getResource(view));
    Scene scene = new Scene(loader.load());
    Stage stage = new Stage();
    stage.setOnCloseRequest(event -> MessageController.sendMessage("BYE " + "none", socket));
    stage.setTitle(title);
    stage.setScene(scene);
    stage.show();
    return loader;
  }

  public static FXMLLoader initialize(String view, String title, Socket socket) throws IOException {
    FXMLLoader loader = new FXMLLoader(ScreenInitializer.class.getResource(view));
    Scene scene = new Scene(loader.load());
    Stage stage = new Stage();
    stage.setOnCloseRequest(event -> MessageController.sendMessage("BYE " + "none", socket));
    stage.setTitle(title);
    stage.setScene(scene);
    stage.show();
    return loader;
  }
}
