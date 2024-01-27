package com.example.go;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class MainScreenFacade {

  Boolean mode = false;

  @FXML
  private void recapClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Recap clicked!");

    initializeRecap();
  }

  @FXML
  private void onlineClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Online clicked!");

    initializeOnline();
  }

  @FXML
  private void botClicked() {
    MyLogger.logger.log(Level.INFO, "Bot clicked!");

    initializeBot();
  }

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

      stage.setOnCloseRequest(event -> MessageController.sendMessage("BYE " + "none", socket));

      stage.setTitle("Go");
      stage.setScene(scene);
      stage.show();

      GameBoard controller = loader.getController();
      controller.initialize(size, socket);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeOnline() throws IOException {
    MyLogger.logger.log(Level.INFO, "Initializing online game!");

    Socket socket = new Socket("localhost", 4444);

    MessageController.sendMessage("ONLINE", socket);
    String whichPlayer = MessageController.receiveMessage(socket);

    if (Objects.equals(whichPlayer, "FIRST")) {
      MyLogger.logger.log(Level.INFO, "I'm FIRST!");

      FXMLLoader loader = new FXMLLoader(getClass().getResource("online-choice-view.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = new Stage();

      stage.setOnCloseRequest(event -> MessageController.sendMessage("BYE " + "none", socket));

      stage.setTitle("Choose mode");
      stage.setScene(scene);
      stage.show();

      OnlineChoiceController controller = loader.getController();
      controller.initialize(socket);

    } else {
      MyLogger.logger.log(Level.INFO, "I'm SECOND!");

      String size = MessageController.receiveMessage(socket);
      OnlineChoiceController ch = new OnlineChoiceController();
      ch.initialize(socket);
      ch.initializeBoard(Integer.parseInt(size), "SECOND");
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

  private void initializeBot() {
    try {
      MyLogger.logger.log(Level.INFO, "Beware! Initializing game with a computer!");

      Socket socket = new Socket("localhost", 4444);
      String message = "BOT";
      MessageController.sendMessage(message, socket);

      FXMLLoader loader = new FXMLLoader(getClass().getResource("bot-choice-view.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = new Stage();

      stage.setOnCloseRequest(event -> MessageController.sendMessage("BYE " + "none", socket));

      stage.setTitle("Choose mode");
      stage.setScene(scene);
      stage.show();

      BotChoiceController controller = loader.getController();
      controller.initialize(socket);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}