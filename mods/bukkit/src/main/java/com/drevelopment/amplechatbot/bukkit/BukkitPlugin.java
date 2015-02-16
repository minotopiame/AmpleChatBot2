package com.drevelopment.amplechatbot.bukkit;

import java.io.File;
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
import com.drevelopment.amplechatbot.bukkit.permission.SuperPermsPermissionHandler;
import com.drevelopment.amplechatbot.bukkit.question.BukkitQuestionHandler;
import com.drevelopment.amplechatbot.core.commands.SimpleCommandHandler;
import com.drevelopment.amplechatbot.core.event.SimpleEventHandler;
import com.drevelopment.amplechatbot.core.listeners.ResponseListener;

public class BukkitPlugin extends JavaPlugin {

	private Logger logger;

	@Override
	public void onEnable() {
		logger = this.getLogger();

		Ample.setEventHandler(new SimpleEventHandler());
		Ample.setCommandHandler(new SimpleCommandHandler());
		Ample.setModTransformer(new BukkitModTransformer(this));
		Ample.setConfigHandler(new BukkitConfigHandler(this));

		//TEMP
		Ample.setPermissionHandler(new SuperPermsPermissionHandler());

		if (!setupSQL()) {
			logger.severe("Database could not be setup. AmpleChatBot will now disable");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Ample.setQuestionHandler(new BukkitQuestionHandler(this));

		getServer().getPluginManager().registerEvents(new BukkitListener(this), this);
		Ample.getEventHandler().subscribe(ResponseListener.class);
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
			logger.severe("The SQLType has the unknown value of: "+((BukkitConfigHandler)Ample.getConfigHandler()).getSQLValue());
			return false;
		}

		Ample.setDatabaseHandler(new SQLDatabaseHandler(this, dataop));

		try {
			((SQLDatabaseHandler)Ample.getDatabaseHandler()).open();
			if (dataop instanceof MySQLOptions) {
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Responses (`id` int AUTO_INCREMENT, `keyphrase` varchar(200), `response` varchar(200), PRIMARY KEY (id))");
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Usage (`dtime` integer, `question` integer, `player` varchar(50))");
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Spam (`dtime` integer, `action` integer, `player` varchar(50))");
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Flood (`dtime` integer, `action` integer, `player` varchar(50))");
			} else if (dataop instanceof SQLiteOptions) {
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Responses (id INTEGER PRIMARY KEY AUTOINCREMENT, keyphrase varchar(200), response varchar(200))");
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Usage (dtime INTEGER, action INTEGER, player varchar(50))");
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Spam (dtime integer, action integer, player varchar(50))");
				((SQLDatabaseHandler)Ample.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS amplechatbot_Flood (dtime integer, action integer, player varchar(50))");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
