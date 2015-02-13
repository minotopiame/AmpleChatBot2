package com.drevelopment.amplechatbot.core.permission;

import java.util.Set;

import com.drevelopment.amplechatbot.api.entity.Player;
import com.drevelopment.amplechatbot.core.util.LocaleHandler;

public class SimplePermissionHandler {

	public String getName() {
		return "Nope";
	}

	public boolean isEnabled() {
		return false;
	}

	public boolean hasPermission(Player player, String node) {
		throw new UnsupportedOperationException("No permission handler");
	}

	public Set<String> getGroups(Player player) {
		throw new UnsupportedOperationException(LocaleHandler.getString("No permission handler"));
	}
}
