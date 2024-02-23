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

    private static Stage boardStage;

    @Override
    public void start(Stage newStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));

        boardStage = newStage;
        boardStage.setTitle("BlackBox+");
        boardStage.setScene(new Scene(root));
        boardStage.show();
    }

    @FXML
    private TextField myTextField;

    public static void main(String[] args) {
        launch();
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        int size = Integer.parseInt(myTextField.getText());
        boardStage.close();
        BlackBoxApplication board1 = new BlackBoxApplication(size);
        board1.start(new Stage());
    }
}