module com.socketprogramming.atestat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;

    opens com.socketprogramming.atestat to javafx.fxml;
    exports com.socketprogramming.atestat;
}