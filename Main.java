package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main program, this launches the application
 */

public class Main extends Application {

    /**@param primaryStage present the main stage
     * @throws Exception for calling internal file*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        primaryStage.setTitle("First Screen");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    /**@param args launch program*/
    public static void main(String[] args){

        launch(args);
    }
}
