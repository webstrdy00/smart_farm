package com.smart_farm.dto.senser;

import com.smart_farm.entity.Senser;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SenserResponseDto {
    private Long id;
    private String nickname;
    private LocalDateTime date;
    private boolean pen;
    private boolean subMoter;
    private boolean light;
    private boolean window;
    private float lightValue;
    private float temperature;
    private float humidity;

    public SenserResponseDto(Senser senser){
        this.id = senser.getId();
        this.nickname = senser.getNickname();
        this.date = senser.getDate();
        this.pen = senser.isPen();
        this.subMoter = senser.isSubMoter();
        this.light = senser.isLight();
        this.window = senser.isWindow();
        this.lightValue = senser.getLightValue();
        this.temperature = senser.getTemperature();
        this.humidity = senser.getHumidity();
    }
}
