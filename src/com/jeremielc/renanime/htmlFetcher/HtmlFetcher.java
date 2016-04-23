package com.jeremielc.renanime.htmlFetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Allow to retrieve anime informations from MyAnimeList website.
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class HtmlFetcher {

    //public static final int MAX_ANIME_NUMBER = 33212;
    private final String baseUrl;
    private String animeId, animeTitle, animeEpUrl;
    private ArrayList<String> episodesList;
    private int episodeNumber;

    /**
     * Instanciate the HTML fetcher using the general MyAnimeList URL for the
     * anime.
     *
     * @param baseUrl An URL formatted like
     * http://myanimelist.net/anime/<ANIME_ID>/<ANIME_NAME>.
     */
    public HtmlFetcher(String baseUrl) {
        this.baseUrl = baseUrl;
        animeId = "";
        animeTitle = "";
        animeEpUrl = "";
        episodesList = new ArrayList<>();
        episodeNumber = 0;
        fetchContent();
    }

    /**
     * Retrieve the general informations (the title and the anime's episode URL
     * if it exists) from the MyAnimeList anime main page. Then, it
     * automatically call fetchTitles() to retrieve the number of episodes and
     * the episode list.
     *
     * @see fetchTitles()
     */
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
                                    animeEpUrl = retrieveAnimeEpisodesLink(readedLine);
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

    /**
     * Allow to retrieve the number of episodes and the episode list.
     * Automatically called by fetchContent().
     *
     * @see fetchContent();
     */
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
                                    episodesList.add(retrieveEpisodeTitle(readedLine));
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

    /**
     * Allow to get anime's episode link from MyAnimeList.
     *
     * @param rawString The line to parse, containing the episodes link.
     * @return
     */
    private String retrieveAnimeEpisodesLink(String rawString) {
        StringTokenizer st = new StringTokenizer(rawString, "\"");
        String episodeLink = st.nextToken();
        episodeLink = st.nextToken();

        animeEpUrl = episodeLink;

        return episodeLink;
    }
    
    /**
     * Parse the anime URL to get the anime identificaion number.
     *
     * @param url The URL to parse.
     * @return A string representing the anime Identifier.
     */
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

    /**
     * Allow to get the MyAnimeList anime title (Complete, with special
     * characters).
     *
     * @param rawString The line to parse.
     * @return A string representing the full anime name.
     */
    private String retrieveAnimeTitle(String rawString) {
        String cleanString = rawString.replace(" - MyAnimeList.net", "");
        cleanString = cleanString.trim();

        animeTitle = cleanString;

        return cleanString;
    }

    /**
     * Parse the line containing the number of episodes to get it.
     *
     * @param rawString The line to parse.
     * @return An integer representing the number of episodes.
     */
    private int retrieveEpisodeNumber(String rawString) {
        String epNumber = rawString;

        StringTokenizer st = new StringTokenizer(rawString, "(");
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

    /**
     * Parse the line containing an episode title to get it.
     *
     * @param rawString The line to parse.
     * @return A string representing the title.
     */
    private String retrieveEpisodeTitle(String rawString) {
        String title = rawString.replaceAll("</a>", "");
        StringTokenizer st = new StringTokenizer(title, ">");

        while (st.hasMoreTokens()) {
            title = st.nextToken();
        }

        title = title.replaceAll("&#039;", "'");
        title = title.replaceAll("[?]+", "!");
        title = title.replaceAll(":", "");

        return title;
    }

    /**
     * Allow to get the anime identifier.
     * @return The anime identifier.
     */
    public String getAnimeId() {
        return animeId;
    }

    /**
     * Allow to set the anime identifier.
     * @param animeId The anime identifier.
     */
    public void setAnimeId(String animeId) {
        this.animeId = animeId;
    }

    /**
     * Allow to get the anime title.
     * @return The anime title.
     */
    public String getAnimeTitle() {
        return animeTitle;
    }

    /**
     * Allow to set the anime title.
     * @param animeTitle The anime title.
     */
    public void setAnimeTitle(String animeTitle) {
        this.animeTitle = animeTitle;
    }

    /**
     * Allow to get the anime episodes URL.
     * @return The anime episodes URL.
     */
    public String getAnimeEpUrl() {
        return animeEpUrl;
    }

    /**
     * Allow to set the anime episodes URL.
     * @param animeEpUrl The anime episodes URL.
     */
    public void setAnimeEpUrl(String animeEpUrl) {
        this.animeEpUrl = animeEpUrl;
    }

    /**
     * Allow to get the episodes list.
     * @return The episodes list.
     */
    public ArrayList<String> getEpisodesList() {
        return episodesList;
    }

    /**
     * Allow to set the episodes list.
     * @param episodesList The episodes list.
     */
    public void setEpisodesList(ArrayList<String> episodesList) {
        this.episodesList = episodesList;
    }
}
