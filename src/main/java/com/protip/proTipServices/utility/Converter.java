package com.protip.proTipServices.utility;

import com.protip.proTipServices.model.Message;
import com.protip.proTipServices.model.SendMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility method for converting between types
 */
public class Converter {

    /**
     * Method for getting {@link List<SendMessage>} from {@link List<Message>}
     *
     * @param messages {@link List<Message>} the messages list to be converted
     * @return {@link List<SendMessage>}     the converted list of messages
     */
    public static List<SendMessage> fromMessageToSendMessage(final List<Message> messages) {
        final List<SendMessage> sendMessages = new ArrayList<>();

        for (final Message message : messages) {
            final SendMessage sendMessage = new SendMessage(message.getSenderName(), message.getMessage(), message.getDateTime());

            sendMessages.add(sendMessage);
        }

        return sendMessages;
    }
}
