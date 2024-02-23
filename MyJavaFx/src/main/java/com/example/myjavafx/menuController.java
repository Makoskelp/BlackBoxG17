package com.example.myjavafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class menuController extends Application {

    private static Stage menuStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        menuStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

        primaryStage.setTitle("BlackBox+");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    private TextField myTextField;

    public static void main(String[] args) {
        launch();
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        int size = Integer.parseInt(myTextField.getText());
        menuStage.close();
        HelloApplication board1 = new HelloApplication(size);
        board1.start(new Stage());
    }
}