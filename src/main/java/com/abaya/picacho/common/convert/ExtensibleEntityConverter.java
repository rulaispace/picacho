package com.abaya.picacho.common.convert;

import com.abaya.picacho.common.model.ExtensibleEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Set;

public class ExtensibleEntityConverter implements ConditionalGenericConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType() == targetType.getType()) return false;

        try {
            targetType.upcast(ExtensibleEntity.class);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return null;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return mapper.convertValue(source, targetType.getType());
    }
}