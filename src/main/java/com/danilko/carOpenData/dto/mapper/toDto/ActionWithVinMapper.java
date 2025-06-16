package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.readDto.ActionReadDto;
import com.danilko.carOpenData.dto.readDto.ActionWithVin;
import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionWithVinMapper implements Mapper<Action, ActionWithVin> {

    private final OperationReadDtoMapper operationReadDtoMapper;

    private final DepartmentReadDtoMapper departmentReadDtoMapper;

    @Override
    public ActionWithVin mapFrom(Action action) {
        var res = new ActionWithVin();
        if (action == null) {
            throw new ValidationException("Action is null");
        }

        var person = action.getPerson();
        var regAddr = action.getRegAddr();
        var regDate = action.getRegDate();
        var operation = operationReadDtoMapper.mapFrom(action.getOperation());
        var department = departmentReadDtoMapper.mapFrom(action.getDepartment());
        var vehicleVin = action.getVehicle().getVin();
        var numberPlate = action.getNumberPlate();

        res.setPerson(person);
        res.setRegAddr(regAddr);
        res.setRegDate(regDate);
        res.setOperationReadDto(operation);
        res.setDepartmentReadDto(department);
        res.setVehicleVin(vehicleVin);
        res.setNumberPlate(numberPlate);

        return res;
    }
}
