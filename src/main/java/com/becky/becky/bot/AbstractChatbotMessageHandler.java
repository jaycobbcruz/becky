package com.becky.becky.bot;

import com.becky.becky.bot.ab.Bot;
import com.becky.becky.bot.ab.Chat;
import com.becky.becky.model.Message;
import com.becky.becky.util.TranslateText;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSessionRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public abstract class AbstractChatbotMessageHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractChatbotMessageHandler.class);

    private static final boolean ENABLE_TRANSLATION = true;

    static {
        if (ENABLE_TRANSLATION) {
            System.setProperty("GOOGLE_API_KEY", "AIzaSyCN1Pxgfc-5mOvOjQ2Gd5bvJVcPZckH-KA");
        }
    }

    @Autowired
    private BotFactory botFactory;
    private final MapSessionRepository sessionRepository = new MapSessionRepository();
    private final Map<String, String> sessionMapIds = new HashMap<>();

    public abstract Future<Message> handleMessage(final Message message);

    protected Future<Message> chatBot(final Message message) {
        try {
            Thread.sleep(300); //do not let chatbot to log message before user's message
        } catch (InterruptedException e) {
            log.warn(e.getMessage(), e);
        }

        String chatbotResponse;
        if ("reloadbot".equals(StringUtils.remove(message.getText(), " "))) {
            reloadBots();
            chatbotResponse = "I feel refreshed!";
        } else if (ENABLE_TRANSLATION) {
            try {
                final String detectedLanguage = TranslateText.detectLanguage(message.getText());
                final String messageLanguageToEnglish = TranslateText.translate(message.getText(), detectedLanguage, "en");
                chatbotResponse = getChatSession(message.getChatId()
                ).multisentenceRespond(denormalize(messageLanguageToEnglish));
                chatbotResponse = TranslateText.translate(chatbotResponse, "en", detectedLanguage);
            } catch (Exception e) {
                chatbotResponse = getChatSession(message.getChatId()
                ).multisentenceRespond(message.getText());
            }
        } else {
            chatbotResponse = getChatSession(message.getChatId()).multisentenceRespond(message.getText());
        }
        final Message botMessage = new Message();
        botMessage.setUserName("Becky");
        botMessage.setText(chatbotResponse);
        return new AsyncResult<>(botMessage);
    }

    private Chat getChatSession(final String userName) {
        if (sessionMapIds.containsKey(userName)) {
            return (Chat) sessionRepository.getSession(sessionMapIds.get(userName)).getAttribute("chatSession");
        } else {
            final Bot bot = botFactory.getDefaultBot();

            final Chat chatSession = new Chat(bot, true);
            final ExpiringSession session = sessionRepository.createSession();
            session.setMaxInactiveIntervalInSeconds(86400); //1 day
            session.setAttribute("chatSession", chatSession);
            sessionRepository.save(session);
            sessionMapIds.put(userName, session.getId());
            return chatSession;
        }
    }

    private void reloadBots() {
        this.sessionMapIds.clear();
        botFactory.reloadBots();
    }

    private String denormalize(String s) {
        String text = s;
        text = text.replace("&#39;", "'");
        text = text.replace("&apos;", "'");
        return text;
    }

}
