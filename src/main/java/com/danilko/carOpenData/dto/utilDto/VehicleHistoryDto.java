package com.danilko.carOpenData.dto.utilDto;

import com.danilko.carOpenData.dto.readDto.ActionReadDto;
import com.danilko.carOpenData.dto.readDto.ActionWithVin;
import com.danilko.carOpenData.dto.readDto.VehicleReadDto;
import com.danilko.carOpenData.dto.readDto.WantedVehicleReadDto;
import com.danilko.carOpenData.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleHistoryDto {
    private Optional<VehicleReadDto> vehicleReadDto;
    private Optional<WantedVehicleReadDto> stealInfoDto;
    private List<ActionReadDto> actionReadDtoList;
}
