package cz.dubcat.xpboost.api.messages;

import cz.dubcat.xpboost.XPBoostMain;
import cz.dubcat.xpboost.api.MainAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ExperienceNotifier {

    private String messageSettingsPath = "settings.experienceGainedMessagesOptions";
    private String messageReminderSettingsPath = "settings.activeBoostReminderOptions";

    public void experienceGainedNotification(Player player, String message) {
        if (this.notificationsEnabled()) {
            String messageLocationRaw = XPBoostMain.getPlugin().getConfig().getString(messageSettingsPath + ".location").toUpperCase();
            MessageLocation messageLocation = MessageLocation.valueOf(messageLocationRaw);
            this.sendNotification(player, messageLocation, message);
        }
    }

    public void reminderNotification(Player player, String message) {
        String messageLocationRaw = XPBoostMain.getPlugin().getConfig().getString(messageReminderSettingsPath + ".location").toUpperCase();
        MessageLocation messageLocation = MessageLocation.valueOf(messageLocationRaw);
        this.sendNotification(player, messageLocation, message);
    }

    public boolean notificationsEnabled() {
        return XPBoostMain.getPlugin().getConfig().getBoolean(messageSettingsPath + ".enabled");
    }

    private void sendNotification(Player player, MessageLocation messageLocation, String message) {
        switch (messageLocation) {
            case ACTIONBAR:
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(MainAPI.colorizeText(message)));
                break;
            case CHAT:
                MainAPI.sendMessage(message, player);
                break;
        }
    }
}
