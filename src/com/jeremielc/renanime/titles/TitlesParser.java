package com.jeremielc.renanime.titles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Jérémie Leclerc
 */
public class TitlesParser {

    private final String animeName;
    private final String titleUrl;
    private int episodeNumber;

    public TitlesParser(String animeName, String titleUrl) {
        this.animeName = animeName;
        this.titleUrl = titleUrl;
        fetchTitles();
    }

    private void fetchTitles() {
        File dbDir = new File("fetched_files/");
        if (!dbDir.exists()) {
            dbDir.mkdir();
        }

        File titleFile = new File("fetched_files/" + animeName + ".html");

        if (dbDir.exists()) {
            try {
                URL url = new URL(titleUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int code = connection.getResponseCode();

                if (code == 200) {
                    try (FileWriter fw = new FileWriter(titleFile)) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        String readedLine;
                        while ((readedLine = br.readLine()) != null) {
                            fw.write(readedLine + "\n");
                        }
                        
                        br.close();
                    }
                }
            } catch (IOException ex) {
                System.err.println("Cannot create file for titles.");
                ex.printStackTrace(System.err);
            }
        }
    }

    public ArrayList<String> parseTitles() {
        File titleFile = new File("fetched_files/" + animeName + ".html");
        ArrayList<String> titles = new ArrayList<>();

        if (titleFile.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(titleFile));

                String readedLine;
                while ((readedLine = br.readLine()) != null) {
                    if (readedLine.contains("Episodes<span class=") && readedLine.endsWith(")</span>")) {
                        episodeNumber = retrieveEpisodeNumber(readedLine);
                        System.out.println("episode number : " + episodeNumber);
                        break;
                    }
                }

                int numFetchedEpisodeTitle = 0;
                while ((readedLine = br.readLine()) != null) {
                    if (numFetchedEpisodeTitle < episodeNumber) {
                        if (readedLine.contains("<td class=\"episode-title\">") && readedLine.contains("<a href=\"" + titleUrl) && (readedLine.endsWith("</a>"))) {
                            titles.add(retrieveEpisodeTitle(readedLine));
                            numFetchedEpisodeTitle++;
                        }
                    } else {
                        break;
                    }
                }
                
                br.close();
            } catch (IOException ex) {
                System.err.println("Cannot open file.");
                ex.printStackTrace(System.err);
            }
        } else {
            System.err.println("File " + titleFile.getName() + " does not exist.");
        }
        
        return titles;
    }

    private int retrieveEpisodeNumber(String line) {
        String epNumber = line;

        StringTokenizer st = new StringTokenizer(line, "(");
        while (st.hasMoreTokens()) {
            epNumber = st.nextToken();
        }

        epNumber = epNumber.replaceAll("</span>", "");

        st = new StringTokenizer(epNumber, "/");
        while (st.hasMoreTokens()) {
            epNumber = st.nextToken();
        }

        epNumber = epNumber.replace(")", "");

        int epNum = Integer.parseInt(epNumber);

        return epNum;
    }

    private String retrieveEpisodeTitle(String line) {
        String title = line.replaceAll("</a>", "");
        StringTokenizer st = new StringTokenizer(title, ">");

        while (st.hasMoreTokens()) {
            title = st.nextToken();
        }
        
        title = title.replaceAll("&#039;", "'");
        title = title.replaceAll("[?]+", "!");
        title = title.replaceAll(":", "");
        
        return title;
    }
}
