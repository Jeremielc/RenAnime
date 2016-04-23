package com.jeremielc.renanime.resources.fxml;

import com.jeremielc.renanime.fileManagement.FileManager;
import com.jeremielc.renanime.htmlFetcher.HtmlFetcher;
import com.jeremielc.renanime.pojo.Anime;
import com.jeremielc.renanime.titles.TitlesManager;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
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
 * FXML Controller class of RootLayout.
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class RootLayoutController implements Initializable {

    @FXML
    private TextField animeName, animeFolderPath;
    @FXML
    private TextArea titlesArea;
    private List<File> listOfAnimeFiles;
    private Window owner;

    public RootLayoutController() {
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

    }

    /**
     * Show the about window of the program.
     */
    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.setTitle("RenAnime - About");
        alert.setContentText("RenAnime v1.0 - Jérémie Leclerc <le.microarchitechte@gmail.com>\n\n"
                + "This software is provided under the GNU General Public Licence "
                + "(check the LICENSE text file to learn more about this).\n\n"
                + "This software is intended to rename files in bulk using user provided infos.\n\n"
                + "By providing an anime name, a list of files and a list of titles, "
                + "the software formats it following the scheme below : \n"
                + "\t\"Anime name - Episode xx - Episode title.ext\"");

        alert.showAndWait();
    }

    /**
     * Allow the user to select the files to rename.
     */
    @FXML
    public void handleBrowse() {
        FileManager fm = new FileManager();
        listOfAnimeFiles = fm.retrieveAnimeFiles();

        if (listOfAnimeFiles != null) {
            animeFolderPath.setText(listOfAnimeFiles.get(0).getParent());
        }
    }

    @FXML
    /**
     * Close the application.
     */
    public void handleClose() {
        Platform.exit();
    }

    /**
     * Show the help window of the program.
     */
    @FXML
    public void handleHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.setTitle("RenAnime - Help");
        alert.setContentText("To use this software, provide an anime name and a list of files to rename. "
                + "If You want, you can add a list of titles (separated by semicolon (';').\n\n"
                + "The files you provided before will be renamed following this scheme : \n"
                + "\t\"Anime name - Episode xx - Episode title.ext\"\n"
                + " or if no titles mentioned : \n"
                + "\t\"Anime name - Episode xx.ext\"\n\n"
                + "If there is less titles specified than files number, the firsts files will be renamed "
                + "following the first scheme and the others following the seccond scheme.");

        alert.showAndWait();
    }

    /**
     * Provide other classes with necessary informations to rename the files.
     * Proceed with those informations.
     */
    @FXML
    public void handleRenanime() {
        Anime anime = new Anime();
        FileManager fm = new FileManager();

        if (!animeName.getText().equals("") && !animeFolderPath.getText().equals("")) {
            anime.setAnimeTitle(animeName.getText().trim());
            Optional<ButtonType> result;

            if (titlesArea.getText().equals("")) {  //If there is no titles
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

                if (titlesArea.getText().contains("http://myanimelist.net/anime")) {  //If titles have to be fetch from my anime list website.
                    HtmlFetcher hf = new HtmlFetcher(titlesArea.getText());
                    
                    anime.setAnimeId(hf.getAnimeId());
                    anime.setAnimeTitle(hf.getAnimeTitle());
                    anime.setEpisodeUrl(hf.getAnimeEpUrl());
                    anime.setEpisodeList(hf.getEpisodeList());

                    String fullTitleString = "";
                    Iterator iter = anime.getEpisodeList().iterator();
                    while (iter.hasNext()) {
                        fullTitleString += iter.next() + "\n";
                    }

                    titlesArea.setText(fullTitleString);
                } else {
                    anime.setEpisodeList(tm.retrieveTitleList(titlesArea.getText()));
                }

                if (anime.getEpisodeList() != null) {
                    if (anime.getEpisodeList().size() < listOfAnimeFiles.size()) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initOwner(owner);
                        alert.setTitle("Verify data.");
                        alert.setHeaderText("It appears that the number of titles is different of the number of episodes.");
                        alert.setContentText("Click 'Cancel' to check your informations or 'OK' to proceed without missing titles.");

                        result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            anime.setEpisodeList(tm.alignTitleNumber(anime.getEpisodeList(), listOfAnimeFiles.size()));
                            fm.renameAnimeFiles(animeName.getText().trim(), listOfAnimeFiles, anime.getEpisodeList(), true);
                        }
                    } else {
                        fm.renameAnimeFiles(animeName.getText().trim(), listOfAnimeFiles, anime.getEpisodeList(), true);
                    }
                }
            }
        } else {
            System.err.println("Please specifiy a path and a name for the anime.");
        }
    }

    public void setOwner(Window owner) {
        this.owner = owner;
    }
}
