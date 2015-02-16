package com.drevelopment.amplechatbot.api.event.chat;

import com.drevelopment.amplechatbot.api.entity.Player;
import com.drevelopment.amplechatbot.api.event.Event;

public class PlayerChatEvent extends Event {

	private Player player;

	private String message;

	public PlayerChatEvent(Player player, String message) {
		this.player = player;
		this.message = message;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getMessage() {
		return this.message;
	}

}
