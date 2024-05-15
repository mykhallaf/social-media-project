module socialmediaproject.gui {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens socialmediaproject.gui to javafx.fxml;
    exports socialmediaproject.gui;
}