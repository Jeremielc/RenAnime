package com.jeremielc.renanime.resources.fxml;

import com.jeremielc.renanime.fileManagement.FileManager;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jeremie
 */
public class RootLayoutController implements Initializable {

    @FXML
    private TextField animeName, animeFolderPath;
    @FXML
    private TextArea titlesArea;
    private final FileManager fm;
    private List<File> listOfAnimeFiles;

    public RootLayoutController() {
        fm = new FileManager();
        listOfAnimeFiles = null;
    }

    /**
     * Initializes the controller class.
     *
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
        listOfAnimeFiles = fm.retrieveAnimeFiles();

        if (listOfAnimeFiles != null) {
            animeFolderPath.setText(listOfAnimeFiles.get(0).getParent());
        }
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
        if (!animeName.getText().equals("") && !animeFolderPath.getText().equals("")) {
            System.out.println("RenAnime !");
            fm.renameAnimeFiles(animeName.getText(), listOfAnimeFiles, null);
            //Manage the case where number of title is different of number of files.
        } else {
            System.out.println("Please specifiy a path and a name for the anime.");
        }
    }
}
