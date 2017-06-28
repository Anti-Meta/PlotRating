package nl.antimeta.plotrating.entity;

import nl.antimeta.bukkit.framework.database.annotation.Entity;
import nl.antimeta.bukkit.framework.database.annotation.Field;
import nl.antimeta.bukkit.framework.database.model.BaseEntity;
import nl.antimeta.bukkit.framework.database.model.FieldType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "pr_rating")
public class Rating extends BaseEntity<Rating> {

    @Field(fieldName = "rating_id", fieldType = FieldType.Integer, primary = true)
    private Integer id;

    @Field(fieldName = "plot_id", fieldType = FieldType.Integer)
    private Integer plotId;

    @Field(fieldName = "playerUUID", fieldType = FieldType.Varchar, size = 40)
    private String playerUUID;

    @Field(fieldName = "rating", fieldType = FieldType.Integer)
    private Integer rating;

    @Field(fieldName = "description", fieldType = FieldType.Varchar)
    private String description;

    public Rating() {

    }

    public Rating(Integer plot, Integer rating, String playerUUID) {
        this.plotId = plot;
        this.rating = rating;
        this.playerUUID = playerUUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public List<Rating> buildResultSet(ResultSet resultSet) {
        List<Rating> results = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Rating rating = new Rating();
                rating.setId(resultSet.getInt("rating_id"));
                rating.setPlotId(resultSet.getInt("plot_id"));
                rating.setPlayerUUID(resultSet.getString("playerUUID"));
                rating.setRating(resultSet.getInt("rating"));
                rating.setDescription(resultSet.getString("description"));
                results.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPlotId() {
        return plotId;
    }

    public void setPlotId(Integer plotId) {
        this.plotId = plotId;
    }
}
