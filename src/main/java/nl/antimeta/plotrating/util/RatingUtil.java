package nl.antimeta.plotrating.util;

import nl.antimeta.plotrating.entity.Rating;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    public static double round(double value, int places) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
