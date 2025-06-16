package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.readDto.VehicleReadDto;
import com.danilko.carOpenData.entity.Vehicle;
import com.danilko.carOpenData.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class VehicleReadDtoMapper implements Mapper<Vehicle, VehicleReadDto> {
    @Override
    public VehicleReadDto mapFrom(Vehicle vehicle) {
        if (vehicle == null) {
            throw new ValidationException("Vehicle is null");
        }
        return new VehicleReadDto(vehicle.getVin(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getMakeYear(),
                vehicle.getColor(),
                vehicle.getKind(),
                vehicle.getBodyType(),
                vehicle.getPurpose(),
                vehicle.getFuelType(),
                vehicle.getEngineCapacity(),
                vehicle.getOwnWeight(),
                vehicle.getTotalWeight());
    }
}
