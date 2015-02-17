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
	public Long getMsgDelay() {
		return config.getLong("Delay", 2L);
	}

	public String getSQLValue() {
		return config.getString("sql-type", "SQLite");
	}

	public String getHostname() {
		return config.getString("MySQL-options.hostname", "localhost");
	}

	public String getPort() {
		return config.getString("MySQL-options.port", "3306");
	}

	public String getDatabase() {
		return config.getString("MySQL-options.database", "AmpleChatBot");
	}

	public String getUsername() {
		return config.getString("MySQL-options.username", "minecraft");
	}

	public String getPassword() {
		return config.getString("MySQL-options.password", "password");
	}

}
