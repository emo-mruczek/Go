package com.example.go;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Board {

  public Board(int numRows, int numColumns, GridPane grid) {

    double cellWidth = grid.getWidth() / numColumns;
    double cellHeight = grid.getHeight() / numRows;

    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numColumns; col++) {
        Image image = new Image("C:/Users/krokc/Desktop/test.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);
        grid.add(imageView, row, col);
      }
    }
  }


}
