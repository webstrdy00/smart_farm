package com.smart_farm.dto.senser;

import com.smart_farm.entity.Senser;
import com.smart_farm.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenserRequestDto {

    //@ApiModelProperty(value = "게시글 번호", example = "1", hidden = true)
    private Long id;


    //@ApiModelProperty(value = "현재 시간", example = "yyyy-MM-dd HH:mm", hidden = true)
    private LocalDateTime date = LocalDateTime.now();

    //@ApiModelProperty(value = "팬 동작 상태", example = "1", hidden = true)
    private boolean pen;

    //@ApiModelProperty(value = "서브모터 동작 상태", example = "1", hidden = true)
    private boolean subMoter;

    //@ApiModelProperty(value = "조명 동작 상태", example = "1", hidden = true)
    private boolean LED;

    //@ApiModelProperty(value = "조도 값", example = "0.0", hidden = true)
    private float lightValue;

    //@ApiModelProperty(value = "온도 값", example = "0.0", hidden = true)
    private float temperature;

    //@ApiModelProperty(value = "습도 값", example = "0.0", hidden = true)
    private float humidity;

    private float soilMoisture;

    private float co2Value;

    //@ApiModelProperty(value = "사용자 정보", hidden = true)
    private User user;

    public Senser toEntity(){
        Senser senser = Senser.builder()
                .id(id)
                .date(date)
                .pen(pen)
                .subMoter(subMoter)
                .LED(LED)
                .lightValue(lightValue)
                .temperature(temperature)
                .humidity(humidity)
                .soilMoisture(soilMoisture)
                .co2Value(co2Value)
                .nickname(user.getNickname())
                .build();

        return senser;
    }
}
