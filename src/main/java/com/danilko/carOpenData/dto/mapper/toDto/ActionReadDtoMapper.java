package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.readDto.ActionReadDto;
import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionReadDtoMapper implements Mapper<Action, ActionReadDto> {

    private final OperationReadDtoMapper operationReadDtoMapper;
    private final DepartmentReadDtoMapper departmentReadDtoMapper;
    private final VehicleReadDtoMapper vehicleReadDtoMapper;

    @Override
    public ActionReadDto mapFrom(Action action) {
        if (action == null) {
            throw new ValidationException("Action is null");
        }
        var id = action.getId();
        var person = action.getPerson();
        var regAddr = action.getRegAddr();
        var regDate = action.getRegDate();
        var operation = operationReadDtoMapper.mapFrom(action.getOperation());
        var department = departmentReadDtoMapper.mapFrom(action.getDepartment());
        var vehicle = vehicleReadDtoMapper.mapFrom(action.getVehicle());
        var numberPlate = action.getNumberPlate();
        return new ActionReadDto(id, person, regAddr, regDate, operation, department, vehicle, numberPlate);
    }

//    @Override
//    public ActionReadDto mapFrom(Action action) {
//        if (action == null) {
//            throw new ValidationException("Action is null");
//        }
//        var id = action.getId();
//        var person = action.getPerson();
//        var regAddr = action.getRegAddr();
//        var regDate = action.getRegDate();
//        var operation = operationReadDtoMapper.mapFrom(action.getOperation());
//        var department = departmentReadDtoMapper.mapFrom(action.getDepartment());
//        var vehicle = vehicleReadDtoMapper.mapFrom(action.getVehicle()).getVin();
//        var numberPlate = action.getNumberPlate();
//        return new ActionReadDto(id, person, regAddr, regDate, operation, department, vehicle, numberPlate);
//    }
}
