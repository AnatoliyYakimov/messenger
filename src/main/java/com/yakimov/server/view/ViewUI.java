package com.yakimov.server.view;

import com.yakimov.server.presenter.Presenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewUI extends Application {

    private ViewController controller;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SampleChat");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/ViewUI.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root, 640, 400));
        stage.setHeight(400);
        stage.setWidth(640);
        Presenter presenter = new Presenter(new ViewInterface(this));
        stage.show();

    }

    ViewController getController() {
        return controller;
    }
}


