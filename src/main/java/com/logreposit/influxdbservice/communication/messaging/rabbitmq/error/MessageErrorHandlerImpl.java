package com.logreposit.influxdbservice.communication.messaging.rabbitmq.error;

import com.logreposit.influxdbservice.communication.messaging.exceptions.NotRetryableMessagingException;
import com.logreposit.influxdbservice.communication.messaging.rabbitmq.error.retry.RetryStrategy;
import com.logreposit.influxdbservice.communication.messaging.rabbitmq.sender.RabbitMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class MessageErrorHandlerImpl implements MessageErrorHandler
{
    private static final Logger logger = LoggerFactory.getLogger(MessageErrorHandlerImpl.class);

    private static final String MESSAGE_ERROR_COUNT_HEADER_KEY = "x-error-count";
    private static final String ERROR_EXCHANGE_NAME            = "error.x";

    private final RabbitMessageSender messageSender;
    private final RetryStrategy       retryStrategy;

    @Autowired
    public MessageErrorHandlerImpl(RabbitMessageSender messageSender, RetryStrategy retryStrategy)
    {
        this.messageSender = messageSender;
        this.retryStrategy = retryStrategy;
    }

    private static Long getMessageErrorCount(Message amqpMessage)
    {
        Object errorCountAsObject = amqpMessage.getMessageProperties().getHeaders().get(MESSAGE_ERROR_COUNT_HEADER_KEY);
        Long   errorCount         = 1L;

        if (errorCountAsObject != null)
            errorCount = (Long) errorCountAsObject;

        logger.info("Retrieved message error count: {}", errorCount);

        return errorCount;
    }

    private static boolean isNotRetryablePerDefinition(Exception exception)
    {
        if (exception instanceof MessageConversionException)
        {
            return true;
        }

        if (exception instanceof ClassCastException)
        {
            return true;
        }

        if (exception instanceof org.springframework.messaging.converter.MessageConversionException)
        {
            return true;
        }

        if (exception instanceof MethodArgumentNotValidException)
        {
            return true;
        }

        if (exception instanceof MethodArgumentTypeMismatchException)
        {
            return true;
        }

        return false;
    }

    @Override
    public void handleError(Message amqpMessage, Exception exception)
    {
        String originQueue = amqpMessage.getMessageProperties().getConsumerQueue();
        Long   errorCount  = getMessageErrorCount(amqpMessage);

        if (exception instanceof NotRetryableMessagingException || isNotRetryablePerDefinition(exception))
        {
            logger.error("Exception caught is not retryable. Publishing Message into error queue.");

            this.publishToExchange(
                    ERROR_EXCHANGE_NAME,
                    originQueue,
                    amqpMessage,
                    Collections.singletonMap(MESSAGE_ERROR_COUNT_HEADER_KEY, errorCount)
            );

            return;
        }

        String exchangeName = this.retryStrategy.getExchangeName(errorCount, ERROR_EXCHANGE_NAME);

        logger.info("Exception caught is retryable. Publishing Message to exchange '{}'.", exchangeName);

        this.publishToExchange(
                exchangeName,
                originQueue,
                amqpMessage,
                Collections.singletonMap(MESSAGE_ERROR_COUNT_HEADER_KEY, errorCount)
        );
    }

    private void publishToExchange(String exchangeName, String routingKey, Message amqpMessage, Map<String, Object> headers)
    {
        this.messageSender.send(exchangeName, routingKey, amqpMessage, headers);
    }
}
