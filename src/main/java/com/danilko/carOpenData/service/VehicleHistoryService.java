package com.danilko.carOpenData.service;

import com.danilko.carOpenData.dto.utilDto.VehicleHistoryDto;

public interface VehicleHistoryService {
    VehicleHistoryDto findByNumberPlate(String numberPlate);
    VehicleHistoryDto findByVin(String vin);
}
