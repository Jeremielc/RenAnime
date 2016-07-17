package com.jeremielc.renanime.fileManagement;

import com.jeremielc.renanime.pojo.Anime;
import com.jeremielc.renanime.pojo.AnimeFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
import javafx.stage.FileChooser;

/**
 * This class contains all methods to manage files (retrieve a list of files and
 * rename them).
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class FileManager {

    private File directoryPath;

    /**
     * Instanciate the file manager with a directory path set by default to the
     * user home directory.
     */
    public FileManager() {
        directoryPath = new File(System.getProperty("user.home"));
    }

    /**
     * Let the user choose multiple files to rename.
     *
     * @return A list of files, selected by the user.
     */
    public TreeSet<AnimeFile> retrieveAnimeFiles() {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(directoryPath);
        chooser.setTitle("Select anime's files.");

        List<File> listOfAnimeFiles = chooser.showOpenMultipleDialog(null);

        if (listOfAnimeFiles != null) {
            directoryPath = listOfAnimeFiles.get(0);
            
            TreeSet<AnimeFile> animeFileList = new TreeSet<>();
            for (File f : listOfAnimeFiles) {
                animeFileList.add(new AnimeFile(f));
            }
            
            return animeFileList;
        } else {
            return null;
        }
    }

    /**
     * Allow to rename files in bulk following the format "Anime name - Episode
     * xx - Episode title.ext"
     *
     * @param animeName A string representing the name of the anime.
     * @param listOfAnimeFiles The list of file to rename.
     * @param listOfTitles List of episodes titles.
     * @param isThereTitles Indicate if titles must be writen or not. True if
     * titles must be present in the file name, false otherwise.
     */
    public void renameAnimeFiles(String animeName, TreeSet<AnimeFile> listOfAnimeFiles, List<String> listOfTitles, boolean isThereTitles) {
        File dest;
        StringTokenizer st;
        String path, fileExtension = "";
        int episodeCount = listOfAnimeFiles.size();
        int integerSizeMax = String.valueOf(episodeCount).length();

        int i = 0;
        Iterator<AnimeFile> iter = listOfAnimeFiles.iterator();
        
        while (iter.hasNext()) {
            AnimeFile iteration = iter.next();
            st = new StringTokenizer(iteration.getName(), ".");
            while (st.hasMoreTokens()) {
                fileExtension = st.nextToken();
            }

            path = iteration.getAnimeFile().getParent() + File.separator;
            if (isThereTitles) {
                path += animeName + " - " + "Episode " + String.format("%0" + integerSizeMax + "d", i + 1);

                if (!listOfTitles.get(i).equals("")) {
                    path += " - " + listOfTitles.get(i) + "." + /*iteration.getAnimeFile().getName();// + */fileExtension;
                } else {
                    path += "." + /*iteration.getAnimeFile().getName();// + */fileExtension;
                }
            } else {
                path += animeName + " - "
                        + "Episode " + String.format("%0" + integerSizeMax + "d", i + 1) + "."
                        + /*iteration.getAnimeFile().getName();// + */fileExtension;
            }

            dest = new File(path);
            iteration.getAnimeFile().renameTo(dest);
            
            i++;
        }
    }

    /**
     * Allow to get the anime's title and the url where to find episodes titles
     * from a file.
     *
     * @param animeId A string representing the MyAnimeList ID number of the
     * episode.
     * @param anime An anime object to store data.
     */
    public void retrieveAnimeData(String animeId, Anime anime) {
        try {
            File textFile = new File("fetched_files/" + animeId + ".txt");

            if (textFile.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
                    anime.setAnimeTitle(br.readLine().replace("title : ", "").trim());
                    anime.setEpisodesUrl(br.readLine().replace("episodes : ", "").trim());
                }
            } else {
                System.err.println("Unable to load " + textFile.getName());
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
