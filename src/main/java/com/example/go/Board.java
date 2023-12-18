package com.example.go;

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


  public Board(int numRows, int numColumns, GridPane grid) {
    this.gp = grid;
    this.numRows = numRows;
    this.numColumns = numColumns;

    drawBoard();
  }

  private void drawBoard() {
    MyLogger.logger.log(Level.INFO, "Drawing a board!");

    double cellWidth = gp.getWidth() / numColumns;
    double cellHeight = gp.getHeight() / numRows;


    for (int row = 1; row < numRows - 1; row++) {
      for (int col = 1; col < numColumns - 1; col++) {
        Image image = new Image("C:/Users/krokc/Desktop/tp/s.png");
        ImageView imageView = new ImageView(image);
        Circle kamien = new Circle(20);


        kamien.setOpacity(0.15);

        kamien.setOnMouseClicked(event -> {

          System.out.println("Button clicked!");


         kamien.setOpacity(1.0);
        });

        imageView.setFitWidth(cellWidth);
        imageView.setFitHeight(cellHeight);

        gp.add(imageView, col, row);


        gp.setAlignment(Pos.CENTER);


        GridPane.setMargin(kamien, new javafx.geometry.Insets(5));

        gp.add(kamien, col, row);

      }
    }

    for (int col = 1; col < numColumns - 1; col++) {
      int row = 0;
      Image image = new Image("C:/Users/krokc/Desktop/tp/g.png");
      ImageView imageView = new ImageView(image);
      imageView.setFitWidth(cellWidth);
      imageView.setFitHeight(cellHeight);
      gp.add(imageView, col, row);
    }

    for (int col = 1; col < numColumns - 1; col++) {
      Image image = new Image("C:/Users/krokc/Desktop/tp/d.png");
      ImageView imageView = new ImageView(image);
      imageView.setFitWidth(cellWidth);
      imageView.setFitHeight(cellHeight);
      gp.add(imageView, col, numRows);
    }

    for (int row = 1; row < numRows - 1; row++) {
      int col = 0;
      Image image = new Image("C:/Users/krokc/Desktop/tp/l.png");
      ImageView imageView = new ImageView(image);
      imageView.setFitWidth(cellWidth);
      imageView.setFitHeight(cellHeight);
      gp.add(imageView, col, row);
    }

    for (int row = 1; row < numRows - 1; row++) {
      Image image = new Image("C:/Users/krokc/Desktop/tp/p.png");
      ImageView imageView = new ImageView(image);
      imageView.setFitWidth(cellWidth);
      imageView.setFitHeight(cellHeight);
      gp.add(imageView, numColumns, row);
    }

    Image image = new Image("C:/Users/krokc/Desktop/tp/gl.png");
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(cellWidth);
    imageView.setFitHeight(cellHeight);
    gp.add(imageView, 0, 0);

    Image image1 = new Image("C:/Users/krokc/Desktop/tp/gp.png");
    ImageView imageView1 = new ImageView(image1);
    imageView1.setFitWidth(cellWidth);
    imageView1.setFitHeight(cellHeight);
    gp.add(imageView1, numColumns, 0);

    Image image2 = new Image("C:/Users/krokc/Desktop/tp/dl.png");
    ImageView imageView2 = new ImageView(image2);
    imageView2.setFitWidth(cellWidth);
    imageView2.setFitHeight(cellHeight);
    gp.add(imageView2, 0, numRows);

    Image image3 = new Image("C:/Users/krokc/Desktop/tp/dp.png");
    ImageView imageView3 = new ImageView(image3);
    imageView3.setFitWidth(cellWidth);
    imageView3.setFitHeight(cellHeight);
    gp.add(imageView3, numColumns, numRows);


  }


}
