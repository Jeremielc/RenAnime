package com.jeremielc.renanime.fileManagement;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;
import javafx.stage.FileChooser;

/**
 *
* @author jeremielc : le.microarchitechte@gmail.com
 */
public class FileManager {

    private File directoryPath;

    public FileManager() {
        directoryPath = new File(System.getProperty("user.home"));
    }

    public List<File> retrieveAnimeFiles() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(directoryPath);
        chooser.setTitle("Select anime's files.");

        List<File> listOfAnimeFiles = chooser.showOpenMultipleDialog(null);

        if (listOfAnimeFiles != null) {
            directoryPath = listOfAnimeFiles.get(0);
            return listOfAnimeFiles;
        } else {
            return null;
        }
    }

    public void renameAnimeFiles(String animeName, List<File> listOfAnimeFiles, List<String> listOfTitles, boolean isThereTitles) {
        File dest;
        StringTokenizer st;
        String path, fileExtension = "";
        int episodeCount = listOfAnimeFiles.size();
        int integerSizeMax = String.valueOf(episodeCount).length();

        for (int i = 0; i < listOfAnimeFiles.size(); i++) {
            st = new StringTokenizer(listOfAnimeFiles.get(i).getName(), ".");
            while (st.hasMoreTokens()) {
                fileExtension = st.nextToken();
            }

            path = listOfAnimeFiles.get(i).getParent() + File.separator;
            if (isThereTitles) {
                path += animeName + " - " + "Episode " + String.format("%0" + integerSizeMax + "d", i + 1);
                
                if (!listOfTitles.get(i).equals("")) {
                    path += " - " + listOfTitles.get(i) + "." + fileExtension;
                } else {
                    path += "." + fileExtension;
                }
            } else {
                path += animeName + " - "
                        + "Episode " + String.format("%0" + integerSizeMax + "d", i + 1) + "."
                        + fileExtension;
            }

            System.out.println("Path : " + path);

            dest = new File(path);
            listOfAnimeFiles.get(i).renameTo(dest);
        }
    }

}
