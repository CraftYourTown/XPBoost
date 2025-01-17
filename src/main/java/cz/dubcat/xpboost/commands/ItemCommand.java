package cz.dubcat.xpboost.commands;

import cz.dubcat.xpboost.XPBoostMain;
import cz.dubcat.xpboost.api.MainAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemCommand implements CommandInterface {

    private XPBoostMain plugin;

    public ItemCommand(XPBoostMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player;

        if (sender instanceof Player) {
            player = (Player) sender;
            if (!(player.hasPermission("xpboost.admin"))) {
                return false;
            }
        }

        if (args.length == 4) {

            String name = args[1];

            Player hrac = Bukkit.getServer().getPlayer(name);
            int time = Integer.parseInt(args[3]);
            double boost = Double.parseDouble(args[2]);

            // player not found
            if (hrac == null || !hrac.isOnline()) {
                if (sender instanceof Player) {
                    MainAPI.sendMessage("Player is not online", (Player) sender);
                } else {
                    plugin.getLogger().info("Player is not online.");
                }
                return true;
            }

            ItemStack item = new ItemStack(
                    Material.getMaterial(XPBoostMain.getPlugin().getConfig().getString("settings.itemmaterial")));
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(MainAPI.colorizeText(XPBoostMain.getLang().getString("lang.itemname")
                    .replace("%boost%", boost + "").replace("%time%", time + "")));

            List<String> lore = new ArrayList<String>();
            lore.add(MainAPI.colorizeText(XPBoostMain.getLang().getString("lang.item.lore1").replace("%boost%", "" + boost)));
            lore.add(MainAPI.colorizeText(XPBoostMain.getLang().getString("lang.item.lore2").replace("%time%", "" + time)));
            lore.add(MainAPI.colorizeText(XPBoostMain.getLang().getString("lang.item.lore3")));
            if (XPBoostMain.getLang().contains("lang.item.lore4")) {
                lore.add(MainAPI.colorizeText(XPBoostMain.getLang().getString("lang.item.lore4")));
            } else if (XPBoostMain.getLang().contains("lang.item.lore5")) {
                lore.add(MainAPI.colorizeText(XPBoostMain.getLang().getString("lang.item.lore5")));
            }

            meta.setLore(lore);
            item.setItemMeta(meta);

            hrac.getInventory().addItem(item);

            if (sender instanceof Player) {
                MainAPI.sendMessage(
                        "XPBoost item of &c" + boost + "x boost &ffor &c" + time + "s &fhas been given to &c" + name,
                        (Player) sender);
            } else {
                plugin.getLogger().info(
                        "XPBoost item of " + boost + "x boost for " + time + " seconds has been given to " + name);
            }
            return true;
        } else {
            if (sender instanceof Player) {
                player = (Player) sender;
                MainAPI.sendMessage("Usage: &c/xpboost item <player> <boost> <time>", player);
            } else {
                plugin.getLogger().info("Usage: /xpboost item <player> <boost> <time>");
            }
        }

        return false;
    }
}
