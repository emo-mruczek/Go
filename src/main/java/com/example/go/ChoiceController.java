package com.example.go;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;



public class ChoiceController {


  @FXML
  private void smallClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Small clicked!");

    initializeBoard(9, 9);
  }

  //TODO: is it a right size?? im not feeling like checking it lol
  @FXML
  private void mediumClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Medium clicked!");

    initializeBoard(13, 13);
  }

  //TODO: omg its so ugly whyyyyyyyy ;-;-;-;
  @FXML
  private void largeClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Large clicked");

    initializeBoard(19, 19);
  }

  private void initializeBoard(int rows, int columns) throws IOException {
    MyLogger.logger.log(Level.INFO, "Initializing " + rows + "x" + columns + " board!");

    FXMLLoader loader = new FXMLLoader(getClass().getResource("board-view.fxml"));
    Scene scene = new Scene(loader.load());
    Stage stage = new Stage();
    stage.setTitle("Go");
    stage.setScene(scene);
    stage.show();
    Board controller = loader.getController();
    controller.initialize(rows, columns);

  }

}