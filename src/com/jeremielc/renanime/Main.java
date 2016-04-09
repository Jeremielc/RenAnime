package com.jeremielc.renanime;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("resources/fxml/RootLayout.fxml"));

        try {
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            
            primaryStage.setTitle("RenAnime");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
