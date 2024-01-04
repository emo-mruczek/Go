package com.example.go;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.logging.Level;

public class Stone extends Circle {
  private char rowChar;
  private char colChar;
  private boolean player;
  private boolean isPut = false;

  public Stone(double radius) {
    super(radius);
    setOpacity(0.0);
  }

  public void put(boolean player, char rowChar, char colChar) {
    this.player = player;
    this.rowChar = rowChar;
    this.colChar = colChar;

    setColor(player);
    setOpacity(1.0);
    isPut = true;
  }

  private void setColor(boolean player) {
    if (player)
      setFill(Color.BLACK);
    else
      setFill(Color.WHITE);
  }

  public void remove() {
    setOpacity(0.0);
    isPut = false;
  }

  public boolean getPlayer() {
    return player;
  }

  public char getRow() {
    return rowChar;
  }

  public char getCol() {
    return colChar;
  }

  public boolean isPut() {
    return isPut;
  }


}
