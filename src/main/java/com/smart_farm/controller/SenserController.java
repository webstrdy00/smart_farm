package com.smart_farm.controller;

import com.smart_farm.dto.senser.DateAndValueResponseDto;
import com.smart_farm.dto.senser.SenserRequestDto;
import com.smart_farm.dto.senser.SenserResponseDto;
import com.smart_farm.repository.SenserRepository;
import com.smart_farm.service.SenserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SenserController {  //HttpServerRequest
    private final SenserService senserService;

    // 값 저장하기
    @PostMapping("/senser/data")
    public String saveValue(@RequestBody SenserRequestDto requestDto) {
        return senserService.saveSenserValue(requestDto);
    }

    // 모든값 불러오기
    @GetMapping("/sensers")
    public List<SenserResponseDto> getAllValue() {
        List<SenserResponseDto> dto = senserService.getValueAll();
        return dto;
    }

    // 최신값 불러오기
    @GetMapping("/latest/sensers")
    public SenserResponseDto getLatestAllValue() {
        SenserResponseDto dto = senserService.getLatestValueAll();
        return dto;
    }

    // 특정값 불러오기
    @GetMapping("/senser")
    public List<DateAndValueResponseDto> getValue(@RequestParam String senser) {
        List<DateAndValueResponseDto> values = new ArrayList<>();
        switch (senser) {
            case "lightValue":
                values = senserService.getDateAndLightValue();
                break;
            case "temperature":
                values = senserService.getDateAndTemp();
                break;
            case "humidity":
                values = senserService.getDateAndHum();
                break;
            case "soilMoisture":
                values = senserService.getDateAndSoil();
                break;
            case "co2Value":
                values = senserService.getDateAndCo2();
                break;
        }
        return values;
    }
//    // 값 삭제하기
//    @DeleteMapping("/delete/board")
//    public String deleteSenserValue(@RequestBody BoardRequestDto requestDto){
//        return boardService.boardDelete(requestDto);
//    }

//    @PutMapping("/put/board/{id}")
//    public String updateSenser(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
//        Optional<Board> optionalBoard = boardRepository.findById(id);
//
//        if(optionalBoard.isPresent()){
//            Board board = optionalBoard.get();
//
//            board.
//        }
//    }
//    @GetMapping("/board/id")
//    public BoardResponseDto getSenserValue(@PathVariable Long id){
//        return boardService.get
//    }

//    @DeleteMapping
//    public ResponseEntity<String> deleteFarm(@PathVariable Long id){
//
//    }
}
