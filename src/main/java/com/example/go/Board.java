package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.util.logging.Level;

public class Board {

  @FXML
  private GridPane gp = new GridPane();
  private int numRows;
  private int numColumns;
  double cellWidth;
  double cellHeight;

  public void initialize(int numRows, int numColumns) {
    this.numRows = numRows;
    this.numColumns = numColumns;

    drawBoard();
    addStones();
  }

  private void drawBoard() {
    //TODO: clean-up

    MyLogger.logger.log(Level.INFO, "Drawing a board!");

    this.cellWidth = gp.getWidth() / numColumns;
    this.cellHeight = gp.getHeight() / numRows;

    for (int row = 1; row < numRows - 1; row++) {
      for (int col = 1; col < numColumns - 1; col++) {
        Image image = new Image("C:/Users/krokc/Desktop/tp/s.png");
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);

        gp.add(imageView, col, row);
      }
    }

    String path = "C:/Users/krokc/Desktop/tp/"; //change accordingly TODO: make it not dependent on an absolute path

    for (int col = 1; col < numColumns - 1; col++) {
      addImageToCell(gp, path + "g.png", col, 0);
      addImageToCell(gp, path + "d.png", col, numRows - 1);
    }

    for (int row = 1; row < numRows - 1; row++) {
      addImageToCell(gp, path + "l.png", 0, row);
      addImageToCell(gp, path + "p.png", numColumns - 1, row);
    }

    addImageToCell(gp, path + "gl.png", 0, 0);
    addImageToCell(gp, path + "gp.png", numColumns - 1, 0);
    addImageToCell(gp, path + "dl.png", 0, numRows - 1);
    addImageToCell(gp, path + "dp.png", numColumns - 1, numRows - 1);
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

    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numColumns; col++) {

        Circle stone = new Circle(10);

        stone.setOpacity(0.15); 

        stone.setOnMouseClicked(event -> {
          MyLogger.logger.log(Level.INFO, "Stone clicked!");
          stone.setOpacity(1.0);
        });

        GridPane.setHalignment(stone, HPos.CENTER);

        gp.add(stone, col, row);
      }
    }
  }
}
