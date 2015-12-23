package com.drevelopment.amplechatbot.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ample.toggle")) {
            sender.sendMessage("§cNo permission.");
            return true;
        }
        BukkitPlugin.enabled = !BukkitPlugin.enabled;
        if (BukkitPlugin.enabled) {
            sender.sendMessage("§6The AmpleChatBot is now enabled.");
        } else {
            sender.sendMessage("§6The AmpleChatBot is now disabled.");
        }
        return true;
    }
}
