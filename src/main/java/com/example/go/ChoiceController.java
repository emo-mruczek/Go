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

  Boolean mode = false;

  @FXML
  private void recapClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Recap clicked!");

    initializeRecap();
  }

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

      if (PvC.isSelected()) {
        mode = true;
        MessageController.sendMessage("PVC", socket);
        System.out.println("Jestem pvc");
      } else {
        MessageController.sendMessage("PVP", socket);
        System.out.println("Jestem pvp");
      }

      FXMLLoader loader = new FXMLLoader(getClass().getResource("board-view.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = new Stage();

      stage.setOnCloseRequest(event -> MessageController.sendMessage("BYE " + "none", socket));

      stage.setTitle("Go");
      stage.setScene(scene);
      stage.show();

      BoardGame controller = loader.getController();
      controller.initialize(size, mode, socket);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeRecap() throws IOException {
    try {
      MyLogger.logger.log(Level.INFO, "Initializing list of previous games");

      Socket socket = new Socket("localhost", 4444);
      String message = "RECAP";
      MessageController.sendMessage(message, socket);

      FXMLLoader loader = new FXMLLoader(getClass().getResource("game-list-view.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = new Stage();

      stage.setOnCloseRequest(event -> MessageController.sendMessage("BYE " + "none", socket));

      stage.setTitle("List of games");
      stage.setScene(scene);
      stage.show();

      ListController controller = loader.getController();
      controller.initialize(socket);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}