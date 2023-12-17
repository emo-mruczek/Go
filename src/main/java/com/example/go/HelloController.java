package com.example.go;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class HelloController {

  @FXML
  private Label welcomeText;
  @FXML
  private GridPane grid;
  int numRows = 3;
  int numColumns = 3;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");

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