package com.logreposit.influxdbservice.communication.messaging.handler;

import com.logreposit.influxdbservice.communication.messaging.common.Message;
import com.logreposit.influxdbservice.communication.messaging.common.MessageType;
import com.logreposit.influxdbservice.communication.messaging.exceptions.MessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.NotRetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.exceptions.RetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api.EventBMV600LogdataReceivedMessageProcessor;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api.EventCmiLogdataReceivedMessageProcessor;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api.EventDeviceCreatedMessageProcessor;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api.EventLacrosseTXLogdataReceivedMessageProcessor;
import com.logreposit.influxdbservice.communication.messaging.handler.processors.logreposit_api.EventUserCreatedMessageProcessor;
import com.logreposit.influxdbservice.utils.RequestCorrelation;
import com.logreposit.influxdbservice.utils.logging.LoggingUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerImpl implements MessageHandler
{
    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerImpl.class);

    private final EventUserCreatedMessageProcessor               eventUserCreatedMessageProcessor;
    private final EventDeviceCreatedMessageProcessor             eventDeviceCreatedMessageProcessor;
    private final EventCmiLogdataReceivedMessageProcessor        eventCmiLogdataReceivedMessageProcessor;
    private final EventBMV600LogdataReceivedMessageProcessor     eventBMV600LogdataReceivedMessageProcessor;
    private final EventLacrosseTXLogdataReceivedMessageProcessor eventLacrosseTXLogdataReceivedMessageProcessor;

    @Autowired
    public MessageHandlerImpl(EventUserCreatedMessageProcessor eventUserCreatedMessageProcessor,
                              EventDeviceCreatedMessageProcessor eventDeviceCreatedMessageProcessor,
                              EventCmiLogdataReceivedMessageProcessor eventCmiLogdataReceivedMessageProcessor,
                              EventBMV600LogdataReceivedMessageProcessor eventBMV600LogdataReceivedMessageProcessor,
                              EventLacrosseTXLogdataReceivedMessageProcessor eventLacrosseTXLogdataReceivedMessageProcessor)
    {
        this.eventUserCreatedMessageProcessor               = eventUserCreatedMessageProcessor;
        this.eventDeviceCreatedMessageProcessor             = eventDeviceCreatedMessageProcessor;
        this.eventCmiLogdataReceivedMessageProcessor        = eventCmiLogdataReceivedMessageProcessor;
        this.eventBMV600LogdataReceivedMessageProcessor     = eventBMV600LogdataReceivedMessageProcessor;
        this.eventLacrosseTXLogdataReceivedMessageProcessor = eventLacrosseTXLogdataReceivedMessageProcessor;
    }

    @Override
    public void handleMessage(Message message) throws MessagingException
    {
        logger.info("Retrieved message: {}", LoggingUtils.serialize(message));

        setCorrelationId(message);
        checkIfMessageIsValidOrThrowNotRetryableException(message);

        MessageType messageType = getTypeOfMessage(message);

        switch (messageType)
        {
            case EVENT_CMI_LOGDATA_RECEIVED:
                this.eventCmiLogdataReceivedMessageProcessor.processMessage(message);
                break;
            case EVENT_BMV_600_LOGDATA_RECEIVED:
                this.eventBMV600LogdataReceivedMessageProcessor.processMessage(message);
                break;
            case EVENT_USER_CREATED:
                this.eventUserCreatedMessageProcessor.processMessage(message);
                break;
            case EVENT_DEVICE_CREATED:
                this.eventDeviceCreatedMessageProcessor.processMessage(message);
                break;
            case EVENT_LACROSSE_TX_LOGDATA_RECEIVED:
                this.eventLacrosseTXLogdataReceivedMessageProcessor.processMessage(message);
                break;
            default:
                logger.info("No handler for MessageType '{}' existent. Skipping that one.", messageType.toString());
                break;
        }
    }

    private static void setCorrelationId(Message message)
    {
        if (message.getMetaData() != null && StringUtils.isNotEmpty(message.getMetaData().getCorrelationId()))
        {
            RequestCorrelation.setCorrelationId(message.getMetaData().getCorrelationId());
        }
        else
        {
            RequestCorrelation.setCorrelationId(null);
        }
    }

    private static void checkIfMessageIsValidOrThrowNotRetryableException(Message message) throws NotRetryableMessagingException
    {
        if (message == null)
            throw new NotRetryableMessagingException("Message received was null.");

        if (StringUtils.isBlank(message.getType()))
            throw new NotRetryableMessagingException("Message received has blank type string.");
    }

    private static MessageType getTypeOfMessage(Message message) throws RetryableMessagingException
    {
        try
        {
            MessageType messageType = MessageType.valueOf(message.getType());

            return messageType;
        }
        catch (IllegalArgumentException exception)
        {
            logger.error("Could not find appropriate MessageType instance for value '{}'", message.getType());
            throw new RetryableMessagingException(String.format("Could not find appropriate MessageType instance for value '%s'", message.getType()), exception);
        }
    }
}
