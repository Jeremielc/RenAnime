package com.jeremielc.renanime.pojo;

import com.jeremielc.renanime.utils.NaturalOrderComparator;
import java.io.File;
import java.util.Comparator;

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
        NaturalOrderComparator noc = new NaturalOrderComparator();
        
        return noc.compare(this.name, animeFile.name);
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
