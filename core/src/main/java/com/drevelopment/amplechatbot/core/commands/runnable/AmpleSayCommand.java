package com.drevelopment.amplechatbot.core.commands.runnable;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.core.util.FormatChat;
import com.drevelopment.amplechatbot.core.util.LocaleHandler;

public class AmpleSayCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public AmpleSayCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		if (sender.hasPermission("ample.say")) {
			if (args.length > 0) {
				String msg = "";
				for (int i = 0; i < args.length ; i++) {
					msg += args[i];
					msg += " ";
				}
				msg = FormatChat.setDisplay(Ample.getConfigHandler().getDisplay(), msg, Ample.getConfigHandler().getBotName());
				msg = FormatChat.formatChat(msg, sender);
				Ample.getModTransformer().broadcastMessage(msg);
			} else sender.sendMessage(LocaleHandler.getString("Command.Help.Amplesay"));
		} else sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
	}

}
