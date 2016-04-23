package com.jeremielc.renanime.pojo;

import java.util.ArrayList;

/**
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class Anime {
    private String AnimeTitle, animeId, episodeUrl;
    private ArrayList<String> episodeList;

    public Anime(String AnimeTitle, String animeId, String episodeUrl, ArrayList<String> episodeList) {
        this.AnimeTitle = AnimeTitle;
        this.animeId = animeId;
        this.episodeUrl = episodeUrl;
        this.episodeList = episodeList;
    }

    public Anime() {
        
    }
    
    public String getAnimeTitle() {
        return AnimeTitle;
    }

    public void setAnimeTitle(String AnimeTitle) {
        this.AnimeTitle = AnimeTitle;
    }

    public String getAnimeId() {
        return animeId;
    }

    public void setAnimeId(String animeId) {
        this.animeId = animeId;
    }

    public String getEpisodeUrl() {
        return episodeUrl;
    }

    public void setEpisodeUrl(String episodeLink) {
        this.episodeUrl = episodeLink;
    }

    public ArrayList<String> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(ArrayList<String> episodeList) {
        this.episodeList = episodeList;
    }
}
