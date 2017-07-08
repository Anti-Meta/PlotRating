package nl.antimeta.plotrating.util;

import nl.antimeta.plotrating.entity.Rating;

import java.util.List;

public class RatingUtil {

    public static double getAverageRating(List<Rating> ratings) {
        Integer sum = 0;
        if(!ratings.isEmpty()) {
            for (Rating rating : ratings) {
                sum += rating.getRating();
            }
            return sum.doubleValue() / ratings.size();
        }
        return round(sum, 2);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
