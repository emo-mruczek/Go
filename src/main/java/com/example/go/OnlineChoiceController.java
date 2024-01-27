package com.example.go;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class OnlineChoiceController {

  private static Socket socket;

  @FXML
  private void smallClicked() {
    MyLogger.logger.log(Level.INFO, "Small clicked!");

    initializeBoard(9, "FIRST");
  }

  @FXML
  private void mediumClicked() {
    MyLogger.logger.log(Level.INFO, "Medium clicked!");

    initializeBoard(13, "FIRST");
  }

  @FXML
  private void largeClicked() {
    MyLogger.logger.log(Level.INFO, "Large clicked");

    initializeBoard(19, "FIRST");
  }

  public void initialize(Socket socket) {
    this.socket = socket;
  }

 public void initializeBoard(int size, String player) {
    try {
      MyLogger.logger.log(Level.INFO, "Initializing " + size + "x" + size + " board!");

      if (Objects.equals(player, "FIRST")) {
        String message = String.valueOf(size);
        MessageController.sendMessage(message, socket);
      }
      System.out.println("Hi! I'm here and i am " + player);

      FXMLLoader loader = ScreenInitializer.initialize("online-board-view.fxml", "Go", socket);

      OnlineGameBoard controller = loader.getController();
      controller.initialize(size, socket, player);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
