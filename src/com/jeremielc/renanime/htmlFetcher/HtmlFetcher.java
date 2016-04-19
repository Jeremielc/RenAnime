package com.jeremielc.renanime.htmlFetcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

/**
 *
 * @author Jérémie Leclerc
 */
public class HtmlFetcher {
    
    public static final int MAX_ANIME_NUMBER = 33212;

    public HtmlFetcher() {

    }

    public void fetchContent(String baseUrl) {
        String animeNumber = retrieveAnimeNumber(baseUrl);

        File dbDir = new File("database/");
        if (!dbDir.exists()) {
            dbDir.mkdir();
        }

        if (dbDir.exists()) {
            File dbFile = new File("database/" + animeNumber + ".txt");

            try {
                URL url = new URL(baseUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int code = connection.getResponseCode();

                if (code == 200) {
                    BufferedReader br;
                    Boolean isThereEpisodeLink = false;
                    
                    try (FileWriter fw = new FileWriter(dbFile)) {
                        br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                        String readedLine;
                        while ((readedLine = br.readLine()) != null) {
                            if (!readedLine.isEmpty()) {
                                if (readedLine.contains("<title>")) {
                                    readedLine = br.readLine();

                                    fw.write("title : " + retrieveAnimeTitle(readedLine) + "\n");
                                    System.out.println("\ttitle : " + retrieveAnimeTitle(readedLine));

                                    while ((readedLine = br.readLine()) != null) {
                                        if (readedLine.contains("episode\">Episodes</a>")) {
                                            fw.write("episodes : " + retrieveAnimeEpisodes(readedLine) + "\n");
                                            System.out.println("\tepisodes : " + retrieveAnimeEpisodes(readedLine));
                                            isThereEpisodeLink = true;
                                            break;
                                        }
                                    }

                                    if (!isThereEpisodeLink) {
                                        fw.write("episodes : none\n");
                                        System.out.println("\tepisodes : none\n");
                                    }

                                    break;
                                }
                            }
                        }
                    }

                    br.close();
                }
            } catch (IOException ex) {
                System.err.println("Cannot create file for database.");
                ex.printStackTrace(System.err);
            }
        }
    }

    private String retrieveAnimeNumber(String url) {
        String animeNumber = null, previousToken;
        StringTokenizer st = new StringTokenizer(url, "/");

        do {
            previousToken = animeNumber;
            animeNumber = st.nextToken();
        } while (st.hasMoreTokens());
        /*{
            animeNumber = st.nextToken();
        }*/

        return previousToken;
    }

    private String retrieveAnimeEpisodes(String rawString) {
        StringTokenizer st = new StringTokenizer(rawString, "\"");
        String episodeLink = st.nextToken();
        episodeLink = st.nextToken();

        return episodeLink;
    }

    private String retrieveAnimeTitle(String rawString) {
        String cleanString = rawString.replace(" - MyAnimeList.net", "");
        cleanString = cleanString.trim();

        return cleanString;
    }
}
