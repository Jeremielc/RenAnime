/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jeremielc.renanime.fileManagement;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;
import javafx.stage.FileChooser;

/**
 *
 * @author Jérémie Leclerc
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
    
    public void renameAnimeFiles(String animeName, List<File> listOfAnimeFiles, List<String> listOfTitles) {
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
            path += animeName + " - " + 
                    "Episode " + String.format("%0" + integerSizeMax + "d", i+1) + " - " + 
                    /*listOfTitles.get(i) + */"." + 
                    fileExtension;
            
            System.out.println("Path : " + path);
            
            dest = new File(path);
            listOfAnimeFiles.get(i).renameTo(dest);
        }
    }

}
