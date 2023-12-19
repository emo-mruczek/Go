package com.example.go;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    //addStones();
  }

  private void drawBoard() {
    //TODO: clean-up

    MyLogger.logger.log(Level.INFO, "Drawing a board!");

    this.cellWidth = gp.getWidth() / numColumns;
    this.cellHeight = gp.getHeight() / numRows;


    //TODO: kamienie totalnie rozwalają 19x19, trzeba dynamicznie dostosowywac ich wielkosc
    //TODO: najlepiej zeby kamienie w jakiejs osobnej metodzie sie robily, zeby to jakos uporzadkowac
    //TODO: albo w ogole w osobnej klasie
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
      addImageToCell(gp, path + "d.png", col, numRows);
    }

    for (int row = 1; row < numRows - 1; row++) {
      addImageToCell(gp, path + "l.png", 0, row);
      addImageToCell(gp, path + "p.png", numColumns, row);
    }


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

  private void addStones() {
    MyLogger.logger.log(Level.INFO, "Adding stones!");

    for (int row = 0; row < numRows; row++) {
      for (int col = 0; col < numColumns; col++) {

        Circle kamien = new Circle(20);


        kamien.setOpacity(0.15);

        kamien.setOnMouseClicked(event -> {
          MyLogger.logger.log(Level.INFO, "Kamien clicked!");
          kamien.setOpacity(1.0);
        });

        gp.setAlignment(Pos.CENTER);

        GridPane.setMargin(kamien, new Insets(5));

        gp.add(kamien, col, row);
      }
    }
  }
}
