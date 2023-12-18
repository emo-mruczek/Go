module com.example.go {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.logging;


  opens com.example.go to javafx.fxml;
  exports com.example.go;
}