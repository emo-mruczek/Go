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

  @FXML
  private void smallClicked() {
    MyLogger.logger.log(Level.INFO, "Small clicked!");

    Board board = new Board(9, 9, grid);
  }

  //TODO: is it a right size?? im not feeling like checking it lol
  @FXML
  private void mediumClicked() {
    MyLogger.logger.log(Level.INFO, "Medium clicked!");

    Board board = new Board(13, 13, grid);
  }

  //TODO: omg its so ugly whyyyyyyyy ;-;-;-;
  @FXML
  private void largeClicked() {
    MyLogger.logger.log(Level.INFO, "Large clicked");

    Board board = new Board(19, 19, grid);
  }

}