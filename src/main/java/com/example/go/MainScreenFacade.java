package com.example.go;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class MainScreenFacade {
  @FXML
  private void recapClicked() {
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

  private void initializeOnline() throws IOException {
    MyLogger.logger.log(Level.INFO, "Initializing online game!");

    Socket socket = new Socket("localhost", 4444);
    MessageController.sendMessage("ONLINE", socket);
    String whichPlayer = MessageController.receiveMessage(socket);
    if (Objects.equals(whichPlayer, "FIRST")) {
      MyLogger.logger.log(Level.INFO, "I'm FIRST!");
      FXMLLoader loader = ScreenInitializer.initialize("online-choice-view.fxml", "Choose mode", socket);
      OnlineChoiceController controller = loader.getController();
      controller.initialize(socket);
    } else {
      MyLogger.logger.log(Level.INFO, "I'm SECOND!");
      String size = MessageController.receiveMessage(socket);
      OnlineChoiceController ch = new OnlineChoiceController();
      ch.initialize(socket);
      assert size != null;
      ch.initializeBoard(Integer.parseInt(size), "SECOND");
    }
  }

  private void initializeBoard(int size) {
    try {
      MyLogger.logger.log(Level.INFO, "Initializing " + size + "x" + size + " board!");
      Socket socket = new Socket("localhost", 4444);
      String message = String.valueOf(size);
      FXMLLoader loader = ScreenInitializer.initialize(message, "board-view.fxml", "Go", socket);
      HotseatGameBoard controller = loader.getController();
      controller.initialize(size, socket);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeBot() {
    try {
      MyLogger.logger.log(Level.INFO, "Beware! Initializing game with a computer!");
      Socket socket = new Socket("localhost", 4444);
      FXMLLoader loader = ScreenInitializer.initialize("BOT", "bot-choice-view.fxml", "Choose mode", socket);
      BotChoiceController controller = loader.getController();
      controller.initialize(socket);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeRecap() {
    try {
      MyLogger.logger.log(Level.INFO, "Initializing list of previous games");
      Socket socket = new Socket("localhost", 4444);
      FXMLLoader loader = ScreenInitializer.initialize("RECAP", "game-list-view.fxml", "List of games", socket);
      ListController controller = loader.getController();
      controller.initialize(socket);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}