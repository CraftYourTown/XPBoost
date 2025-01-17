package cz.dubcat.xpboost.events;

import cz.dubcat.xpboost.XPBoostMain;
import cz.dubcat.xpboost.api.MainAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onServerListPing(ServerListPingEvent event) {
        if (XPBoostMain.GLOBAL_BOOST.isEnabled()) {
            if (XPBoostMain.getPlugin().getConfig().getBoolean("settings.motdon")) {
                String message = XPBoostMain.getLang().getString("lang.motd");
                message = message.replaceAll("%boost%", String.valueOf(XPBoostMain.GLOBAL_BOOST.getGlobalBoost()));
                message = message.replaceAll("%max%", String.valueOf(event.getMaxPlayers()));
                message = message.replaceAll("%players%", String.valueOf(event.getNumPlayers()));
                event.setMotd(MainAPI.colorizeText(message));
            }
        }
    }

}
