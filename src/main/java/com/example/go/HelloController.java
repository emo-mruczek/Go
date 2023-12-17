package com.example.go;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    Board board = new Board(numRows, numColumns, grid);

  }
}