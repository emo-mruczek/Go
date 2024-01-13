package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.logging.Level;

public class BoardRecap {

  @FXML
  private GridPane gp = new GridPane();
  @FXML
  private Label label = new Label();

  ArrayList<Move> moves = new ArrayList<>();
  private int numberOfMoves;
  private int currID = 0;
  private Stone[][] stones;
  private Game game;

@FXML
  private void nextClicked() {
      if (currID >= numberOfMoves) {
        return;
      }
      String moveType = moves.get(currID).getType();
      switch(moveType) {
        case "INSERTION" -> insertStone();
        case "DELETION" -> deleteStone();
      }

      if (currID == numberOfMoves - 1) {
        label.setText("This is the final move!");
        MyLogger.logger.log(Level.INFO, "There is no more moves.");
        currID++;
      } else {
        label.setText("");
        currID++;
      }
  }

  @FXML
  private void previousClicked() {
    if (currID == 0) {
      label.setText("This was the first move!");
      MyLogger.logger.log(Level.INFO, "There is no previous moves.");
    } else {
      label.setText("");
      currID--;
    }
    String moveType = moves.get(currID).getType();
    switch(moveType) {
      case "INSERTION" -> deleteStone();
      case "DELETION" -> insertStone();
    }
  }

private void insertStone() {
  char row = moves.get(currID).getRow();
  char col = moves.get(currID).getCol();
  boolean player = moves.get(currID).getPlayer();

  MyLogger.logger.log(Level.INFO, "Putting stone: " + row + " " + col);
  stones[reconvertPosition(row)][reconvertPosition(col)].put(player, row, col);
}

private void deleteStone() {
  char row = moves.get(currID).getRow();
  char col = moves.get(currID).getCol();

  MyLogger.logger.log(Level.INFO, "Deleting stone: " + row + " " + col);
  stones[reconvertPosition(row)][reconvertPosition(col)].remove();
}

  public void initialize(Game game, String stringWithMoves) {
    this.game = game;

    int size = 9;
    BoardDrawer.insertImages(gp, size);

    String[] listOfMoves = stringWithMoves.split(";");

    for (String move : listOfMoves) {
      String[] moveData = move.split(",");
      Move m = new Move(Integer.parseInt(moveData[0]), moveData[1], moveData[2], moveData[3], moveData[4], moveData[5]);
      moves.add(m);
    }
    numberOfMoves = moves.size();

    stones = new Stone[size][size];

    double cellSize = gp.getWidth() / size;
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        Stone stone = new Stone(cellSize / 3);
        stones[row][col] = stone;

        GridPane.setHalignment(stone, HPos.CENTER);
        gp.add(stone, col, row);
      }
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
}
