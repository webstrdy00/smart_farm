package com.smart_farm.entity;

import com.smart_farm.dto.senser.SenserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board")
public class Senser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column
    private LocalDateTime date; // 현재 시간

    @Column(name = "pens")
    private boolean pen; // 팬 동작 상태값

    @Column(name = "moter")
    private boolean subMoter; // 서브모터 상태값

    @Column(name = "windows")
    private boolean window; // 창 상태값

    @Column()
    private boolean light;  // 조명 상태값

    @Column
    private float lightValue;  // 조도(아날로그 데이터)

    @Column()
    private float temperature; // 온도

    @Column
    private float humidity; // 습도

    @Column
    private float soilHumidity; // 토양 습도

    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

    public Senser(SenserRequestDto requestDto, User user){
        this.id = requestDto.getId();
        this.date = requestDto.getDate();
        this.lightValue = requestDto.getLightValue();
        this.temperature = requestDto.getTemperature();
        this.humidity = requestDto.getHumidity();
        this.soilHumidity = requestDto.getSoilHumidity();
        this.pen = requestDto.isPen();
        this.subMoter = requestDto.isSubMoter();
        this.window =requestDto.isWindow();
        this.light = requestDto.isLight();
        this.user = user;
    }

    public void update(){

    }
}
