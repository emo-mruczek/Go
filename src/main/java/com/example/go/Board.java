package com.example.go;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.util.logging.Level;

public class Board {

  private final GridPane gp;
  private final int numRows;
  private final int numColumns;
  double cellWidth;
  double cellHeight;
  public Board(int numRows, int numColumns, GridPane grid) {
    this.gp = grid;
    this.numRows = numRows;
    this.numColumns = numColumns;

    drawBoard();
  }

  private void drawBoard() {
    MyLogger.logger.log(Level.INFO, "Drawing a board!");

    this.cellWidth = gp.getWidth() / numColumns;
    this.cellHeight = gp.getHeight() / numRows;


    for (int row = 1; row < numRows - 1; row++) {
      for (int col = 1; col < numColumns - 1; col++) {
        Image image = new Image("C:/Users/krokc/Desktop/tp/s.png");
        ImageView imageView = new ImageView(image);
        Circle kamien = new Circle(20);


        kamien.setOpacity(0.15);

        kamien.setOnMouseClicked(event -> {
          MyLogger.logger.log(Level.INFO, "Kamien clicked!");
          kamien.setOpacity(1.0);
        });

        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);

        gp.add(imageView, col, row);


        gp.setAlignment(Pos.CENTER);


        GridPane.setMargin(kamien, new Insets(5));

        gp.add(kamien, col, row);

      }
    }

    String path = "C:/Users/krokc/Desktop/tp/";

// Adding images to the grid
    for (int col = 1; col < numColumns - 1; col++) {
      addImageToCell(gp, path + "g.png", col, 0);
      addImageToCell(gp, path + "d.png", col, numRows);
    }

    for (int row = 1; row < numRows - 1; row++) {
      addImageToCell(gp, path + "l.png", 0, row);
      addImageToCell(gp, path + "p.png", numColumns, row);
    }

// Adding corner images
    addImageToCell(gp, path + "gl.png", 0, 0);
    addImageToCell(gp, path + "gp.png", numColumns, 0);
    addImageToCell(gp, path + "dl.png", 0, numRows);
    addImageToCell(gp, path + "dp.png", numColumns, numRows);


  }

  private void addImageToCell(GridPane gp, String imagePath, int col, int row) {
    Image image = new Image(imagePath);
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(cellWidth);
    imageView.setFitHeight(cellHeight);
    gp.add(imageView, col, row);
  }



}
