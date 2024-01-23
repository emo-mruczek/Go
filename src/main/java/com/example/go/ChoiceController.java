package com.example.go;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class ChoiceController {
  @FXML
  Label status = new Label();

  private Boolean mode = false;
  private Socket socket;

  public void initialize(Socket socket) throws IOException, InterruptedException {
    this.socket = socket;
    String response = MessageController.receiveMessage(socket);

    if (Objects.equals(response, "SECOND")) {
      String size = MessageController.receiveMessage(socket);
      makeBoard(Integer.parseInt(size), false);
    }

  }

  @FXML
  private void recapClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Recap clicked!");

    initializeRecap();
  }

  @FXML
  CheckBox PvC = new CheckBox();

  @FXML
  private void smallClicked() throws IOException, InterruptedException {
    MyLogger.logger.log(Level.INFO, "Small clicked!");

    initializeBoard(9);
  }

  @FXML
  private void mediumClicked() throws IOException, InterruptedException {
    MyLogger.logger.log(Level.INFO, "Medium clicked!");

    initializeBoard(13);
  }

  @FXML
  private void largeClicked() throws IOException, InterruptedException {
    MyLogger.logger.log(Level.INFO, "Large clicked");

    initializeBoard(19);
  }

  private void initializeBoard(int size) throws IOException, InterruptedException {
      MyLogger.logger.log(Level.INFO, "Initializing " + size + "x" + size + " board!");

      String message = String.valueOf(size);
      MessageController.sendMessage(message, socket);


      makeBoard(size, true);
  }

  private void makeBoard(int size, boolean player) throws IOException, InterruptedException {

    if(player) {
      MessageController.sendMessage(String.valueOf(size), socket);
    }

    if (PvC.isSelected()) {
      mode = true;
    //  MessageController.sendMessage("PVC", socket);
      System.out.println("Jestem pvc");
    } else {
     // MessageController.sendMessage("PVP", socket);
      System.out.println("Jestem pvp");
    }


    FXMLLoader loader = new FXMLLoader(getClass().getResource("board-view.fxml"));
    Scene scene = new Scene(loader.load());
    Stage stage = new Stage();

    stage.setTitle("Go");
    stage.setScene(scene);
    stage.show();

    BoardGame controller = loader.getController();
    controller.initialize(size, mode, socket, player);
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