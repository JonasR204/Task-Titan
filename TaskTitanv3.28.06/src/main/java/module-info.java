module com.tasktitan.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens com.tasktitan.gui to javafx.fxml;
    opens com.tasktitan.gui.controllers to javafx.fxml;

    opens com.tasktitan.gui.navigation to javafx.fxml;

    exports com.tasktitan.gui;
    exports com.tasktitan.gui.controllers;
    opens model to javafx.base;

    exports com.tasktitan.gui.navigation;
}