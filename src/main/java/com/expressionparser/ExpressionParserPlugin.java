package com.expressionparser;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MessageNode;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.io.ByteArrayInputStream;
import com.expressionparser.javacc.*;
@Slf4j
@PluginDescriptor(
        name = "Expressions"
)
public class ExpressionParserPlugin extends Plugin
{
    Expressions eval = null;

    @Inject
    private Client client;

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) throws ParseException {
        float result = 0;

        String message = chatMessage.getMessage();
        try {
            if (eval == null) {
                eval = new Expressions(new ByteArrayInputStream(message.getBytes()));
            } else {
                eval.ReInit(new ByteArrayInputStream(message.getBytes()));
            }
            result = eval.Start();
            /* log.info(String.valueOf(result)); */
            String redrawMessage = new ChatMessageBuilder()
                    .append(ChatColorType.NORMAL)
                    .append(message)
                    .append(ChatColorType.HIGHLIGHT)
                    .append(" = " + result)
                    .build();

            final MessageNode messageNode = chatMessage.getMessageNode();
            messageNode.setRuneLiteFormatMessage(redrawMessage);
            client.refreshChat();

        } catch (Error e) { }
    }

}
