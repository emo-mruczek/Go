package com.example.go;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

public class Board {

  private final GridPane gp;
  private final int numRows;
  private final int numColumns;

  public Board(int numRows, int numColumns, GridPane grid) {
    this.gp = grid;
    this.numRows = numRows;
    this.numColumns = numColumns;

    drawBoard();
  }

  private void drawBoard() {
    double cellWidth = gp.getWidth() / numColumns;
    double cellHeight = gp.getHeight() / numRows;

    gp.setAlignment(Pos.CENTER);

    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numColumns; col++) {
        Image image = new Image("C:/Users/Aldona/Desktop/test.png");
        ImageView imageView = new ImageView(image);
        Circle kamien = new Circle(20);

        // Set initial transparency
        kamien.setOpacity(0); // Adjust the value as needed

        kamien.setOnMouseClicked(event -> {
          // Handle button click action
          System.out.println("Button clicked!");

          // Make the circle visible on click
          kamien.setOpacity(1.0);
        });

        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);
        gp.add(imageView, col, row);

        // Set the alignment of the cell
        gp.setAlignment(Pos.CENTER);

        // You can set some padding or spacing if needed
        GridPane.setMargin(kamien, new javafx.geometry.Insets(5));

        gp.add(kamien, col, row);
      }
    }
  }
}
