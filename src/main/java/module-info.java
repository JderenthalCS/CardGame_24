module com.example.cardgame_24 {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;
    requires javafx.media;


    opens com.example.cardgame_24 to javafx.fxml;
    exports com.example.cardgame_24;
}