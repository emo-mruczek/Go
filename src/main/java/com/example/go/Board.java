package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

import javafx.scene.paint.Color;

public class Board {

  @FXML
  private GridPane gp = new GridPane();
  private int size;
  double cellWidth;
  double cellHeight;
  private Socket socket;

  boolean Player = true;

  public void initialize(int size, Socket socket) {
    this.size = size;
    this.socket = socket;

    drawBoard();
    addStones();
  }

  private void drawBoard() {
    //TODO: clean-up

    MyLogger.logger.log(Level.INFO, "Drawing a board!");

    this.cellWidth = gp.getWidth() / size;
    this.cellHeight = gp.getHeight() / size;

    for (int row = 1; row < size - 1; row++) {
      for (int col = 1; col < size - 1; col++) {

        Image image = new Image("C:/Users/krokc/Desktop/tp/s.png");

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);

        gp.add(imageView, col, row);
      }
    }

    String path = "C:/Users/krokc/Desktop/tp/"; //change accordingly TODO: make it not dependent on an absolute path

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

        Circle stone = new Circle(cellWidth / 3);
        stone.setOpacity(0.0);

        stone.setOnMouseClicked(event -> {
          MyLogger.logger.log(Level.INFO, "Stone clicked!");

          // Przekształć indeksy wiersza i kolumny na odpowiednie litery, jeśli są większe niż 9
          char rowChar = (row < 10) ? (char) ('0' + row) : (char) ('A' + row - 10);
          char colChar = (col < 10) ? (char) ('0' + col) : (char) ('A' + col - 10);

          // Ustaw kolor kamienia
          int color = (Player) ? 1 : 2;

          // Wyślij informacje do serwera
          sendMessage("INSERT " + rowChar + colChar + color, socket);

          if (Player)
            stone.setFill(Color.WHITE);
          else
            stone.setFill(Color.BLACK);

          Player = !Player;
          stone.setOpacity(1.0);
        });

        GridPane.setHalignment(stone, HPos.CENTER);

        gp.add(stone, col, row);
      }
    }
  }

  private void sendMessage(String message, Socket socket) {
    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println(message);
    } catch (UnknownHostException e) {
      System.out.println("Server not found: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("I/O error: " + e.getMessage());
    }
  }


}
