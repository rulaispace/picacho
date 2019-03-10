package com.abaya.picacho.common.convert;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Set;

@Slf4j
public class GenericConverter implements ConditionalGenericConverter {
    private final ModelMapper mapper = new ModelMapper();

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
        // return mapper.convertValue(source, targetType.getType());
       /* try {
            Constructor constructor = targetType.getType().getDeclaredConstructor();
            constructor.setAccessible(true);
            Object target = constructor.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new IllegalArgumentException("Fail to construct the target type", e);
        }*/
       return mapper.map(source, targetType.getType());
    }
}
