package com.decathlon.sports.converter;

import com.decathlon.sports.dao.model.Sport;
import com.decathlon.sports.dto.SportDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SportDtoToEntityConverter implements Converter<SportDTO, Sport> {

    @Override
    public Sport convert(SportDTO source) {
        Sport entity = new Sport();
        entity.setId(source.getId());
        entity.setName(source.getAttributes().getName());
        entity.setDescription(source.getAttributes().getDescription());
        entity.setSlug(source.getAttributes().getSlug());
        return entity;
    }

    @Override
    public <U> Converter<SportDTO, U> andThen(Converter<? super Sport, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}