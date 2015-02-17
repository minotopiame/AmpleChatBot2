package com.drevelopment.amplechatbot.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.api.entity.Player;
import com.drevelopment.amplechatbot.bukkit.database.SQLDatabaseHandler;
import com.drevelopment.amplechatbot.bukkit.entity.BukkitPlayer;
import com.drevelopment.amplechatbot.core.SimpleModTransformer;

public class BukkitModTransformer extends SimpleModTransformer {

	private BukkitPlugin plugin;

	public BukkitModTransformer(BukkitPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void scheduleRunnable(Runnable runnable, Long delay) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
	}

	@Override
	public Player getModPlayer(String uuid) {
		org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(uuid));

		if (bukkitPlayer == null)
			return null;

		return new BukkitPlayer(plugin, bukkitPlayer);
	}

	@Override
	public String getPlayerName(String uuid) {
		return Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
	}

	@Override
	public void broadcastMessage(String message) {
		message = ((SQLDatabaseHandler)Ample.getDatabaseHandler()).unescape(message);
		Bukkit.broadcastMessage(message);
	}

	@Override
	public void runCommand(CommandSender sender, String command) {
		command = ((SQLDatabaseHandler)Ample.getDatabaseHandler()).unescape(command);
		if (sender instanceof Player) {
			Bukkit.getServer().dispatchCommand(Bukkit.getPlayer(UUID.fromString(((Player) sender).getUUID())), command);
		} else {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
		}
	}

}
