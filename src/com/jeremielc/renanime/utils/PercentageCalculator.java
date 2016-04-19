package com.jeremielc.renanime.utils;

/**
 * Reserved for future use.
 *
 * @author Jérémie Leclerc
 */
public class PercentageCalculator {
    
    private final int minValue, maxValue;
    
    public PercentageCalculator(int minValue, int maxValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
    }
    
    public String computeProgress(int step) {
        Double percentage =  (double) (step * 100 / maxValue);
        
        String pattern = String.valueOf(percentage);
        
        return pattern + " %";
    }
}
