package com.example.go;

public class Move {
  private boolean player;
  private final char row;
  private final char col;
  private String timestamp;
  private String type;
  private int ID;

public Move(boolean player, char row, char col ) {
  this.col = col;
  this.player = player;
  this.row = row;
}

public Move(int ID, String timestamp, String playerString, String row, String col, String type ) {
  this.ID = ID;
  this.timestamp = timestamp;
  this.row = row.charAt(0);
  this.col = col.charAt(0);
  this.type = type;

  switch (playerString) {
    case "WHITE" -> player = false;
    case "BLACK" -> player = true;
  }
}

  public char getCol() {
    return col;
  }

  public char getRow() {
    return row;
  }
  public boolean getPlayer() {
  return player;
  }

  public String getType() {
    return type;
  }

  public int getID() {
  return ID;
  }

}
