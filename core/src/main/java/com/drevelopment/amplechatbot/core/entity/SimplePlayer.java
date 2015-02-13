package com.drevelopment.amplechatbot.core.entity;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.api.entity.Player;

public abstract class SimplePlayer implements CommandSender, Player {

	/**
	 * Gets whether the player has a certain permission node
	 */
	public boolean hasPermission(String node) {
		return Ample.getPermissionHandler().hasPermission(this, node);
	}

	public String getName() {
		return null;
	}

	public String getUUID() {
		return null;
	}

	public abstract void setLevel(int level);

	public abstract int getLevel();

	public abstract void giveItem(int item, int amount);

	public abstract String getLocale();

}
