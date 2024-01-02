package com.example.go;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

public class ChoiceController {


  @FXML
  private void smallClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Small clicked!");

    initializeBoard(9);
  }

  @FXML
  private void mediumClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Medium clicked!");

    initializeBoard(13);
  }

  @FXML
  private void largeClicked() throws IOException {
    MyLogger.logger.log(Level.INFO, "Large clicked");

    initializeBoard(19);
  }

  private void initializeBoard(int size) {
    try {
      MyLogger.logger.log(Level.INFO, "Initializing " + size + "x" + size + " board!");

      //Socket socket = new Socket("localhost", 4444);
      //String message = String.valueOf(size);
      //sendMessage(message, socket);

      FXMLLoader loader = new FXMLLoader(getClass().getResource("board-view.fxml"));
      Scene scene = new Scene(loader.load());
      Stage stage = new Stage();

      //stage.setOnCloseRequest(event -> {
      //  sendMessage("BYE " + "none", socket);
      //});

      stage.setTitle("Go");
      stage.setScene(scene);
      stage.show();

      Board controller = loader.getController();
      controller.initialize(size);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void sendMessage(String message, Socket socket) {
    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println(message);
    } catch (UnknownHostException e) {
      System.out.println("Server not found: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("I/O error: " + e.getMessage());
    }
  }


}