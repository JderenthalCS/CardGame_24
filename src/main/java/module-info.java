module com.example.cardgame_24 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cardgame_24 to javafx.fxml;
    exports com.example.cardgame_24;
}