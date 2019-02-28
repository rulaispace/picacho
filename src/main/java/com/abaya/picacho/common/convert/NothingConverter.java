package com.abaya.picacho.common.convert;

import com.abaya.picacho.common.model.Nothing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class NothingConverter implements ConditionalGenericConverter {
    private static final Logger logger = LoggerFactory.getLogger(NothingConverter.class);

    private DefaultConversionService conversionService = new DefaultConversionService();

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getType() == Nothing.class && sourceType.getType() != targetType.getType();
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Nothing.class, Object.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (targetType.getType() == Date.class) return null;

        try {
            return conversionService.convert("", targetType);
        } catch (ConversionFailedException ex) {
            return conversionService.convert("0", targetType);
        }
    }
}