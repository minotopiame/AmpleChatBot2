package com.drevelopment.amplechatbot.bukkit.database.options;

import java.io.File;

public class SQLiteOptions implements DatabaseOptions {

	private final File file;

	public SQLiteOptions(File file) {
		this.file = file;
	}

	public File getSQLFile() {
		return file;
	}

}
