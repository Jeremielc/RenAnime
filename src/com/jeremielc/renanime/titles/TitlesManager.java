package com.jeremielc.renanime.titles;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class is used to manage titles. It allow to retrieve titles for each
 * episodes and manage the length of provided list to fit with anime's episodes
 * number.
 *
 * @author jeremielc : le.microarchitechte@gmail.com
 */
public class TitlesManager {

    public TitlesManager() {

    }

    /**
     * Parse a string containing all the titles separated by semicolon and
     * return a list of titles string formated.
     *
     * @param fullTitleString A string in the form "title_1;title_2;..."
     * @return A list of string containing all the provided titles.
     */
    public ArrayList<String> retrieveTitleList(String fullTitleString) {
        ArrayList<String> listOfTitles = new ArrayList<>();

        fullTitleString = fullTitleString.replace("\r", "");

        StringTokenizer st = new StringTokenizer(fullTitleString, "\n");

        while (st.hasMoreElements()) {
            listOfTitles.add(st.nextToken().trim());
        }

        return listOfTitles;
    }

    /**
     * Create a new List containing all titles in the previous list plus empty
     * titles ("") so that the list length matches the number of episodes.
     *
     * @param titleList The previous list with not enought titles.
     * @param episodeNumber The number of episodes (aka the number of selected
     * files.
     * @return A new List with empty titles where they were missing.
     */
    public ArrayList<String> alignTitleNumber(ArrayList<String> titleList, int episodeNumber) {
        ArrayList<String> newTitleList = new ArrayList<>();

        for (int i = 0; i < episodeNumber; i++) {
            if (i < titleList.size()) {
                newTitleList.add(titleList.get(i));
            } else {
                newTitleList.add("");
            }
        }

        return newTitleList;
    }
}
