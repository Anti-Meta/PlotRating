package nl.antimeta.plotrating;

import com.intellectualcrafters.plot.object.PlotId;
import nl.antimeta.bukkit.framework.database.Dao;
import nl.antimeta.bukkit.framework.database.Database;
import nl.antimeta.bukkit.framework.database.model.Resource;
import nl.antimeta.bukkit.framework.database.type.MysqlDatabaseType;
import nl.antimeta.plotrating.entity.Plot;
import nl.antimeta.plotrating.entity.Rating;
import nl.antimeta.plotrating.model.DatabaseModel;
import nl.antimeta.plotrating.model.RateStatus;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PRDatabase {

    private static PRDatabase instance;

    private Dao<Plot> plotDao;
    private Dao<Rating> ratingDao;

    public static PRDatabase getInstance() {
        return instance;
    }

    static PRDatabase setupInstance(DatabaseModel databaseModel) {
        if (instance == null) {
            instance = new PRDatabase(databaseModel);
        }
        return instance;
    }

    private PRDatabase(DatabaseModel databaseModel) {
        createDatabaseTables(databaseModel);
    }

    private void createDatabaseTables(DatabaseModel databaseModel) {
        Resource resource = new Resource();
        resource.setHostname(databaseModel.getHost());
        resource.setPort(databaseModel.getPort());
        resource.setDatabase(databaseModel.getDatabase());
        resource.setUser(databaseModel.getUsername());
        resource.setPassword(databaseModel.getPassword());

        Database database = new Database(new MysqlDatabaseType(), resource);
        try {
            database.createTable(Plot.class);
            database.createTable(Rating.class);
            plotDao = new Dao<>(database, Plot.class);
            ratingDao = new Dao<>(database, Rating.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Plot getPlot(int id) {
        try {
            List<Plot> result = plotDao.find(id);
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Plot getPlotFromSquared(int x, int y) {
        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("x_id", x);
            parameter.put("y_id", y);
            List<Plot> result = plotDao.find(parameter);
            if (!result.isEmpty()) {
                return result.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Rating> getRatings(Plot plot) {
        return getRatings(plot.getId());
    }

    public List<Rating> getRatings(int plotId) {
        try {
            return ratingDao.find("plot_id", plotId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void savePlot(Plot plot) {
        try {
            plotDao.save(plot);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveRating(Rating rating) {
        try {
            ratingDao.save(rating);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlotAndRatings(PlotId plotId) throws SQLException {
        Plot dbPlot = getPlotFromSquared(plotId.x, plotId.y);
        if (dbPlot != null) {
            List<Rating> ratings = getRatings(dbPlot);
            for (Rating rating : ratings) {
                ratingDao.delete(rating);
            }
            plotDao.delete(dbPlot.getId());
        }
    }

    public List<Plot> findAllPendingPlots() {
        try {
            return plotDao.find("rate_status", RateStatus.PENDING.getStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Dao<Plot> getPlotDao() {
        return plotDao;
    }

    public Dao<Rating> getRatingDao() {
        return ratingDao;
    }
}
