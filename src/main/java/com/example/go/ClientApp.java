package com.example.go;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ClientApp extends Application {
  @Override
  public void start(Stage stage) throws IOException, InterruptedException {
    MyLogger.logger.log(Level.INFO, "Starting an app");
    FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("start-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Hello UwU");
    stage.setScene(scene);
    stage.show();
    Socket socket = new Socket("localhost", 4444);
   // stage.setOnCloseRequest(event -> MessageController.sendMessage("BYE " + "none", socket));
    ChoiceController controller = fxmlLoader.getController();
    controller.initialize(socket);
  }

  public static void main(String[] args) {
    launch();
  }
}