package com.becky.becky.bot;

import com.becky.becky.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class StompChatbotMessageHandler extends AbstractChatbotMessageHandler {

	@Autowired
	private SimpMessagingTemplate template;

    @Async
    public Future<Message> handleMessage(Message message) {
        final Future<Message> botMessage = chatBot(message);
        try {
            this.template.convertAndSend("/topic/" + message.getChatId(), botMessage.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return botMessage;
    }

}
