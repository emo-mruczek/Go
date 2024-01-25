package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.Socket;
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


  public void initialize(int size,  Socket socket) {
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
      case "INSERT" -> stones[reconvertPosition(rowChar)][reconvertPosition(colChar)].put(Player, rowChar, colChar);
      //insertStone(value, stone, rowChar, colChar);
     // case "DELETE" -> {
     //   deleteStone(value);
     //   receiveMessage(stone, rowChar, colChar);
     // }
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


  @FXML
  private void passClicked() {

    MyLogger.logger.log(Level.INFO, "Player passed :(");
  }


}
