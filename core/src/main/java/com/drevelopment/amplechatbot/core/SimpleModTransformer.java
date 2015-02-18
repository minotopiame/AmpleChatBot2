package com.drevelopment.amplechatbot.core;

import java.util.HashMap;
import java.util.Map;

import com.drevelopment.amplechatbot.api.ModTransformer;
import com.drevelopment.amplechatbot.api.entity.Player;

public abstract class SimpleModTransformer implements ModTransformer {

	protected final Map<String, Player> players = new HashMap<String, Player>();

	public Player getPlayer(String UUID) {
		if (players.containsKey(UUID)) return players.get(UUID);
		Player player = getModPlayer(UUID);
		if (player != null) {
			players.put(UUID, player);
			return player;
		}
		return player;
	}

	public void removePlayer(Player player) {
		players.remove(player.getUUID());
	}
}
