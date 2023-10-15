module com.example.invaders {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;




    opens com.example.invaders to javafx.fxml;
    exports com.example.invaders;
}