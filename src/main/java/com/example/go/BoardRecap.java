package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.nio.file.FileSystemNotFoundException;
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
      System.out.println(currID);
      switch(moveType) {
        case "INSERTION" -> insertStone(currID);
        case "DELETION" -> {
          ArrayList<Integer> storeID = new ArrayList<>();
          int tempID = currID;
          while (moves.get(tempID).getType().compareTo("DELETION") == 0) {
             storeID.add(tempID);
            tempID++;
          }
          insertStone(tempID);

          for (int ID : storeID) {
            deleteStone(ID);
          }

          currID = tempID;
          }
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
      label.setText("That was the first move!");
      MyLogger.logger.log(Level.INFO, "There is no previous moves.");
    } else {
      label.setText("");
      currID--;
    }
    String moveType = moves.get(currID).getType();
    switch(moveType) {
      case "INSERTION" -> {

        System.out.println(currID);

        if (currID == 0) {
          deleteStone(currID);
          return;
        }

        if (moves.get(currID - 1).getType().compareTo("DELETION") == 0) {
          ArrayList<Integer> storeID = new ArrayList<>();
          int tempID = currID - 1;
          while (moves.get(tempID).getType().compareTo("DELETION") == 0) {
            System.out.println("Jestem tutaj!");
            storeID.add(tempID);
            System.out.println(tempID);
            tempID--;
          }
          deleteStone(currID);

          System.out.println(storeID);

          for (int ID : storeID) {
            insertStone(ID);
          }

          currID = tempID + 1;
          return;
        }

        deleteStone(currID);
      }
      case "DELETION" -> {
        System.out.println("Czy ja tutaj wchodze w ogole?");
        insertStone(currID);
      }
    }
  }

private void insertStone(int ID) {
  char row = moves.get(ID).getRow();
  char col = moves.get(ID).getCol();
  boolean player = moves.get(ID).getPlayer();

  MyLogger.logger.log(Level.INFO, "Putting stone: " + row + " " + col);
  stones[reconvertPosition(row)][reconvertPosition(col)].put(player, row, col);
}

private void deleteStone(int ID) {
  char row = moves.get(ID).getRow();
  char col = moves.get(ID).getCol();

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
