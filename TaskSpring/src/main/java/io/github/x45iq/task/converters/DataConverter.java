package io.github.x45iq.task.converters;

import jakarta.persistence.AttributeConverter;

import java.util.Base64;

public class DataConverter implements AttributeConverter<String, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(String s) {
        return Base64.getDecoder().decode(s);
    }

    @Override
    public String convertToEntityAttribute(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
}
