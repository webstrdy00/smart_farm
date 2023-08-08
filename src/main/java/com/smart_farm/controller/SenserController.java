package com.smart_farm.controller;

import com.smart_farm.dto.senser.response.DateAndValueResponseDto;
import com.smart_farm.dto.senser.request.SenserRequestDto;
import com.smart_farm.dto.senser.response.SenserResponseDto;
import com.smart_farm.service.SenserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/senser")
public class SenserController {  //HttpServerRequest
    private final SenserService senserService;

    // 값 저장하기
    @ApiOperation(value = "데이터 저장 API")
    @PostMapping("/data")
    public void saveValue(@RequestBody SenserRequestDto requestDto) {
        senserService.saveSenserValue(requestDto);
    }

    // 모든값 불러오기
    @ApiOperation(value = "모든 데이터 호출 API")
    @GetMapping("")
    public List<SenserResponseDto> getAllValue() {
        List<SenserResponseDto> dto = senserService.getValueAll();
        return dto;
    }

    // 최신값 불러오기
    @ApiOperation(value = "최신 데이터 호출 API")
    @GetMapping("/latest")
    public SenserResponseDto getLatestAllValue() {
        SenserResponseDto dto = senserService.getLatestValueAll();
        return dto;
    }

    // 특정값 불러오기
    @ApiOperation(value = "특정 데이터 호출 API")
    @GetMapping("/value")
    public List<DateAndValueResponseDto> getValue(@RequestParam String value) {
        List<DateAndValueResponseDto> values = new ArrayList<>();
        switch (value) {
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
    // 특정값 삭제하기
    @ApiOperation(value = "특정 데이터 삭제 API")
    @DeleteMapping("/{senserId}")
    public void deleteSenserValue(@PathVariable(name = "senserId") Long senserId){
        senserService.deleteSenser(senserId);
    }

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
