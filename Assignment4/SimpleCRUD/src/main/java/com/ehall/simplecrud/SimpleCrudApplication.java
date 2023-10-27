package com.ehall.simplecrud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SimpleCrudApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SimpleCrudApplication.class.getResource("crud-editor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Music Album Table Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
