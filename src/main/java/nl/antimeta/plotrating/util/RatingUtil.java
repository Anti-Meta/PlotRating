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
        return sum;
    }
}
