package nl.antimeta.plotrating.entity;

import nl.antimeta.bukkit.framework.database.annotation.Entity;
import nl.antimeta.bukkit.framework.database.annotation.Field;
import nl.antimeta.bukkit.framework.database.model.BaseEntity;
import nl.antimeta.bukkit.framework.database.model.FieldType;
import nl.antimeta.plotrating.model.RateStatus;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "pr_plot")
public class Plot extends BaseEntity<Plot> {

    @Field(fieldType = FieldType.Integer, name = "plot_id", primary = true)
    private Integer id;

    @Field(fieldType = FieldType.Integer, name = "x_id")
    private Integer plotXId;

    @Field(fieldType = FieldType.Integer, name = "y_id")
    private Integer plotYId;

    @Field(fieldType = FieldType.Varchar, name = "playerUUID", size = 40)
    private String playerUUID;

    @Field(fieldType = FieldType.Varchar, name = "rate_status", size = 20)
    private String rateStatus;

    //LOCAL FIELDS
    private Player player;
    private RateStatus internRateStatus;
    private boolean plotOwner;

    public Plot() {

    }

    public Plot(Player player) {
        this.player = player;
        playerUUID = player.getUniqueId().toString();
    }

    public Plot(CommandSender cmdSender) {
        this.player = (Player) cmdSender;
        playerUUID = player.getUniqueId().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public List<Plot> buildResultSet(ResultSet resultSet) {
        List<Plot> results = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Plot plot = new Plot();
                plot.setId(resultSet.getInt("plot_id"));
                plot.setPlotXId(resultSet.getInt("x_id"));
                plot.setPlotYId(resultSet.getInt("y_id"));
                plot.setPlayerUUID(resultSet.getString("playerUUID"));
                plot.setRateStatus(resultSet.getString("rate_status"));
                results.add(plot);
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

    public String getRateStatus() {
        return rateStatus;
    }

    public void setRateStatus(String rateStatus) {
        this.rateStatus = rateStatus;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public RateStatus getInternRateStatus() {
        return internRateStatus;
    }

    public void setInternRateStatus(RateStatus internRateStatus) {
        this.internRateStatus = internRateStatus;
    }

    public boolean isPlotOwner() {
        return plotOwner;
    }

    public void setPlotOwner(boolean plotOwner) {
        this.plotOwner = plotOwner;
    }

    public Integer getPlotXId() {
        return plotXId;
    }

    public void setPlotXId(Integer plotXId) {
        this.plotXId = plotXId;
    }

    public Integer getPlotYId() {
        return plotYId;
    }

    public void setPlotYId(Integer plotYId) {
        this.plotYId = plotYId;
    }
}
