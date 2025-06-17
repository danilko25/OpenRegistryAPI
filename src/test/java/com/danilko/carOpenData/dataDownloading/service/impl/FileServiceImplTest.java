package com.danilko.carOpenData.dataDownloading.service.impl;

import com.danilko.carOpenData.dataDownloading.impl.FileServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FileServiceImplTest {

    @Autowired
    FileServiceImpl fileService;

    String url_10_2024 = "https://data.gov.ua/dataset/0ffd8b75-0628-48cc-952a-9302f9799ec0/resource/c3ffecc4-bb5c-4102-b761-6dcfeb60b4fe/download/reestrtz2024.zip";

    @Test
    void test() throws IOException {
        fileService.downloadAndExtractZip(url_10_2024, "Test2024");
    }






}