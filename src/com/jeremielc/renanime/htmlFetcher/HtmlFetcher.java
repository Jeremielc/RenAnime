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
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class HtmlFetcher {

    public static final int MAX_ANIME_NUMBER = 33212;
    private final String baseUrl;
    private String animeId, animeTitle, animeEpUrl;

    public HtmlFetcher(String baseUrl) {
        this.baseUrl = baseUrl;
        fetchContent();
    }

    private void fetchContent() {
        String animeId = retrieveAnimeIdentifier(baseUrl);

        File dbDir = new File("fetched_files/");
        if (!dbDir.exists()) {
            dbDir.mkdir();
        }

        if (dbDir.exists()) {
            File dbFile = new File("fetched_files/" + animeId + ".txt");

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
                System.err.println("Cannot create file for fetched_files.");
                ex.printStackTrace(System.err);
            }
        }
    }

    private String retrieveAnimeIdentifier(String url) {
        String animeId = null, previousToken;
        StringTokenizer st = new StringTokenizer(url, "/");

        do {
            previousToken = animeId;
            animeId = st.nextToken();
        } while (st.hasMoreTokens());

        animeId = previousToken;
        return animeId;
    }

    private String retrieveAnimeEpisodes(String rawString) {
        StringTokenizer st = new StringTokenizer(rawString, "\"");
        String episodeLink = st.nextToken();
        episodeLink = st.nextToken();

        animeEpUrl = episodeLink;
        return episodeLink;
    }

    private String retrieveAnimeTitle(String rawString) {
        String cleanString = rawString.replace(" - MyAnimeList.net", "");
        cleanString = cleanString.trim();

        animeTitle = cleanString;
        return cleanString;
    }

    public String getAnimeId() {
        return animeId;
    }

    public void setAnimeId(String animeId) {
        this.animeId = animeId;
    }

    public String getAnimeTitle() {
        return animeTitle;
    }

    public void setAnimeTitle(String animeTitle) {
        this.animeTitle = animeTitle;
    }

    public String getAnimeEpUrl() {
        return animeEpUrl;
    }

    public void setAnimeEpUrl(String animeEpUrl) {
        this.animeEpUrl = animeEpUrl;
    }
    
    
}
