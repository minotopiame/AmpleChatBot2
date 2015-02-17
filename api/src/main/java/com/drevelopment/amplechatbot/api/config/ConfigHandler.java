package com.drevelopment.amplechatbot.api.config;

public interface ConfigHandler {

	/**
	 * Gets whether or not the plugin should use Metrics.
	 * <p> These statistics can be viewed <a href='http://mcstats.org/plugin/CouponCodes'>here</a>
	 * @return boolean specified in config
	 */
	public boolean getUseMetrics();

	/**
	 * Gets whether or not the plugin should update itself automatically
	 * @return boolean specified in config
	 */
	public boolean getAutoUpdate();

	/**
	 * Gets the default locale to use
	 * @return the default locale specified in the config, in the form <code>en_US</code>
	 */
	public String getLocale();

	/**
	 * Gets the name of the bot
	 * @return The name of the bot
	 */
	public String getBotName();

	/**
	 * Gets the format for chat
	 * @return The format for chat
	 */
	public String getDisplay();

	public Double getAllowable();

	public Integer[] getAbuseRatio();

	public String getAbuseAction();

	public String getAbuseKick();

	public Long getMsgDelay();

}
