package nl.antimeta.plotrating.entity;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.bukkit.util.OfflinePlayerUtil;
import nl.antimeta.bukkit.framework.database.annotation.Entity;
import nl.antimeta.bukkit.framework.database.annotation.Field;
import nl.antimeta.bukkit.framework.database.model.BaseEntity;
import nl.antimeta.bukkit.framework.database.model.FieldType;
import nl.antimeta.plotrating.model.RateStatus;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "pr_plot")
public class Plot extends BaseEntity<Plot> {

    @Field(fieldName = "plot_id", fieldType = FieldType.Integer,  primary = true)
    private Integer id;

    @Field(fieldName = "x_id", fieldType = FieldType.Integer)
    private Integer plotXId;

    @Field(fieldName = "y_id", fieldType = FieldType.Integer)
    private Integer plotYId;

    @Field(fieldName = "playerUUID", fieldType = FieldType.Varchar , size = 40)
    private String playerUUID;

    @Field(fieldName = "rate_status", fieldType = FieldType.Varchar, size = 20)
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
        if (player == null && playerUUID != null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
            if (offlinePlayer.isOnline()) {
                player = offlinePlayer.getPlayer();
            } else {
                player = OfflinePlayerUtil.loadPlayer(offlinePlayer);
            }
        }
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
