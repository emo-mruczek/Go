package com.example.go;

public class Move {
 // private int id; i think it is not necessary as array list is sorted
  private boolean player;
  private char row;
  private char col;

public Move(boolean player, char row, char col ) {
  this.col = col;
  this.player = player;
  this.row = row;
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
}
