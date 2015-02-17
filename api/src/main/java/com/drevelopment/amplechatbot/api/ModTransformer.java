package com.drevelopment.amplechatbot.api;

import com.drevelopment.amplechatbot.api.command.CommandSender;
import com.drevelopment.amplechatbot.api.entity.Player;

public interface ModTransformer {

	/**
	 * Schedules a runnable to run immediately.
	 * <p> Schedules a delayed task on the server
	 * @param runnable Runnable to schedule
	 */
	public void scheduleRunnable(Runnable runnable, Long Delay);

	/**
	 * Gets the Player.
	 * <p> If the player has not been gotten before, gets and wraps the player from the server.
	 * @param UUID The unique identifier of the player to get
	 * @return The player
	 */
	public Player getPlayer(String UUID);

	/**
	 * <b>*{@link #getPlayer(String)} should be used instead*</b><p>
	 * Gets the player from the server software, wrapped by {@link Player}
	 * @param UUID The UUID of the player to get
	 * @return Player A new instance of the player
	 */
	public Player getModPlayer(String UUID);

	/**
	 * This should be used in case the player you want to get the name of may be offline.
	 * @param UUID The UUID of the player's name to get
	 * @return The name of the player
	 */
	public String getPlayerName(String UUID);

	/**
	 * Broadcasts a message to the server.
	 * @param message The message to broadcast
	 */
	public void broadcastMessage(String message);

	/**
	 * Runs a command as either console, or the specified player.
	 * <p>If sender is null or not an instance of {@link Player}, the command will be run as console.</p>
	 * @param sender The Sender to run the command as. If null, will default to console.
	 * @param command The command to run, Without the <code>/</code> in front.
	 */
	public void runCommand(CommandSender sender, String command);

}
