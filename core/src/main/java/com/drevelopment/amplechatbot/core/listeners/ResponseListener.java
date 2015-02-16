package com.drevelopment.amplechatbot.core.listeners;

import java.util.Map.Entry;
import java.util.TreeMap;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.api.event.AmpleListener;
import com.drevelopment.amplechatbot.api.event.chat.PlayerChatEvent;
import com.drevelopment.amplechatbot.core.util.FormatChat;

public class ResponseListener {

	@AmpleListener
	public void onChat(PlayerChatEvent event ) {
		if( event.getPlayer().hasPermission("ample.invoke")) {
			String message = event.getMessage();
			if (message.length() > 3) {
				TreeMap<Double,TreeMap<Integer,String>> rank = Ample.getQuestionHandler().getResponses(message);
				try {
					Entry<Double, TreeMap<Integer, String>> highest = rank.lastEntry();
					TreeMap<Integer, String> value = highest.getValue();
					if(highest.getKey() > Ample.getConfigHandler().getAllowable()) {
						execute(value, event);
					}
				}  catch (Exception e) { }
			}
		}
	}

	private void execute(TreeMap<Integer, String> value, final PlayerChatEvent event) {
		final String response = value.firstEntry().getValue();
		final int id = value.firstEntry().getKey();
		//TODO Usage
		Ample.getQuestionHandler().addUsage(event.getPlayer().getName(), id);
		int v = Ample.getQuestionHandler().getUsage(id);
		int c = Ample.getConfigHandler().getAbuseRatio()[0];
		if(v <= c) {
			String[] newline = response.split(";");
			for(int a=0;a < newline.length;a++) {
				final String line = newline[a];
				String fresponse = FormatChat.formatChat(FormatChat.setDisplay(Ample.getConfigHandler().getDisplay(), line, Ample.getConfigHandler().getBotName()), event.getPlayer());
				final String fmsg = fresponse;
				Ample.getModTransformer().scheduleRunnable(new Runnable() {

				@Override
				public void run() {
					if(!line.isEmpty()) {
						if(line.length() > 4 && line.toLowerCase().substring(0, 4).equals("cmd:")) {
							//String cmd = db.unescape(line.toLowerCase().substring(4));
							//Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),FormatChat.formatChat(cmd.trim(),event.getPlayer()));
						} else if(line.length() > 5 && line.toLowerCase().substring(0, 5).equals("pcmd:")) {
							//String cmd = db.unescape(line.toLowerCase().substring(5));
							//Bukkit.getServer().dispatchCommand(event.getPlayer(),FormatChat.formatChat(cmd.trim(), event.getPlayer()));
						} else if(line.length() > 3 && line.toLowerCase().substring(0, 3).equals("pm:")) {
							//plugin.loger("pm to "+event.getPlayer().getName()+": "+line.substring(3));
							//event.getPlayer().sendMessage(FormatChat.formatChat(FormatChat.setDisplay(Ample.getConfigHandler().getDisplay(),db.unescape(line.substring(3)), config.getBotName()), event));
						} else {
							Ample.getModTransformer().broadcastMessage(fmsg);
						}
					}
				}
			});
			}
		} else {
			if(Ample.getConfigHandler().getAbuseAction().equalsIgnoreCase("kick")) event.getPlayer().kickPlayer(Ample.getConfigHandler().getAbuseKick());
		}
	}
}
