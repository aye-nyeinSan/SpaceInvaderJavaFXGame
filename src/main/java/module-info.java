module com.example.invaders {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.invaders to javafx.fxml;
    exports com.example.invaders;
}