package com.drevelopment.amplechatbot.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.drevelopment.amplechatbot.api.entity.Player;
import com.drevelopment.amplechatbot.bukkit.entity.BukkitPlayer;
import com.drevelopment.amplechatbot.core.SimpleModTransformer;

public class BukkitModTransformer extends SimpleModTransformer {

	private BukkitPlugin plugin;

	public BukkitModTransformer(BukkitPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void scheduleRunnable(Runnable runnable) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable);
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
		Bukkit.broadcastMessage(message);
	}

}
