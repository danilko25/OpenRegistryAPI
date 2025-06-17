package com.danilko.carOpenData.dataDownloading.service.impl;

import com.danilko.carOpenData.dataDownloading.impl.CsvProcessingServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CsvProcessingServiceImplTest {

    @Autowired
    CsvProcessingServiceImpl csvProcessingService;

    private final String filePath = "src/main/resources/downloads/unZipDirectory/tz_opendata_z01012024_po01012025.csv";

    @Test
    void testParsingCSV(){
        csvProcessingService.parseCsvFile(filePath);
    }

}