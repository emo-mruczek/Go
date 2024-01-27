package com.example.go;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;

public class OnlineGameBoard implements Runnable{

  @FXML
  private GridPane gp = new GridPane();
  @FXML
  private Label label = new Label();
  @FXML
  private Button button = new Button();

  private int size;
  double cellSize;

  private Stone[][] stones;
  private boolean myTurn = false;
  private boolean continueToPlay = true;
  private boolean waiting = true;
  private boolean PlayerButBool;
  private char rowSelected;
  private char colSelected;
  private boolean pass = false;

  Socket socket;
  String Player;

  public void initialize(int size, Socket socket, String Player ) {
    this.socket = socket;
    this.Player = Player;
    this.size = size;

    if (Objects.equals(Player, "FIRST")) {
      this.PlayerButBool = true;
    } else {
      this.PlayerButBool = false;
    }

    drawBoard();
    addStones();

    Thread thread = new Thread(this);
    thread.start();
  }



  @Override
  public void run() {
    button.setDisable(true);

    try {

    if(PlayerButBool) {
      Platform.runLater(() -> label.setText("Waiting for the second player..."));
      MessageController.receiveMessage(socket);
      Platform.runLater(() -> label.setText("Opponent has joined! You can move now"));
      myTurn = true;
    } else {
      Platform.runLater(() -> label.setText("Waiting for the first player to move"));
    }

    while (continueToPlay) {
      if (PlayerButBool) {
        MyLogger.logger.log(Level.INFO, "I'm waiting for an action!");
        button.setDisable(false);
        waitForPlayerAction();
        MyLogger.logger.log(Level.INFO, "I'm sending a move");
        sendMove();
        button.setDisable(true);
        MyLogger.logger.log(Level.INFO, "I'm waiting for a message!");
        receiveMessage();
      }
      else {
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
    } catch (IOException ex) {
      System.err.println(ex);
    } catch (InterruptedException ex) {}

  }


  @FXML
  private void passClicked() {
    pass = true;
    waiting = false;
    myTurn = false;
  }


  private void receiveMessage() throws IOException, InterruptedException {
    String answer= MessageController.receiveMessage(socket);
    Platform.runLater(() -> label.setText("Your turn!"));

    whatAnswer(answer);
    myTurn = true;
  }

  private void sendMove() throws IOException, InterruptedException {
    if (pass) {

      MessageController.sendMessage("PASS", socket);
      String isGameFinished = MessageController.receiveMessage(socket);
      if(Objects.equals(isGameFinished, "YES")) {
        endGame();
      }
    } else {
    int color = (PlayerButBool) ? 1 : 2;
    MessageController.sendMessage(rowSelected + String.valueOf(colSelected) + color, socket);
    String answer = MessageController.receiveMessage(socket);
    whatAnswer(answer);
    }
  }

  private void endGame() {
    button.setDisable(true);

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        stones[row][col].setDisable(true);
      }
    }
    String winner = MessageController.receiveMessage(socket);
    button.setDisable(true);
    switch (winner) {
      case "1" ->  Platform.runLater(() ->label.setText("BLACK is the winner!"));
      case "2" ->  Platform.runLater(() ->label.setText("WHITE is the winner!"));
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
        whatAnswer(another);
      }
      case "PASS" -> {
        isGameFinished(value);
      }
    }

  }
  private void isGameFinished(String value) {
    if(Objects.equals(value, "END")) {
      endGame();
    } else {
      Platform.runLater(() -> label.setText("Opponent has passed (away)."));
    }
  }


  private void deleteStone(String value) {
    int row = reconvertPosition(value.charAt(0));
    int col = reconvertPosition(value.charAt(1));

    stones[row][col].remove();

    MyLogger.logger.log(Level.INFO, "Deleting: " + value);
  }

  private void insertStone(String value) throws InterruptedException, IOException {

    switch (value) {
      case "TRUE" -> {
        String where = MessageController.receiveMessage(socket);

        int row = getRow(where.charAt(0));
        int col = getCol(where.charAt(1));
        int color = getColor(where.charAt(2));

        boolean currPlayer;

        if (color == 1) {
          currPlayer = true;
        } else {
          currPlayer = false;
        }

      // moves.add(new Move(currPlayer, where.charAt(0), where.charAt(1)));

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

  private void drawBoard() {
    // TODO: clean-up
    MyLogger.logger.log(Level.INFO, "Drawing a board!");

    cellSize = gp.getWidth() / size;
    stones = new Stone[size][size];

    BoardDrawer.insertImages(gp, size);
  }

  private void addStones() {
    MyLogger.logger.log(Level.INFO, "Adding stones!");

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        label.setText("Current player: Black");

        int finalRow = row;
        int finalCol = col;

        Stone stone = new Stone(cellSize / 3);
        stones[row][col] = stone;
        stone.setOnMouseClicked(event -> {
          MyLogger.logger.log(Level.INFO, "Stone clicked!");

          char rowChar = convertPosition(finalRow);
          char colChar = convertPosition(finalCol);

          if (!stone.isPut() && myTurn) {
            pass = false;
            rowSelected = rowChar;
            colSelected = colChar;
            Platform.runLater(() -> label.setText("Waiting for the other player to move"));
            waiting = false;
            myTurn = false;
          }
        });
        GridPane.setHalignment(stone, HPos.CENTER);
        gp.add(stone, col, row);
      }
    }
  }

  private char convertPosition(int position) {
    return (position < 10) ? (char) ('0' + position) : (char) ('A' + position - 10);
  }

  private int getRow(char rowChar) {
    if (rowChar >= '0' && rowChar <= '9') {
      return rowChar - '0';
    } else {
      return rowChar - 'A' + 10;
    }
  }

  private int getCol(char colChar) {
    if (colChar >= '0' && colChar <= '9') {
      return colChar - '0';
    } else {
      return colChar - 'A' + 10;
    }
  }

  private int reconvertPosition(char character) {
    if (character >= '0' && character <= '9') {
      return character - '0';
    } else if (character >= 'A' && character <= 'Z') {
      return character - 'A' + 10;
    } else {
      throw new IllegalArgumentException("Invalid character: " + character);
    }
  }

  private int getColor(char colorChar) {
    return Character.getNumericValue(colorChar);
  }


}
