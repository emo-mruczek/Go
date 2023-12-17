package com.example.go;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class HelloController {

  @FXML
  private Label welcomeText;
  @FXML
  private GridPane grid;

  int numRows = 10;
  int numColumns = 10;

  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");

    Board board = new Board(numRows, numColumns, grid);

  }
}