module org.example.przetwarzanieobrazow2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.przetwarzanieobrazow2 to javafx.fxml;
    exports org.example.przetwarzanieobrazow2;
}