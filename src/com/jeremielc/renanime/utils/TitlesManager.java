package com.jeremielc.renanime.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
* @author jeremielc : le.microarchitechte@gmail.com
 */
public class TitlesManager {

    public TitlesManager() {
        
    }
    
    public List<String> retrieveTitleList (String fullTitleString) {
        List<String> listOfTitles = new ArrayList<>();
        
        fullTitleString = fullTitleString.replace("\r", "");
        fullTitleString = fullTitleString.replace("\n", "");
        
        StringTokenizer st = new StringTokenizer(fullTitleString, ";");
        
        while (st.hasMoreElements()) {
            listOfTitles.add(st.nextToken().trim());
        }
        
        return listOfTitles;
    }
    
    public List<String> alignTitleNumber (List<String> titleList, int episodeNumber) {
        List<String> newTitleList = new ArrayList<>();
        
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
