package com.example.go;

public class Game {
  private int id;
  private String timestamp;
  private int size;
  private String player;
  private String[] moves;

  public Game(int id, String timestamp, int size, String player) {
    this.id = id;
    this.timestamp = timestamp;
    this.size = size;
    this.player = player;
  }

  @Override
  public String toString() {
    return("Game number " + id + " played on " + timestamp + " on board with size " + size + ". Winner is " + player);
  }
}
