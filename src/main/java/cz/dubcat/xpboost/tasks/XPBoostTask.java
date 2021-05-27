package cz.dubcat.xpboost.tasks;

import cz.dubcat.xpboost.XPBoostMain;
import cz.dubcat.xpboost.api.MainAPI;
import cz.dubcat.xpboost.constructors.Database;
import cz.dubcat.xpboost.constructors.Database.DType;
import cz.dubcat.xpboost.constructors.Debug;
import cz.dubcat.xpboost.constructors.XPBoost;
import cz.dubcat.xpboost.utils.DbUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class XPBoostTask extends BukkitRunnable {

    @Override
    public void run() {
        Map<UUID, XPBoost> mp = XPBoostMain.allplayers;

        for (Entry<UUID, XPBoost> pair : mp.entrySet()) {
            XPBoost xpb = pair.getValue();

            if (xpb.getTimeRemaining() <= 0) {
                if (Database.getDatabaseType() == DType.FILE) {
                    File file = MainAPI.setPlayerFile(xpb.getUuid());
                    file.delete();
                } else {
                    PreparedStatement ps = null;
                    try (Connection conn = Database.getConnection()) {
                        // readding new value
                        ps = conn.prepareStatement("DELETE FROM xpboost WHERE uuid=?");
                        ps.setString(1, xpb.getUuid().toString());
                        ps.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        DbUtils.closeQuietly(ps);
                    }
                }

                MainAPI.debug("Removed boost from UUID " + xpb.getUuid(), Debug.NORMAL);
                mp.remove(xpb.getUuid());
            }
        }
    }

}
