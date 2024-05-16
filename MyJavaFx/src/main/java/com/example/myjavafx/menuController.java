package com.example.myjavafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

//this class is to create and run the start menu

public class menuController extends Application {

    //boardStage is the javafx stage that is used to display the menu
    private static Stage boardStage;

    //start function is the first function ran
    @Override
    public void start(Stage newStage) throws Exception {
        //accesses the menu.fxml file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));

        boardStage = newStage;
        boardStage.setTitle("BlackBox+");
        boardStage.setScene(new Scene(root));
        boardStage.show();
    }

    @FXML
    private TextField myTextField;

    //main method should be ignored if the javafx file is set up correct
    //start method should always run first
    public static void main(String[] args) {
        launch();
    }

    //this function is called when the user presses enter
    @FXML
    public void onEnter(ActionEvent ae) {
        //size takes the values of the myTextField which is set by the user
        int size = Integer.parseInt(myTextField.getText());
        //closes the current stage
        if(size >= 4 && size <= 7)
        {
            boardStage.close();
            BlackBoxApplication board1 = new BlackBoxApplication(size);
            //starts the display of the board
            board1.start(new Stage());
        }
    }
}