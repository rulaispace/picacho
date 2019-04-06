package com.abaya.picacho.common.convert;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Set;

@Slf4j
public class GenericConverter implements ConditionalGenericConverter {
    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (sourceType.getType() == targetType.getType()) return false;
        return true;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return null;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
       return mapper.map(source, targetType.getType());
    }
}
