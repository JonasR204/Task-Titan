module com.tasktitan.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.tasktitan.gui to javafx.fxml;
    opens com.tasktitan.gui.controllers to javafx.fxml;
    opens com.tasktitan.gui.components to javafx.fxml;
    opens com.tasktitan.gui.navigation to javafx.fxml;

    exports com.tasktitan.gui;
    exports com.tasktitan.gui.controllers;
    exports com.tasktitan.gui.components;
    exports com.tasktitan.gui.navigation;
}