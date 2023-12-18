package com.example.go;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    MyLogger.logger.log(Level.INFO, "Starting an app");
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}