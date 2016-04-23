package com.jeremielc.renanime.htmlFetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class HtmlFetcher {

    //public static final int MAX_ANIME_NUMBER = 33212;
    private final String baseUrl;
    private String animeId, animeTitle, animeEpUrl;
    private ArrayList<String> episodeList;
    private int episodeNumber;

    public HtmlFetcher(String baseUrl) {
        this.baseUrl = baseUrl;
        animeId = "";
        animeTitle = "";
        animeEpUrl = "";
        episodeList = new ArrayList<>();
        episodeNumber = 0;
        fetchContent();
    }

    private void fetchContent() {
        animeId = retrieveAnimeIdentifier(baseUrl);

        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();

            if (code == 200) {
                BufferedReader br;
                Boolean isThereEpisodeLink = false;

                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String readedLine;
                while ((readedLine = br.readLine()) != null) {
                    if (!readedLine.isEmpty()) {
                        if (readedLine.contains("<title>")) {
                            readedLine = br.readLine();

                            animeTitle = retrieveAnimeTitle(readedLine);

                            while ((readedLine = br.readLine()) != null) {
                                if (readedLine.contains("episode\">Episodes</a>")) {
                                    animeEpUrl = retrieveAnimeEpisodes(readedLine);
                                    isThereEpisodeLink = true;
                                    break;
                                }
                            }

                            if (!isThereEpisodeLink) {
                                animeEpUrl = "none";
                            }

                            break;
                        }
                    }
                }

                br.close();
            }
        } catch (IOException ex) {
            System.err.println("Cannot open link. Check your informations.");
            ex.printStackTrace(System.err);
        }

        fetchTitles();
    }

    private void fetchTitles() {
        try {
            URL url = new URL(animeEpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();

            if (code == 200) {
                try {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String readedLine;
                        while ((readedLine = br.readLine()) != null) {
                            if (readedLine.contains("Episodes<span class=") && readedLine.endsWith(")</span>")) {
                                episodeNumber = retrieveEpisodeNumber(readedLine);
                                break;
                            }
                        }
                        
                        int numFetchedEpisodeTitle = 0;
                        while ((readedLine = br.readLine()) != null) {
                            if (numFetchedEpisodeTitle < episodeNumber) {
                                if (readedLine.contains("<td class=\"episode-title\">") && readedLine.contains("<a href=\"" + animeEpUrl) && (readedLine.endsWith("</a>"))) {
                                    episodeList.add(retrieveEpisodeTitle(readedLine));
                                    numFetchedEpisodeTitle++;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                } catch (IOException ex) {
                    System.err.println("Cannot read data from html connection.");
                    ex.printStackTrace(System.err);
                }
            }
        } catch (IOException ex) {
            System.err.println("Cannot open html connection.");
            ex.printStackTrace(System.err);
        }
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

    private String retrieveAnimeIdentifier(String url) {
        String id = null, previousToken;
        StringTokenizer st = new StringTokenizer(url, "/");

        do {
            previousToken = id;
            id = st.nextToken();
        } while (st.hasMoreTokens());

        id = previousToken;

        return id;
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

    public ArrayList<String> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(ArrayList<String> episodeList) {
        this.episodeList = episodeList;
    }
}
