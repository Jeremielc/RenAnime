package com.jeremielc.renanime.pojo;

import java.io.File;

/**
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class AnimeFile implements Comparable<AnimeFile> {

    private String name;
    private File animeFile;

    public AnimeFile(File animeFile) {
        this.animeFile = animeFile;
        if (animeFile != null) {
            this.name = animeFile.getName();
        }
    }

    @Override
    public int compareTo(AnimeFile animeFile) {
        return name.compareToIgnoreCase(animeFile.getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getAnimeFile() {
        return animeFile;
    }

    public void setAnimeFile(File animeFile) {
        this.animeFile = animeFile;
    }
}
