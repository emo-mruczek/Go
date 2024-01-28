package com.example.go;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.logging.Level;

public final class BoardDrawer {
  private static double cellWidth;
  private static double cellHeight;

  private BoardDrawer() {
    throw new InstantiationError("BoardDrawer is a full static class!");
  }

  public static void insertImages(final GridPane gp, final int size) {
    MyLogger.logger.log(Level.INFO, "Inserting images.");
    cellWidth = gp.getWidth() / size;
    cellHeight = gp.getHeight() / size;
    String path = "C:/Users/krokc/Desktop/tp/";
    for (int row = 1; row < size - 1; row++) {
      for (int col = 1; col < size - 1; col++) {
        addImageToCell(gp, path + "s.png", col, row);
      }
    }
    for (int col = 1; col < size - 1; col++) {
      addImageToCell(gp, path + "g.png", col, 0);
      addImageToCell(gp, path + "d.png", col, size - 1);
    }
    for (int row = 1; row < size - 1; row++) {
      addImageToCell(gp, path + "l.png", 0, row);
      addImageToCell(gp, path + "p.png", size - 1, row);
    }
    addImageToCell(gp, path + "gl.png", 0, 0);
    addImageToCell(gp, path + "gp.png", size - 1, 0);
    addImageToCell(gp, path + "dl.png", 0, size - 1);
    addImageToCell(gp, path + "dp.png", size - 1, size - 1);
  }

  private static void addImageToCell(final GridPane gp, final String imagePath, final int col, final int row) {
    Image image = new Image(imagePath);
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(cellWidth);
    imageView.setFitHeight(cellHeight);
    gp.add(imageView, col, row);
  }
}
