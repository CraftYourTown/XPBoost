package cz.dubcat.xpboost.commands;

import cz.dubcat.xpboost.XPBoostMain;
import cz.dubcat.xpboost.api.MainAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MainCommand implements CommandInterface {

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        for (String menu : (List<String>) XPBoostMain.getLang().getList("lang.pluginmenu")) {
            MainAPI.sendMessage(menu, sender);
        }

        return true;
    }
}
