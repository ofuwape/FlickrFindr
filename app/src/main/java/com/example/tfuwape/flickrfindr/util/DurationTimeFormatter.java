package com.example.tfuwape.flickrfindr.util;

import org.joda.time.Duration;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A time formatter for printing duration times
 */
@Singleton
public class DurationTimeFormatter {

    @Inject
    public DurationTimeFormatter() {
        //default constructor
    }

    /**
     * Returns the duration string formatted using HH:mm:ss for the given number of seconds
     *
     * @param seconds Seconds
     * @return Duration string HH:mm:ss
     */
    public String durationStringFromSeconds(int seconds) {
        String result = "0m";

        if (seconds > 0) {
            Duration duration = Duration.standardSeconds(seconds);
            long sec = duration.getStandardSeconds();
            long min = duration.getStandardMinutes();
            if (min > 0) {
                result = min + "m";
            } else {
                result = sec + "s";
            }
        }
        return result;
    }

}
