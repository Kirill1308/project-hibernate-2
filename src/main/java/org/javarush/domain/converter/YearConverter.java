package org.javarush.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Year;

import static java.util.Objects.isNull;

@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, Short> {
    @Override
    public Short convertToDatabaseColumn(Year attribute) {
        if (!isNull(attribute)) {
            return (short) attribute.getValue();
        }
        return null;
    }

    @Override
    public Year convertToEntityAttribute(Short dbData) {
        if (!isNull(dbData)) {
            return Year.of(dbData);
        }
        return null;
    }
}
