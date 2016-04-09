package com.jeremielc.renanime.resources.fxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author jeremie
 */
public class RootLayoutController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RenAnime - About");
        alert.setContentText("Not implemented yet.");
        
        alert.showAndWait();
    }
    
    @FXML
    public void handleBrowse() {
        System.out.println("Browse");
    }
    
    @FXML
    public void handleClose() {
        Platform.exit();
    }
    
    @FXML
    public void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RenAnime - Help");
        alert.setContentText("Not implemented yet.");
        
        alert.showAndWait();
    }
    
    @FXML
    public void handleRenanime() {
        System.out.println("RenAnime !");
    }
}
