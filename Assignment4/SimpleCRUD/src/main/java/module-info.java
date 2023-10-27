module com.ehall.simplecrud {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.ehall.simplecrud to javafx.fxml;
    exports com.ehall.simplecrud;
}