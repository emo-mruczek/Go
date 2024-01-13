package com.example.go;

import java.util.Objects;

public class Move {
 // private int id; i think it is not necessary as array list is sorted
  private boolean player;
  private char row;
  private char col;
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

  System.out.println("Move player: " + playerString);

  switch (playerString) {
    case "WHITE" -> player = false;
    case "BLACK" -> player = true;
  }

 // if (playerString == "WHITE") {
 //   this.player = false;
 // } else {
  //  this.player = true;
 // }

  System.out.println("Move bool player: " + player);

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
