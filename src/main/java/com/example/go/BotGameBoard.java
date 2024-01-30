package com.example.go;

import javafx.fxml.FXML;
import java.util.Objects;
import java.util.logging.Level;

public class BotGameBoard extends GameBoard {

  @Override
  @FXML
  protected void passClicked() {
    MessageController.sendMessage("PASS " + "none", socket);
    String doesBotPass = MessageController.receiveMessage(socket);
    switch (Objects.requireNonNull(doesBotPass)) {
      case "YES" -> endGame();
      case "NO" -> botMove();
      default -> throw new IllegalStateException("Unexpected value: " + doesBotPass);
    }
  }
  @Override
  @FXML
  protected void forfeitClicked() {
    MyLogger.logger.log(Level.INFO, "Forfeit clicked!");
    MessageController.sendMessage("FORFEIT "+ "none", socket);
    String nothing = MessageController.receiveMessage(socket);
    endGame();
  }

  @Override
  protected void endGame() {
    disableButtons();
    String winner = MessageController.receiveMessage(socket);
    switch (Objects.requireNonNull(winner)) {
      case "1" -> label.setText("BLACK is the winner!");
      case "2" -> label.setText("WHITE is the winner!");
      case "0" -> label.setText("Draw!");
      default -> throw new IllegalStateException("Unexpected value: " + winner);
    }
  }

  @Override
  protected void handleStoneClick(int row, int col, Stone stone) {
    MyLogger.logger.log(Level.INFO, "Stone clicked!");

    char rowChar = Converter.convertPosition(row);
    char colChar = Converter.convertPosition(col);
    if (!stone.isPut()) {
      int color = 1;
      MessageController.sendMessage("INSERT " + rowChar + colChar + color, socket);
      receiveMessage(stone, rowChar, colChar);
    }
  }
  @Override
  protected void receiveMessage(Stone stone, char rowChar, char colChar) {
    String command = MessageController.receiveMessage(socket);
    assert command != null;
    String[] part = command.split("\\s+");
    String name = part[0];
    String value = part[1];
    System.out.println("Command: " + name);
    System.out.println("Data: " + value);
    switch (name) {
      case "INSERT" -> {
        insertStone(value, stone, rowChar, colChar);
        botMove();
      }
      case "DELETE" -> {
        deleteStone(value);
        receiveMessage(stone, rowChar, colChar);
      }
    }
  }

  private void botMove() {
    String botMoveStatus = MessageController.receiveMessage(socket);
    assert botMoveStatus != null;
    String[] part = botMoveStatus.split("\\s+");
    String name = part[0];
    String value = part[1];
    System.out.println("Command: " + name);
    System.out.println("Data: " + value);
    switch (name) {
      case "INSERT" -> {
        MessageController.sendMessage(botMoveStatus, socket);
        insertBotMove(value);
      }
      case "DELETE" -> {
        deleteStone(value);
        botMove();
      }
    }
  }

  private void insertBotMove(String value) {
    if (Objects.equals(value, "TRUE")) {
      String botMove = MessageController.receiveMessage(socket);
      assert botMove != null;
      int row = Converter.reconvertPosition(botMove.charAt(0));
      int col = Converter.reconvertPosition(botMove.charAt(1));
      MyLogger.logger.log(Level.INFO, "Bot's move: " + botMove.charAt(0) + " " + botMove.charAt(1));
      stones[row][col].put(false, botMove.charAt(0), botMove.charAt(1));
    } else {
      botMove();
    }
  }
}
