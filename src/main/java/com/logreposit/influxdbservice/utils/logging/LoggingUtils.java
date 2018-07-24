package com.logreposit.influxdbservice.utils.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUtils
{
    private static final Logger logger = LoggerFactory.getLogger(LoggingUtils.class);

    private LoggingUtils()
    {
    }

    public static String serializeForLoggingWithDefault(Object object)
    {
        return serializeForLogging(object, "<NOT_SERIALIZABLE>");
    }

    public static String serializeForLogging(Object object, String defaultText)
    {
        try
        {
            String serialized = serializeForLogging(object);
            return serialized;
        }
        catch (JsonProcessingException exception)
        {
            logger.warn(String.format("Could not serialize object, returning defaultValue \"%s\" instead.", defaultText));
            return defaultText;
        }
    }

    public static String serializeForLogging(Object object) throws JsonProcessingException
    {
        ObjectMapper objectMapper = getObjectMapper();
        String serialized = objectMapper.writeValueAsString(object);

        return serialized;
    }

    public static String getLogForException(Exception exception)
    {
        String cls = exception.getClass().getName();
        String message = exception.getMessage();
        String stackTrace = ExceptionUtils.getStackTrace(exception);

        String logline = String.format("[%s] %s%n%s", cls, message, stackTrace);
        return logline;
    }

    public static ObjectMapper getObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new ISO8601DateFormat());
        return objectMapper;
    }
}
