package com.example.go;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class ListController {

  @FXML
  ListView<Game> list = new ListView<>();

  private Socket socket;
  private final ObservableList<Game> gamesList = FXCollections.observableArrayList();

  public void initialize(Socket socket) {
    this.socket = socket;
    String stringWithGames = MessageController.receiveMessage(socket);
    assert stringWithGames != null;
    String[] listOfGames = stringWithGames.split(";");
    for (String game: listOfGames) {
      String[] gameData = game.split(",");
      Game g = new Game(Integer.parseInt(gameData[0]), gameData[1], Integer.parseInt(gameData[2]),gameData[3]);
      gamesList.add(g);
    }
    list.setItems(gamesList);
    list.setCellFactory(param -> new TextFieldListCell<>());
    list.setOnMouseClicked(event -> {
      if (event.getClickCount() == 2) {
        Game selectedGame = list.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
          newRecap(selectedGame);
        }
      }
    });
  }

  private void newRecap(Game game) {
    try {
      MyLogger.logger.log(Level.INFO, "Initializing recap");
      MessageController.sendMessage(String.valueOf(game.getId()), socket);
      String movesList = MessageController.receiveMessage(socket);
      FXMLLoader loader = ScreenInitializer.initialize("recap-view.fxml", "Recap", socket);
      BoardRecap controller = loader.getController();
      assert movesList != null;
      controller.initialize(game, movesList);
    } catch (IOException e)  {
      throw new RuntimeException(e);
    }
  }
}
