package com.example.go;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.logging.Level;

public class BotChoiceController extends ChoiceController {
  @Override
  public void initializeBoard(int size, String Player) {
    try {
      MyLogger.logger.log(Level.INFO, "Initializing " + size + "x" + size + " board!");
      String message = String.valueOf(size);
      FXMLLoader loader = ScreenInitializer.initialize(message, "bot-board-view.fxml", "Go", socket);
      BotGameBoard controller = loader.getController();
      controller.initialize(size, socket);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
