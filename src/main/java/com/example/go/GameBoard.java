package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.net.Socket;
import java.util.logging.Level;

public class GameBoard {
  protected double cellSize;
  protected boolean Player = true;
  @FXML
  protected  GridPane gp = new GridPane();
  @FXML
  protected  Label label = new Label();
  @FXML
  protected  Button button = new Button();
  @FXML
  protected  Button button1 = new Button();
  protected  int size;
  protected  Socket socket;
  protected  Stone[][] stones;
  @FXML
  protected void passClicked() {
  }

  @FXML
  protected void forfeitClicked() {
  }

  public void initialize(int size, Socket socket) {
    this.size = size;
    this.socket = socket;
    drawBoard();
    addStones();
  }

  protected void drawBoard() {
    MyLogger.logger.log(Level.INFO, "Drawing a board!");
    cellSize = gp.getWidth() / size;
    stones = new Stone[size][size];
    BoardDrawer.insertImages(gp, size);
  }

  protected void addStones() {
    MyLogger.logger.log(Level.INFO, "Adding stones!");

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        label.setText("Current player: Black");
        Stone stone = new Stone(cellSize / 3);
        stones[row][col] = stone;
        int finalRow = row;
        int finalCol = col;
        stone.setOnMouseClicked(event -> handleStoneClick(finalRow, finalCol, stone));
        GridPane.setHalignment(stone, HPos.CENTER);
        gp.add(stone, col, row);
      }
    }
  }
 protected void handleStoneClick(int row, int col, Stone stone) {}

 protected void deleteStone(String value) {
    int row = Converter.reconvertPosition(value.charAt(0));
    int col = Converter.reconvertPosition(value.charAt(1));
    stones[row][col].remove();
    MyLogger.logger.log(Level.INFO, "Deleting: " + value);
  }

  protected void endGame() {
    disableButtons();
  }

  protected void disableButtons() {
    button.setDisable(true);
    button1.setDisable(true);
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        stones[row][col].setDisable(true);
      }
    }
  }

  protected void insertStone(String value, Stone stone, char rowChar, char colChar) {
    switch (value) {
      case "TRUE" -> {
        stone.put(Player, rowChar, colChar);
        MyLogger.logger.log(Level.INFO, "Stone put: " + rowChar + colChar);
      }
      case "FALSE" -> {
        MyLogger.logger.log(Level.INFO, "Stone wasn't put: " + rowChar + colChar);
        label.setText("You can't add a stone here!");
      }
    }
  }

  protected void receiveMessage(Stone stone, char rowChar, char colChar) {
    String command = MessageController.receiveMessage(socket);
    assert command != null;
    String[] part = command.split("\\s+");
    String name = part[0];
    String value = part[1];
    System.out.println("Command: " + name);
    System.out.println("Data: " + value);
    switch (name) {
      case "INSERT" -> insertStone(value, stone, rowChar, colChar);
      case "DELETE" -> {
        deleteStone(value);
        receiveMessage(stone, rowChar, colChar);
      }
    }
  }
}
