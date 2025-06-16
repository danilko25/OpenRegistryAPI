package com.danilko.carOpenData.controller;

import com.danilko.carOpenData.dto.readDto.ActionReadDto;
import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.service.ActionService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actions")
public class ActionController {

    private final ActionService actionService;

    @GetMapping()
    public ResponseEntity<List<ActionReadDto>> filterActions(
            @ModelAttribute
            @Parameter(
            description = "Get list of registration actions with filtering",
            example = "vehicle.brand=subaru&vehicle.model=outback&vehicle.makeYearFrom=2012&vehicle.makeYearTo=2014&"
            )
            ActionFilterDto actionFilterDto
    ){
        var list = actionService.findAll(actionFilterDto);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{filterId}")
    public List<ActionReadDto> filterActionsByStatisticFilter(@PathVariable("filterId") Long filterId){
        return actionService.findAllByStatisticFilter(filterId);
    }

}








