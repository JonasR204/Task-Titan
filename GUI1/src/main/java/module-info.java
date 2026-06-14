module com.example.gui1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gui1 to javafx.fxml;
    exports com.example.gui1;
}