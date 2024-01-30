package com.example.go;

import javafx.application.Platform;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class OnlineGameBoard extends GameBoard implements Runnable  {
  private String PlayerString;
  private boolean myTurn = false;
  private final boolean continueToPlay = true;
  private boolean waiting = true;
  private char rowSelected;
  private char colSelected;
  private boolean pass = false;

  @Override
  @FXML
  protected void passClicked() {
    pass = true;
    waiting = false;
    myTurn = false;
  }

  @Override
  @FXML
  protected void forfeitClicked() {
    MessageController.sendMessage("FORFEIT", socket);
    endGame();
  }

  @Override
  public void initialize(int size, Socket socket) {
    this.socket = socket;
    this.size = size;
    this.Player = Objects.equals(PlayerString, "FIRST");
    drawBoard();
    addStones();
    Thread thread = new Thread(this);
    thread.start();
  }

  public void setPlayer(String player) {
    this.PlayerString = player;
  }


  @Override
  public void run() {
    button.setDisable(true);
    try {
      if (Player) {
        Platform.runLater(() -> label.setText("Waiting for the second player..."));
        MessageController.receiveMessage(socket);
        Platform.runLater(() -> label.setText("Opponent has joined! You can move now"));
        myTurn = true;
      } else {
        Platform.runLater(() -> label.setText("Waiting for the first player to move"));
      }
      while (continueToPlay) {
        if (Player) {
          MyLogger.logger.log(Level.INFO, "I'm waiting for an action!");
          button.setDisable(false);
          waitForPlayerAction();

          MyLogger.logger.log(Level.INFO, "I'm sending a move");
          sendMove();
          button.setDisable(true);

          MyLogger.logger.log(Level.INFO, "I'm waiting for a message!");
          receiveMessage();
        } else {
          MyLogger.logger.log(Level.INFO, "I'm waiting for a message!");
          receiveMessage();

          MyLogger.logger.log(Level.INFO, "I'm waiting for an action!");
          button.setDisable(false);
          waitForPlayerAction();

          MyLogger.logger.log(Level.INFO, "I'm sending a move");
          sendMove();
          button.setDisable(true);
        }
      }
    } catch (IOException | InterruptedException ignored) {
    }
  }

  private void receiveMessage() throws IOException, InterruptedException {
    String answer = MessageController.receiveMessage(socket);
    Platform.runLater(() -> label.setText("Your turn!"));
    assert answer != null;
    whatAnswer(answer);
    myTurn = true;
  }

  private void sendMove() throws IOException, InterruptedException {
    if (pass) {
      MessageController.sendMessage("PASS", socket);
      String isGameFinished = MessageController.receiveMessage(socket);
      if (Objects.equals(isGameFinished, "YES")) {
        endGame();
      }
    } else {
      int color = (Player) ? 1 : 2;
      MessageController.sendMessage(rowSelected + String.valueOf(colSelected) + color, socket);
      String answer = MessageController.receiveMessage(socket);
      assert answer != null;
      whatAnswer(answer);
    }
  }

  @Override
  protected void endGame() {
    disableButtons();
    String winner = MessageController.receiveMessage(socket);
    switch (Objects.requireNonNull(winner)) {
      case "1" -> Platform.runLater(() -> label.setText("BLACK is the winner!"));
      case "2" -> Platform.runLater(() -> label.setText("WHITE is the winner!"));
      case "0" -> Platform.runLater(() -> label.setText("Draw!"));
    }
  }

  private void whatAnswer(String answer) throws IOException, InterruptedException {
    String[] part = answer.split("\\s+");
    String name = part[0];
    String value = part[1];
    System.out.println("Command: " + name);
    System.out.println("Data: " + value);
    switch (name) {
      case "INSERT" -> insertStone(value);
      case "DELETE" -> {
        deleteStone(value);
        String another = MessageController.receiveMessage(socket);
        assert another != null;
        whatAnswer(another);
      }
      case "PASS" ->
        isGameFinished(value);

    }

  }

  private void isGameFinished(String value) {
    if (Objects.equals(value, "END")) {
      endGame();
    } else {
      Platform.runLater(() -> label.setText("Opponent has passed (away)."));
    }
  }

  private void insertStone(String value) throws InterruptedException, IOException {
    switch (value) {
      case "TRUE" -> {
        String where = MessageController.receiveMessage(socket);
        assert where != null;
        int row = Converter.reconvertPosition(where.charAt(0));
        int col = Converter.reconvertPosition(where.charAt(1));
        int color = getColor(where.charAt(2));
        boolean currPlayer;
        currPlayer = color == 1;
        stones[row][col].put(currPlayer, where.charAt(0), where.charAt(1));
        MyLogger.logger.log(Level.INFO, "Stone put: " + where.charAt(0) + where.charAt(1));
      }
      case "FALSE" -> {
        MyLogger.logger.log(Level.INFO, "Stone wasn't put: " + value);
        Platform.runLater(() -> label.setText("You can't add a stone here!"));
        waiting = true;
        myTurn = true;
        waitForPlayerAction();
        sendMove();
      }
    }
  }

  private void waitForPlayerAction() throws InterruptedException {
    while (waiting) {
      Thread.sleep(100);
    }
    waiting = true;
  }

  @Override
  protected void handleStoneClick(int row, int col, Stone stone) {
    MyLogger.logger.log(Level.INFO, "Stone clicked!");
    char rowChar = Converter.convertPosition(row);
    char colChar = Converter.convertPosition(col);
    if (!stone.isPut() && myTurn) {
      pass = false;
      rowSelected = rowChar;
      colSelected = colChar;
      Platform.runLater(() -> label.setText("Waiting for the other player to move"));
      waiting = false;
      myTurn = false;
    }
  }

  protected int getColor(char colorChar) {
    return Character.getNumericValue(colorChar);
  }
}
