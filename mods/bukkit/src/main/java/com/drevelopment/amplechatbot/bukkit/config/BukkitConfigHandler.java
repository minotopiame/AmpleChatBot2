package com.drevelopment.amplechatbot.bukkit.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.drevelopment.amplechatbot.api.config.ConfigHandler;

public class BukkitConfigHandler implements ConfigHandler {

	private FileConfiguration config;

	public BukkitConfigHandler(Plugin plugin) {
		this.config = plugin.getConfig();

		if (!(new File("plugins/AmpleChatBot/config.yml").exists()))
			plugin.saveDefaultConfig();
		if (!config.options().copyDefaults(true).configuration().equals(config))
			plugin.saveConfig();
	}

	@Override
	public boolean getUseMetrics() {
		return config.getBoolean("use-metrics", true);
	}

	@Override
	public boolean getAutoUpdate() {
		return config.getBoolean("auto-update", true);
	}

	@Override
	public String getLocale() {
		return config.getString("locale", "en_US");
	}

	@Override
	public String getBotName() {
		return config.getString("BotName", "AmpleBot");
	}

	@Override
	public String getDisplay() {
		return config.getString("Display", "<%botname> %message");
	}

	@Override
	public Double getAllowable() {
		return config.getDouble("Allowable", 80);
	}

	@Override
	public Integer[] getAbuseRatio() {
		String[] ratio = config.getString("AbuseRatio", "3;20").split(";");
		Integer v1 = Integer.parseInt(ratio[0]);
		Integer v2 = Integer.parseInt(ratio[1]);
		Integer[] ary = {v1, v2};
		return  ary;
	}

	@Override
	public String getAbuseAction() {
		return config.getString("AbuseAction", "ignore");
	}

	@Override
	public String getAbuseKick() {
		return config.getString("AbuseKick", "[AmpleBot] Do not abuse me or I will keep kicking you!");
	}

	@Override
	public String getSpamWarn() {
		return config.getString("SpamWarn", "Please do not spam!");
	}

	@Override
	public String[] getFloodAction() {
		return config.getString("FloodAction", "warn,kick,ban").split(",");
	}

	@Override
	public String getFloodWarn() {
		return config.getString("FloodWarn", "Please do not flood the server!");
	}

	@Override
	public String getFloodKick() {
		return config.getString("FloodKick", "You have been kicked for flooding the server!");
	}

	@Override
	public String getFloodBan() {
		return config.getString("FloodBan", "You have been banned for flooding the server!");
	}

	@Override
	public Integer[] getFloodRatio() {
		String[] ratio = config.getString("FloodRatio", "5;10").split(";");
		Integer v1 = Integer.parseInt(ratio[0]);
		Integer v2 = Integer.parseInt(ratio[1]);
		Integer[] ary = {v1, v2};
		return  ary;
	}

	@Override
	public Long getMsgDelay() {
		return config.getLong("Delay");
	}

	public String getSQLValue() {
		return config.getString("sql-type");
	}

	public String getHostname() {
		return config.getString("MySQL-options.hostname");
	}

	public String getPort() {
		return config.getString("MySQL-options.port");
	}

	public String getDatabase() {
		return config.getString("MySQL-options.database");
	}

	public String getUsername() {
		return config.getString("MySQL-options.username");
	}

	public String getPassword() {
		return config.getString("MySQL-options.password");
	}

}
