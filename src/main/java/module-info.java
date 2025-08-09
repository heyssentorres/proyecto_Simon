module com.example.proyecto_simon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.media;
    requires java.sql;

    exports app;
    opens app to javafx.fxml;

    exports model;
    opens model to javafx.base;

    exports controller;
    opens controller to javafx.fxml;
}