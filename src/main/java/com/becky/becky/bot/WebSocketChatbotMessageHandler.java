package com.becky.becky.bot;

import com.becky.becky.model.Message;
import com.becky.becky.model.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class WebSocketChatbotMessageHandler extends AbstractChatbotMessageHandler {

    @Async
    public Future<Message> handleMessage(Message message) {
        return chatBot(message);
    }

}
