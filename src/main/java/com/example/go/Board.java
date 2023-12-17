package com.example.go;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Board {


  private final  GridPane gp;
  private final int numRows;
  private final int numColumns;

  public Board(int numRows, int numColumns, GridPane grid) {

    this.gp = grid;
    this.numRows = numRows;
    this.numColumns = numColumns;

    drawBoard();

  }

  private void drawBoard() {
    double cellWidth = gp.getWidth() / numRows;
    double cellHeight = gp.getHeight() / numColumns;

    for (int row = 1; row < numRows - 1; row++) {
      for (int col = 1; col < numColumns - 1; col++) {
        Image image = new Image("C:/Users/krokc/Desktop/tp/s.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);
        gp.add(imageView, col, row);
      }
    }

    for (int col = 1; col < numColumns - 1; col++) {
      Image image = new Image("C:/Users/krokc/Desktop/tp/d.png");
      ImageView imageView = new ImageView(image);
      imageView.setFitWidth(cellWidth);
      imageView.setFitHeight(cellHeight);
      gp.add(imageView, col, numRows);
    }


  }


}
