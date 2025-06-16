package com.danilko.carOpenData.dataDownloading.service;

import com.danilko.carOpenData.dto.utilDto.RegistrationsRegistryDto;

import java.io.FileNotFoundException;

public interface CsvProcessingService {

    RegistrationsRegistryDto parseCsvFile(String filePath) throws FileNotFoundException;
}
