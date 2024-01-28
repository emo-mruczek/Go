package com.example.go;

import javafx.fxml.FXML;
import java.net.Socket;
import java.util.logging.Level;

public abstract class ChoiceController {
  protected static Socket socket;

  @FXML
  protected void smallClicked() {
    MyLogger.logger.log(Level.INFO, "Small clicked!");
    initializeBoard(9, "FIRST");
  }

  @FXML
  protected void mediumClicked() {
    MyLogger.logger.log(Level.INFO, "Medium clicked!");
    initializeBoard(13, "FIRST");
  }

  @FXML
  protected void largeClicked() {
    MyLogger.logger.log(Level.INFO, "Large clicked");
    initializeBoard(19, "FIRST");
  }

  protected void initialize(Socket socket) {
    ChoiceController.socket = socket;
  }

  protected abstract void initializeBoard(int size, String player);

}
