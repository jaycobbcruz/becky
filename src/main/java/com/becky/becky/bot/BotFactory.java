package com.becky.becky.bot;

import com.becky.becky.bot.ab.Bot;
import com.becky.becky.bot.ab.MagicStrings;

public class BotFactory {

	private Bot defaultBot;
	//TODO: allow to create and map multiple bots

	public Bot getDefaultBot() {
		return defaultBot;
	}

	public void setDefaultBot(Bot defaultBot) {
		this.defaultBot = defaultBot;
	}

	public void reloadBots() {
		this.defaultBot = new Bot(defaultBot.getName(), MagicStrings.root_path, "reload");
	}
}
