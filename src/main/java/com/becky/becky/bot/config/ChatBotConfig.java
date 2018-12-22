package com.becky.becky.bot.config;

import com.becky.becky.bot.BotFactory;
import com.becky.becky.bot.ab.Bot;
import com.becky.becky.bot.ab.MagicBooleans;
import com.becky.becky.bot.ab.MagicStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ChatBotConfig {

	private static final Logger log = LoggerFactory.getLogger(ChatBotConfig.class);

	private static final Map<String, String> DEFAULTS;

	static {
		Map<String, String> defaults = new LinkedHashMap<String, String>();
		defaults.put("http.enabled", String.valueOf(false));
		defaults.put("node.local", String.valueOf(true));
		DEFAULTS = Collections.unmodifiableMap(defaults);
	}

	//@Value("#{'${bot.data.location:classpath:/beckybot}'}")
	//@Value("/app/src/main/resources/beckybot/")
	@Value("#{'${bot.data.location:/app/src/main/resources/beckybot/}'}")
	private File botDataLocation;

	@Value("${bot.default.name:super}")
	private String defaultBotName;

	@Bean
	public BotFactory loadBotFactory() {
		try {
			MagicBooleans.jp_tokenize = false;
			MagicBooleans.trace_mode = true;
			MagicStrings.root_path = botDataLocation.getAbsolutePath();

			final Bot bot = new Bot(defaultBotName, MagicStrings.root_path, "chat");
			final BotFactory botFactory = new BotFactory();
			botFactory.setDefaultBot(bot);

			return botFactory;
		} catch (Exception e) {
			log.error("Error occurred while initialising bot: " + e.getMessage(), e);
		}
		return null;
	}

}