package com.smart_farm.service;

import com.smart_farm.dto.senser.DateAndValueResponseDto;
import com.smart_farm.dto.senser.SenserRequestDto;
import com.smart_farm.dto.senser.SenserResponseDto;
import com.smart_farm.entity.Senser;
import com.smart_farm.repository.SenserRepository;
import com.smart_farm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class SenserService {
    private final SenserRepository senserRepository;

    public void saveSenserValue(SenserRequestDto requestDto){
        Senser topSenser = senserRepository.findTopByOrderByIdDesc();
        if(topSenser == null){
            Senser senser = new Senser(requestDto);

            senserRepository.save(senser);
        }
        else if(topSenser.getTemperature() != requestDto.getTemperature()|| topSenser.getHumidity() != requestDto.getHumidity() || topSenser.isSubMoter() != requestDto.isSubMoter() || topSenser.isPen() != requestDto.isPen() || topSenser.isLed() != requestDto.isLed() ||
                topSenser.getSoilMoisture() != requestDto.getSoilMoisture() || topSenser.getCo2Value() != requestDto.getCo2Value())
        {
            Senser senser = new Senser(requestDto);

            senserRepository.save(senser);
        }
    }

    public List<SenserResponseDto> getValueAll(){
        List<Senser> sensers =  senserRepository.findAllByOrderByIdDesc();
        if (sensers == null)
            throw new RuntimeException("농장 없음");  // 내가 정의한 errorcode로 적용시키기

        return sensers.stream().map(SenserResponseDto::new).collect(Collectors.toList());
    }

    public SenserResponseDto getLatestValueAll(){
        Senser sensers =  senserRepository.findTopByOrderByIdDesc();
        if (sensers == null)
            throw new RuntimeException("농장 없음");  // 내가 정의한 errorcode로 적용시키기
        SenserResponseDto dto = new SenserResponseDto(sensers);
        return dto;
    }

    public List<DateAndValueResponseDto> getDateAndLightValue(){
        List<Senser> senserValueList = senserRepository.findAllByOrderByIdDesc();
        List<DateAndValueResponseDto> result = new ArrayList<>();
        for(Senser senser : senserValueList){
            DateAndValueResponseDto dto = new DateAndValueResponseDto(senser.getDate(), senser.getLightValue());
            result.add(dto);
        }

        return result;
    }

    public List<DateAndValueResponseDto> getDateAndTemp(){
        List<Senser> senserValueList = senserRepository.findAllByOrderByIdDesc();
        List<DateAndValueResponseDto> result = new ArrayList<>();

        for(Senser senser: senserValueList){
            DateAndValueResponseDto dto = new DateAndValueResponseDto(senser.getDate(), senser.getTemperature());
            result.add(dto);
        }

        return result;
    }

    public List<DateAndValueResponseDto> getDateAndHum(){
        List<Senser> senserValueList = senserRepository.findAllByOrderByIdDesc();
        List<DateAndValueResponseDto> result = new ArrayList<>();

        for(Senser senser: senserValueList){
            DateAndValueResponseDto dto = new DateAndValueResponseDto(senser.getDate(), senser.getHumidity());
            result.add(dto);
        }

        return result;
    }
    public List<DateAndValueResponseDto> getDateAndSoil() {
        List<Senser> senserValueList = senserRepository.findAllByOrderByIdDesc();
        List<DateAndValueResponseDto> result = new ArrayList<>();

        for(Senser senser: senserValueList){
            DateAndValueResponseDto dto = new DateAndValueResponseDto(senser.getDate(), senser.getSoilMoisture());
            result.add(dto);
        }

        return result;
    }
    public List<DateAndValueResponseDto> getDateAndCo2() {
        List<Senser> senserValueList = senserRepository.findAllByOrderByIdDesc();
        List<DateAndValueResponseDto> result = new ArrayList<>();

        for (Senser senser: senserValueList){
            DateAndValueResponseDto dto = new DateAndValueResponseDto(senser.getDate(), senser.getCo2Value());
            result.add(dto);
        }
        return  result;
    }

    public void deleteSenser(Long senserId) {
        Optional<Senser> senserOptional = senserRepository.findById(senserId);
        senserOptional.ifPresent(senser -> senserRepository.delete(senser));
    }
//    public String boardDelete(BoardRequestDto requestDto){
//
//    }

//    public BoardResponseDto getValueById(Long id){
//        Board board = boardRepository.
//    }
}
