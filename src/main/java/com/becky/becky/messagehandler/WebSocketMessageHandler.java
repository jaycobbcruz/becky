package com.becky.becky.messagehandler;

import com.becky.becky.bot.WebSocketChatbotMessageHandler;
import com.becky.becky.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.Future;

public class WebSocketMessageHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketMessageHandler.class);

    @Autowired
    private WebSocketChatbotMessageHandler webSocketChatbotMessageHandler;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage inMessage) throws Exception {
        log.info("Incoming message: " + inMessage.getPayload());

        final Message message = new Message();
        message.setText(inMessage.getPayload());
        message.setChatId(session.getId());

        final Future<Message> messageFuture = webSocketChatbotMessageHandler.handleMessage(message);

        final TextMessage outMessage = new TextMessage(messageFuture.get().getText());
        log.info("Outgoing message: " + outMessage.getPayload());
        session.sendMessage(outMessage);
    }
}
