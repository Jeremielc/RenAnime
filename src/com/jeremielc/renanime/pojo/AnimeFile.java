package com.jeremielc.renanime.pojo;

import com.jeremielc.renanime.utils.NaturalOrderComparator;
import java.io.File;

/**
 * This class contains the path to an anime file an the name of this file. It
 * call comparison method from NaturalOrderComparator class.
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 * @see NaturalOrderComparator
 */
public class AnimeFile implements Comparable<AnimeFile> {

    private String name;
    private File animeFile;

    /**
     * Instanciate an AnimeFile object with the path to the anime file and its
     * name.
     *
     * @param animeFile The anime File.
     */
    public AnimeFile(File animeFile) {
        this.animeFile = animeFile;
        if (animeFile != null) {
            this.name = animeFile.getName();
        }
    }

    /**
     * Implemented method. Allow the TreeSet to order files by name.
     *
     * @param animeFile An AnimeFile object.
     * @return The name's associated weight of AnimeFile's name (+1 or -1).
     * @see java.util.TreeSet
     */
    @Override
    public int compareTo(AnimeFile animeFile) {
        NaturalOrderComparator noc = new NaturalOrderComparator();

        return noc.compare(this.name, animeFile.name);
    }

    /**
     * Allow to get the anime file name.
     *
     * @return The anime file name.
     */
    public String getName() {
        return name;
    }

    /**
     * Allow to set the anime file name.
     *
     * @param name The anime file name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Allow to get the anime file.
     *
     * @return The anime file.
     */
    public File getAnimeFile() {
        return animeFile;
    }

    /**
     * Allow to set the anime file.
     *
     * @param animeFile The anime file.
     */
    public void setAnimeFile(File animeFile) {
        this.animeFile = animeFile;
    }
}
