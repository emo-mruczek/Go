package com.example.go;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.logging.Level;


public class HelloController {

  @FXML
  private Label welcomeText;
  @FXML
  private GridPane grid;


  int numRows = 9;
  int numColumns = 9;


  @FXML
  protected void onHelloButtonClick() {
    welcomeText.setText("Welcome to JavaFX Application!");

    MyLogger.logger.log(Level.INFO, "Clicked on a button!");

    Board board = new Board(numRows, numColumns, grid);

  }
}