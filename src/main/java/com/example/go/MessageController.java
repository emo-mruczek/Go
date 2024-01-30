package com.example.go;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

public class MessageController {

  public static void sendMessage(String message, Socket socket) {
    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      MyLogger.logger.log(Level.INFO, message);
      out.println(message);
    } catch (UnknownHostException e) {
      System.out.println("Server not found: " + e.getMessage());
    } catch (IOException e) {
      System.out.println("I/O error: " + e.getMessage());
    }
  }
  
  public static String receiveMessage(Socket socket) {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String serverResponse = in.readLine();
      MyLogger.logger.log(Level.INFO, serverResponse);
      if (serverResponse != null) {
        return serverResponse;
      } else {
        MyLogger.logger.log(Level.WARNING, "Received NULL!");
        return null;
      }
    } catch (IOException e) {
      MyLogger.logger.log(Level.WARNING, "Error while receiving a message: " + e.getMessage());
      return null;
    }
  }
}
