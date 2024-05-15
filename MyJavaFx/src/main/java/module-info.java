module com.example.myjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires junit;


    opens com.example.myjavafx to javafx.fxml;
    exports com.example.myjavafx;
}