package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

public class BotGameBoard {

  @FXML
  private GridPane gp = new GridPane();
  @FXML
  private Label label = new Label();
  @FXML
  private Button button = new Button();

  private Socket socket;
  private int size;
  private Stone[][] stones;
  double cellSize;
  int color = 1;
  boolean Player = true;
  ArrayList<Move> moves = new ArrayList<Move>();


  public void initialize(int size, Socket socket) {
    this.size = size;
    this.socket = socket;

    drawBoard();
    addStones();
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
        label.setText("You are playing as Black!");

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


            //stone.setOpacity(0.0);   //TODO: why is it here and what is it doing?

            MessageController.sendMessage("INSERT " + rowChar + colChar + color, socket);

            receiveMessage(stone, rowChar, colChar);

          }
        });
        GridPane.setHalignment(stone, HPos.CENTER);
        gp.add(stone, col, row);
      }
    }
  }

  private void receiveMessage(Stone stone, char rowChar, char colChar) {
    String command = MessageController.receiveMessage(socket);

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

      int row = reconvertPosition(botMove.charAt(0));
      int col = reconvertPosition(botMove.charAt(1));

      MyLogger.logger.log(Level.INFO, "Bot's move: " + botMove.charAt(0) + " " + botMove.charAt(1));
      stones[row][col].put(false, botMove.charAt(0), botMove.charAt(1));
    } else {
      botMove();
    }
  }


  private void insertStone(String value, Stone stone, char rowChar, char colChar) {
    switch (value) {
      case "TRUE" -> {
        moves.add(new Move(Player, rowChar, colChar));
        stone.put(Player, rowChar, colChar);
        MyLogger.logger.log(Level.INFO, "Stone put: " + rowChar + colChar);
      }
      case "FALSE" -> {
        MyLogger.logger.log(Level.INFO, "Stone wasn't put: " + rowChar + colChar);
        label.setText("You can't add a stone here!");
      }
    }
  }


  private char convertPosition(int position) {
    return (position < 10) ? (char) ('0' + position) : (char) ('A' + position - 10);
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

  private void deleteStone(String value) {
    int row = reconvertPosition(value.charAt(0));
    int col = reconvertPosition(value.charAt(1));

    stones[row][col].remove();

    label.setText("Last breath!");
    MyLogger.logger.log(Level.INFO, "Deleting: " + value);
  }


  @FXML
  private void passClicked() {

    MyLogger.logger.log(Level.INFO, "Player passed :(");
  }


}
