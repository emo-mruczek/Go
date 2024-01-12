package com.example.go;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;

import java.net.Socket;

public class ListController {

  @FXML
  ListView<Game> list = new ListView<>();
  private ObservableList<Game> gamesList = FXCollections.observableArrayList();

  public void initialize(Socket socket) {
    String stringWithGames = MessageController.receiveMessage(socket);
    String[] listOfGames = stringWithGames.split(";");

    for (String game: listOfGames) {
      String[] gameData = game.split(",");
      Game g = new Game(Integer.parseInt(gameData[0]), gameData[1],Integer.parseInt(gameData[2]),gameData[3]);
      gamesList.add(g);
    }

    list.setItems(gamesList);
    list.setCellFactory(param -> new TextFieldListCell<>());

    list.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        Game selectedGame = list.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
          BoardRecap br = new BoardRecap(selectedGame);
        }
      }
    });
  }


}
