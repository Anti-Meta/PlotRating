package nl.antimeta.plotrating.entity;

import nl.antimeta.bukkit.framework.database.annotation.Entity;
import nl.antimeta.bukkit.framework.database.annotation.Field;
import nl.antimeta.bukkit.framework.database.model.BaseEntity;
import nl.antimeta.bukkit.framework.database.model.FieldType;

@Entity(tableName = "pr_rating")
public class Rating extends BaseEntity {

    @Field(fieldName = "rating_id", fieldType = FieldType.Integer, primary = true)
    private Integer id;

    @Field(fieldName = "plot_id", fieldType = FieldType.Integer, foreign = true, foreignAutoLoad = true)
    private Plot plot;

    @Field(fieldName = "playerUUID", fieldType = FieldType.Varchar, size = 40)
    private String playerUUID;

    @Field(fieldName = "rating", fieldType = FieldType.Integer)
    private Integer rating;

    @Field(fieldName = "description", fieldType = FieldType.Varchar)
    private String description;

    public Rating(Plot plot, Integer rating, String playerUUID) {
        this.plot = plot;
        this.rating = rating;
        this.playerUUID = playerUUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }
}
