package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

public class Board {
  @FXML
  private GridPane gp = new GridPane();
  @FXML
  private Label label = new Label();
  @FXML
  private Button button = new Button();

  private int size;
  double cellWidth;
  double cellHeight;
  private Socket socket;
  private Stone[][] stones;
  boolean Player = true;
  int passes = 0;
  ArrayList<Move> moves = new ArrayList<Move>();


  public void initialize(int size, boolean mode, Socket socket) {
    this.size = size;
    this.socket = socket;

    drawBoard();
    addStones();

    if (mode) {
      initializeComputer();
    }
  }

  @FXML
  private void passClicked() {
    Player = !Player;
    passes++;
    System.out.println(passes);

    if (passes > 1) {
      endGame();
    }

    String text = (Player) ? "Current player: Black" : "Current player: White";
    label.setText(text);
    MyLogger.logger.log(Level.INFO, "Player passed :(");
  }

  private void drawBoard() {
    // TODO: clean-up
    MyLogger.logger.log(Level.INFO, "Drawing a board!");

    this.cellWidth = gp.getWidth() / size;
    this.cellHeight = gp.getHeight() / size;

    stones = new Stone[size][size];

    String path = "C:/Users/krokc/Desktop/tp/"; // change accordingly TODO: make it not dependent on an absolute path

    for (int row = 1; row < size - 1; row++) {
      for (int col = 1; col < size - 1; col++) {
        addImageToCell(gp, path + "s.png", col, row);
      }
    }

    for (int col = 1; col < size - 1; col++) {
      addImageToCell(gp, path + "g.png", col, 0);
      addImageToCell(gp, path + "d.png", col, size - 1);
    }

    for (int row = 1; row < size - 1; row++) {
      addImageToCell(gp, path + "l.png", 0, row);
      addImageToCell(gp, path + "p.png", size - 1, row);
    }

    addImageToCell(gp, path + "gl.png", 0, 0);
    addImageToCell(gp, path + "gp.png", size - 1, 0);
    addImageToCell(gp, path + "dl.png", 0, size - 1);
    addImageToCell(gp, path + "dp.png", size - 1, size - 1);
  }

  private void addImageToCell(GridPane gp, String imagePath, int col, int row) {
    Image image = new Image(imagePath);
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(cellWidth);
    imageView.setFitHeight(cellHeight);
    gp.add(imageView, col, row);
  }

  private void addStones() {
    MyLogger.logger.log(Level.INFO, "Adding stones!");

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        label.setText("Current player: Black");

        int finalRow = row;
        int finalCol = col;

        Stone stone = new Stone(cellWidth / 3);
        stones[row][col] = stone;
        stone.setOnMouseClicked(event -> {
          MyLogger.logger.log(Level.INFO, "Stone clicked!");

          passes = 0;

          char rowChar = convertPosition(finalRow);
          char colChar = convertPosition(finalCol);

          if (!stone.isPut()) {
            //stone.setOpacity(0.0);   //TODO: why is it here and what is it doing?

            int color = (Player) ? 1 : 2;  //TODO: is it ok???

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
      case "INSERT" -> insertStone(value, stone, rowChar, colChar);
      case "DELETE" -> {
        deleteStone(value);
        receiveMessage(stone, rowChar, colChar);
      }
    }

  }

  private void insertStone(String value, Stone stone, char rowChar, char colChar) {
    switch (value) {
      case "TRUE" -> {
        moves.add(new Move(Player, rowChar, colChar));
        stone.put(Player, rowChar, colChar);
        MyLogger.logger.log(Level.INFO, "Stone put: " + rowChar + colChar);
        Player = !Player;
        String text = (Player) ? "Current player: Black" : "Current player: White";
        label.setText(text);}
      case "FALSE" -> {
        MyLogger.logger.log(Level.INFO, "Stone wasn't put: " + rowChar + colChar);
        label.setText("You can't add a stone here!");
      }
    }
  }

  private void deleteStone(String value) {
    int row = reconvertPosition(value.charAt(0));
    int col = reconvertPosition(value.charAt(1));

    stones[row][col].remove();

    label.setText("Last breath!");
    MyLogger.logger.log(Level.INFO, "Deleting: " + value);
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

  //TODO: this
  private void endGame() {
    label.setText("Game finished!");
    button.setDisable(true);

    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        stones[row][col].setDisable(true);
      }
      }
    System.out.println(moves);
    MessageController.sendMessage("SAVE " + "none", socket);
  }

  //TODO: this
  private void initializeComputer() {
    System.out.println("dont be sad :((");

  }
}
