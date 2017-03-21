package com.rohaky.board.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.concurrent.atomic.AtomicInteger;

@Converter(autoApply = true)
public class AtomicIntToIntConverter implements AttributeConverter<AtomicInteger, Integer> {

  @Override
  public Integer convertToDatabaseColumn(AtomicInteger attribute) {
    return attribute != null ? attribute.get() : null;
  }

  @Override
  public AtomicInteger convertToEntityAttribute(Integer dbData) {
    return new AtomicInteger(dbData);
  }
}
