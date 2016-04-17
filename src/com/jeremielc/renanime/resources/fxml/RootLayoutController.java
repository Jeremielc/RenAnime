package com.jeremielc.renanime.resources.fxml;

import com.jeremielc.renanime.fileManagement.FileManager;
import com.jeremielc.renanime.utils.TitlesManager;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class RootLayoutController implements Initializable {

    @FXML
    private TextField animeName, animeFolderPath;
    @FXML
    private TextArea titlesArea;
    private final FileManager fm;
    private List<File> listOfAnimeFiles;
    private Window owner;

    public void setOwner(Window owner) {
        this.owner = owner;
    }

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
        alert.initOwner(owner);
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
        alert.initOwner(owner);
        alert.setTitle("RenAnime - Help");
        alert.setContentText("Not implemented yet.");

        alert.showAndWait();
    }

    @FXML
    public void handleRenanime() {
        if (!animeName.getText().equals("") && !animeFolderPath.getText().equals("")) {
            Optional<ButtonType> result;

            if (titlesArea.getText().equals("")) {  //If ther is no titles
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(owner);
                alert.setTitle("Verify data.");
                alert.setHeaderText("It appears that no titles where mentioned.");
                alert.setContentText("Click 'Cancel' to check your informations or 'OK' to proceed without titles.");

                result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    fm.renameAnimeFiles(animeName.getText().trim(), listOfAnimeFiles, null, false);
                }
            } else if (!titlesArea.getText().equals("")) {
                TitlesManager tm = new TitlesManager();
                List<String> titles = tm.retrieveTitleList(titlesArea.getText());

                if (titles.size() < listOfAnimeFiles.size()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initOwner(owner);
                    alert.setTitle("Verify data.");
                    alert.setHeaderText("It appears that the number of titles is different of the number of episodes.");
                    alert.setContentText("Click 'Cancel' to check your informations or 'OK' to proceed without missing titles.");

                    result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        titles = tm.alignTitleNumber(titles, listOfAnimeFiles.size());
                        fm.renameAnimeFiles(animeName.getText().trim(), listOfAnimeFiles, titles, true);
                    }
                } else {
                    fm.renameAnimeFiles(animeName.getText().trim(), listOfAnimeFiles, titles, true);
                }
            }
        } else {
            System.out.println("Please specifiy a path and a name for the anime.");
        }
    }
}
