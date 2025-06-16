package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.readDto.WantedVehicleReadDto;
import com.danilko.carOpenData.entity.WantedVehicle;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@Component
public class WantedVehicleReadDtoMapper implements Mapper<WantedVehicle, WantedVehicleReadDto> {
    @Override
    public WantedVehicleReadDto mapFrom(WantedVehicle stealInfo) {
        Date insertDate = stealInfo.getInsertDate();
        Date illegalSeizureDate = stealInfo.getIllegalSeizureDate();
        Instant insertDateInstant = insertDate.toInstant();
        Instant illegalSeizureDateInstant = illegalSeizureDate.toInstant();
        ZoneId zoneId = ZoneId.of("Europe/Kiev");

        return new WantedVehicleReadDto(stealInfo.getVin(),
                stealInfo.getBrandModel(),
                stealInfo.getCarType(),
                stealInfo.getColor(),
                stealInfo.getNumberPlate(),
                stealInfo.getChassisNumber(),
                stealInfo.getEngineNumber(),
                illegalSeizureDateInstant.atZone(zoneId).toLocalDateTime(),
                stealInfo.getOrganUnit(),
                insertDateInstant.atZone(zoneId).toLocalDateTime()
                );
    }
}
