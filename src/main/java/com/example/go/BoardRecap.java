package com.example.go;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class BoardRecap {

  @FXML
  private GridPane gp = new GridPane();

  private Game game;

  public void initialize(Game game) {
    this.game = game;

    int size = 9;
    BoardDrawer.insertImages(gp, size);
    System.out.println(size);
  }
}
