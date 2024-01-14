package com.example.go;

public class Game {
  private final int id;
  private final String timestamp;
  private final int size;
  private final String player;
  private String[] moves;

  public Game(int id, String timestamp, int size, String player) {
    this.id = id;
    this.timestamp = timestamp;
    this.size = size;
    this.player = player;
  }

  public int getId() {
    return id;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public int getSize() {
    return size;
  }

  public String getPlayer() {
    return player;
  }

  @Override
  public String toString() {
    return("Game number " + id + " played on " + timestamp + " on board with size " + size + ". Winner is " + player);
  }
}
