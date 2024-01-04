package com.example.go;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ChoiceController {

  @FXML
  CheckBox PvC = new CheckBox();

  @FXML
  private void smallClicked() {
    MyLogger.logger.log(Level.INFO, "Small clicked!");

    initializeBoard(9);
  }

  @FXML
  private void mediumClicked() {
    MyLogger.logger.log(Level.INFO, "Medium clicked!");

    initializeBoard(13);
  }

  @FXML
  private void largeClicked() {
    MyLogger.logger.log(Level.INFO, "Large clicked");

    initializeBoard(19);
  }

  private void initializeBoard(int size) {
    try {
      MyLogger.logger.log(Level.INFO, "Initializing " + size + "x" + size + " board!");

      Socket socket = new Socket("localhost", 4444);
      String message = String.valueOf(size);
      MessageController.sendMessage(message, socket);

      FXMLLoader loader = new FXMLLoader(getClass().getResource("board-view.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = new Stage();

      stage.setOnCloseRequest(event -> {
        MessageController.sendMessage("BYE " + "none", socket);
      });

      stage.setTitle("Go");
      stage.setScene(scene);
      stage.show();

      Board controller = loader.getController();
      controller.initialize(size, socket);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}