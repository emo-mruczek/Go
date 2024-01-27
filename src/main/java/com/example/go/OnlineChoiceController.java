package com.example.go;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class OnlineChoiceController extends ChoiceController {
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
