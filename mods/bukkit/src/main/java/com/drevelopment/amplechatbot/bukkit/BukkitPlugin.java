package com.drevelopment.amplechatbot.bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.drevelopment.amplechatbot.api.Ample;
import com.drevelopment.amplechatbot.bukkit.config.BukkitConfigHandler;
import com.drevelopment.amplechatbot.bukkit.database.SQLDatabaseHandler;
import com.drevelopment.amplechatbot.bukkit.database.options.DatabaseOptions;
import com.drevelopment.amplechatbot.bukkit.database.options.MySQLOptions;
import com.drevelopment.amplechatbot.bukkit.database.options.SQLiteOptions;
import com.drevelopment.amplechatbot.bukkit.listeners.BukkitListener;
import com.drevelopment.amplechatbot.bukkit.metrics.Metrics;
import com.drevelopment.amplechatbot.bukkit.permission.SuperPermsPermissionHandler;
import com.drevelopment.amplechatbot.bukkit.permission.VaultPermissionHandler;
import com.drevelopment.amplechatbot.bukkit.question.BukkitQuestionHandler;
import com.drevelopment.amplechatbot.bukkit.updater.Updater;
import com.drevelopment.amplechatbot.core.commands.SimpleCommandHandler;
import com.drevelopment.amplechatbot.core.event.SimpleEventHandler;
import com.drevelopment.amplechatbot.core.listeners.ResponseListener;
import com.drevelopment.amplechatbot.core.util.LocaleHandler;

public class BukkitPlugin extends JavaPlugin {

	private Logger logger;

	@Override
	public void onEnable() {
		logger = this.getLogger();

		Ample.setEventHandler(new SimpleEventHandler());
		Ample.setCommandHandler(new SimpleCommandHandler());
		Ample.setModTransformer(new BukkitModTransformer(this));
		Ample.setConfigHandler(new BukkitConfigHandler(this));

		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			Ample.setPermissionHandler(new VaultPermissionHandler());
		} else {
			Ample.setPermissionHandler(new SuperPermsPermissionHandler());
		}

		if (!setupSQL()) {
			logger.severe(LocaleHandler.getString("Console.SQL.SetupFailed"));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Ample.setQuestionHandler(new BukkitQuestionHandler(this));

		getServer().getPluginManager().registerEvents(new BukkitListener(this), this);
		Ample.getEventHandler().subscribe(ResponseListener.class);

		if (Ample.getConfigHandler().getUseMetrics()) {
			try {
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e) {}
		}

		if (Ample.getConfigHandler().getAutoUpdate()) {
			new Updater(this, 38442, this.getFile(), Updater.UpdateType.DEFAULT, false);
		}
	}

	@Override
	public void onDisable() {
		try {
			((SQLDatabaseHandler)Ample.getDatabaseHandler()).close();
		} catch (SQLException e) {
			logger.severe(LocaleHandler.getString("Console.SQL.CloseFailed"));
		}

		Ample.setDatabaseHandler(null);
		Ample.setCommandHandler(null);
		Ample.setConfigHandler(null);
		Ample.setEventHandler(null);
		Ample.setModTransformer(null);
		Ample.setPermissionHandler(null);
		Ample.setQuestionHandler(null);
	}

	private boolean setupSQL() {
		DatabaseOptions dataop = null;
		if (((BukkitConfigHandler)Ample.getConfigHandler()).getSQLValue().equalsIgnoreCase("MySQL")) {
			dataop = new MySQLOptions(((BukkitConfigHandler)Ample.getConfigHandler()).getHostname(), ((BukkitConfigHandler)Ample.getConfigHandler()).getPort(), ((BukkitConfigHandler)Ample.getConfigHandler()).getDatabase(), ((BukkitConfigHandler)Ample.getConfigHandler()).getUsername(), ((BukkitConfigHandler)Ample.getConfigHandler()).getPassword());
		}
		else if (((BukkitConfigHandler)Ample.getConfigHandler()).getSQLValue().equalsIgnoreCase("SQLite")) {
			dataop = new SQLiteOptions(new File(getDataFolder()+"/ample_data.db"));
		}
		else if (!((BukkitConfigHandler)Ample.getConfigHandler()).getSQLValue().equalsIgnoreCase("MySQL") && !((BukkitConfigHandler)Ample.getConfigHandler()).getSQLValue().equalsIgnoreCase("SQLite")) {
			logger.severe(LocaleHandler.getString("Console.SQL.UnknownValue", ((BukkitConfigHandler)Ample.getConfigHandler()).getSQLValue()));
			return false;
		}

		Ample.setDatabaseHandler(new SQLDatabaseHandler(this, dataop));

		try {
			((SQLDatabaseHandler)Ample.getDatabaseHandler()).open();
			if (dataop instanceof MySQLOptions) {
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Responses (`id` int AUTO_INCREMENT, `keyphrase` varchar(200), `response` varchar(200), PRIMARY KEY (id))");
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Usage (`dtime` integer, `question` integer, `player` varchar(50))");
			} else if (dataop instanceof SQLiteOptions) {
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Responses (id INTEGER PRIMARY KEY AUTOINCREMENT, keyphrase varchar(200), response varchar(200))");
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Usage (dtime INTEGER, question INTEGER, player varchar(50))");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
