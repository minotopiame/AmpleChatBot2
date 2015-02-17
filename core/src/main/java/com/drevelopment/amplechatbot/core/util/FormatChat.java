package com.drevelopment.amplechatbot.core.util;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.api.entity.Player;

public final class FormatChat {

	public final static String setDisplay(String display, String message, String botname) {
		String str = display.replaceAll("%botname", botname);
		return str.replaceAll("%message", message);
	}

	public static String formatChat(String chat, CommandSender sender)  {
		chat = Color.replaceColors(chat);
		if (sender instanceof Player)
			chat = chat.replaceAll("%player", ((Player)sender).getName());
		chat = chat.replaceAll("%botname", Ample.getConfigHandler().getBotName());
		return chat;
	}

}
