package com.example.go;

import javafx.fxml.FXML;

import java.util.Objects;
import java.util.logging.Level;

public class HotseatGameBoard extends GameBoard {
  int passes = 0;

  @Override
  @FXML
  protected void forfeitClicked() {
    MyLogger.logger.log(Level.INFO, "Forfeit clicked!");
    endGame();
  }

  @Override
  @FXML
  protected void passClicked() {
    Player = !Player;
    passes++;
    System.out.println(passes);
    String text = (Player) ? "Current player: Black" : "Current player: White";
    label.setText(text);
    MyLogger.logger.log(Level.INFO, "Player passed :(");
    if (passes > 1) {
      endGame();
    }
  }
  @Override
  protected void handleStoneClick(int row, int col, Stone stone) {
    MyLogger.logger.log(Level.INFO, "Stone clicked!");
    passes = 0;
    char rowChar = Converter.convertPosition(row);
    char colChar = Converter.convertPosition(col);
    if (!stone.isPut()) {
      int color = (Player) ? 1 : 2;
      MessageController.sendMessage("INSERT " + rowChar + colChar + color, socket);
      receiveMessage(stone, rowChar, colChar);
    }
  }
  @Override
  protected void insertStone(String value, Stone stone, char rowChar, char colChar) {
    switch (value) {
      case "TRUE" -> {
        stone.put(Player, rowChar, colChar);
        MyLogger.logger.log(Level.INFO, "Stone put: " + rowChar + colChar);
        Player = !Player;
        String text = (Player) ? "Current player: Black" : "Current player: White";
        label.setText(text);
      }
      case "FALSE" -> {
        MyLogger.logger.log(Level.INFO, "Stone wasn't put: " + rowChar + colChar);
        label.setText("You can't add a stone here!");
      }
    }
  }
@Override
  protected void endGame() {
    disableButtons();
    MessageController.sendMessage("END " + "none", socket);
    String winner = MessageController.receiveMessage(socket);
    switch (Objects.requireNonNull(winner)) {
      case "1" -> label.setText("BLACK is the winner!");
      case "2" -> label.setText("WHITE is the winner!");
    }
  }
}
