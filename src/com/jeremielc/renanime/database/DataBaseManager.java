package com.jeremielc.renanime.database;

import com.jeremielc.renanime.utils.PercentageCalculator;
import com.jeremielc.renanime.htmlFetcher.HtmlFetcher;

/**
 * Reserved for future use.
 *
 * @author Jérémie Leclerc
 */
public class DataBaseManager {

    public DataBaseManager() {

    }

    public void updateDataBase() {
        String baseUrl = "http://myanimelist.net/anime/";
        HtmlFetcher htmlFetcher = new HtmlFetcher();

        PercentageCalculator pc = new PercentageCalculator(1, HtmlFetcher.MAX_ANIME_NUMBER);
        String lastPercentage = "000.000 %";
        String actualPercentage = "000.000 %";

        String tempUrl;
        for (int i = 1; i <= HtmlFetcher.MAX_ANIME_NUMBER; i++) {
            tempUrl = baseUrl + i;
            htmlFetcher.fetchContent(tempUrl);

            actualPercentage = pc.computeProgress(i);
            if (actualPercentage != lastPercentage) {
                lastPercentage = actualPercentage;
                System.out.println(actualPercentage);
            }
        }
    }
}
