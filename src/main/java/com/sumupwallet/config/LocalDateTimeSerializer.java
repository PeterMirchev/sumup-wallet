package com.sumupwallet.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private static final String DTF_FORMAT = "MM-dd-yyyy HH:mm";

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DTF_FORMAT);

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeString(value.format(dtf));
    }
}
