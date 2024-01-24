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

public class OnlineBoardGame implements Runnable{

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

  Socket socket;
  String Player;

  public void initialize(int size, Socket socket, String Player) {
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
    try {

    if(PlayerButBool) {
      Platform.runLater(() -> label.setText("Waiting for the second player..."));
      MessageController.receiveMessage(socket);
      Platform.runLater(() -> label.setText("Opponent has joined! You can move now"));
      myTurn = true;
    } else {
      Platform.runLater(() -> label.setText("Waiting for the first player to move")); ;
    }

    while (continueToPlay) {
      if (PlayerButBool) {
        waitForPlayerAction();
        //sendMove();
      //  recieveInfoFromServer();
      }
      else {
       // recieveInfoFromServer();
        waitForPlayerAction();
       // sendMove();
      }

    }
    } catch (InterruptedException ex) {}

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

         // passes = 0;

          char rowChar = convertPosition(finalRow);
          char colChar = convertPosition(finalCol);

          if (!stone.isPut()) {
            stone.put(PlayerButBool, rowChar, colChar);

           //int color = (Player) ? 1 : 2;  //TODO: is it ok???

          //  MessageController.sendMessage("INSERT " + rowChar + colChar + color, socket);

           // receiveMessage(stone, rowChar, colChar);

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

  @FXML
  private void passClicked() {

    System.out.println("Nie klikaj gdzie popadnie!");
  }

}
