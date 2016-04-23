package com.jeremielc.renanime.pojo;

import java.util.ArrayList;

/**
 * An class dedicated to contain anime parameters.
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class Anime {

    private String AnimeTitle, animeId, episodesUrl;
    private ArrayList<String> episodesList;

    /**
     * Instanciate an empty anime object.
     */
    public Anime() {

    }

    /**
     * Instanciate an anime object with the provided informations.
     *
     * @param AnimeTitle The anime title.
     * @param animeId The anime identifier.
     * @param episodesUrl The anime episodes URL
     * @param episodesList The anime episodes list.
     */
    public Anime(String AnimeTitle, String animeId, String episodesUrl, ArrayList<String> episodesList) {
        this.AnimeTitle = AnimeTitle;
        this.animeId = animeId;
        this.episodesUrl = episodesUrl;
        this.episodesList = episodesList;
    }

    /**
     * Allow to get the anime's name.
     *
     * @return The anime's name.
     */
    public String getAnimeTitle() {
        return AnimeTitle;
    }

    /**
     * Allow to set anime's title.
     *
     * @param AnimeTitle The anime's title.
     */
    public void setAnimeTitle(String AnimeTitle) {
        this.AnimeTitle = AnimeTitle;
    }

    /**
     * Allow to get the anime's identifier.
     *
     * @return The anime's identifier.
     */
    public String getAnimeId() {
        return animeId;
    }

    /**
     * Allow to set the anime's identifier.
     *
     * @param animeId The anime's identifier.
     */
    public void setAnimeId(String animeId) {
        this.animeId = animeId;
    }

    /**
     * Allow to get the anime's episodes URL.
     *
     * @return The anime's episodes URL.
     */
    public String getEpisodesUrl() {
        return episodesUrl;
    }

    /**
     * Allow to set the anime's episodes URL.
     *
     * @param episodeLink The anime's episodes URL.
     */
    public void setEpisodesUrl(String episodeLink) {
        this.episodesUrl = episodeLink;
    }

    /**
     * Allow to get the anime's episodes list.
     *
     * @return The anime's episodes list.
     */
    public ArrayList<String> getEpisodesList() {
        return episodesList;
    }

    /**
     * Allow to set the anime's episodes list.
     *
     * @param episodesList The anime's episodes list.
     */
    public void setEpisodesList(ArrayList<String> episodesList) {
        this.episodesList = episodesList;
    }
}
