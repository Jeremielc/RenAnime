package com.jeremielc.renanime;

import com.jeremielc.renanime.resources.fxml.RootLayoutController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Initializes the main javaFX window.
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

            RootLayoutController rlc = loader.getController();
            rlc.setOwner(primaryStage);

            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/images/icon.png")));
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
