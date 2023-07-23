package com.smart_farm.dto.senser;

import com.smart_farm.entity.Senser;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DateAndValueResponseDto {
    private float value;
    private LocalDateTime date;

    public DateAndValueResponseDto(LocalDateTime date, float value){
        this.date = date;
        this.value = value;
    }
}
