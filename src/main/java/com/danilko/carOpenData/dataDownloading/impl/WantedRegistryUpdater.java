package com.danilko.carOpenData.dataDownloading.impl;

import com.danilko.carOpenData.dataDownloading.service.FileService;
import com.danilko.carOpenData.dataDownloading.service.RegistryDataUpdater;
import com.danilko.carOpenData.dataDownloading.service.WantedVehiclesWebPageParser;
import com.danilko.carOpenData.entity.FileDownloadHref;
import com.danilko.carOpenData.entity.InfoType;
import com.danilko.carOpenData.entity.WantedVehicle;
import com.danilko.carOpenData.repository.WantedVehiclesRepository;
import com.danilko.carOpenData.service.FileDownloadHrefService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WantedRegistryUpdater implements RegistryDataUpdater {
    private final FileDownloadHrefService fileDownloadHrefService;
    private final WantedVehiclesWebPageParser wantedVehiclesWebPageParser;
    private final FileService fileService;
    private final ObjectMapper jacksonObjectMapper;
    private final WantedVehiclesRepository wantedVehiclesRepository;


    @Override
    public void checkAndUpdate() throws IOException {
        var savedWantedRegistryOpt = fileDownloadHrefService.getWantedFileDownloadHref();
        FileDownloadHref actualWantedFileDownloadingHref;
        int attempts = 0;
        while (true) {
            try {
                actualWantedFileDownloadingHref = wantedVehiclesWebPageParser.parseFileDownloadHref();
                break;
            } catch (IOException e){
                attempts++;
                if (attempts>=3){
                    throw new RuntimeException("Failed to load wantedVehicles page after 3 retries");
                }
            }
        }

        var actualUrl = actualWantedFileDownloadingHref.getUrl();

        if (savedWantedRegistryOpt.isPresent()){
            var saved = savedWantedRegistryOpt.get();
            if (saved.getDateKey().isBefore(actualWantedFileDownloadingHref.getDateKey())){
                if (!actualUrl.equals(saved.getUrl())){
                    downloadRegistry(actualUrl);
                    wantedVehiclesRepository.deleteAll();
                    updateWantedRegistry(actualWantedFileDownloadingHref);
                }
            }
        }else {
            downloadRegistry(actualUrl);
            saveWantedRegistry(actualWantedFileDownloadingHref);
        }
    }

    @Transactional
    protected void downloadRegistry(String url) throws IOException {
        System.out.println("DEBUG WantedRegistryUpdater: Downloading and saving entities Wanted.json");
        var jsonFilePath = fileService.downloadFileFromUrl(url, "WantedVehicles");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        jacksonObjectMapper.setDateFormat(df);
        var list = jacksonObjectMapper.readValue(new File(jsonFilePath), new TypeReference<List<WantedVehicle>>(){});
        wantedVehiclesRepository.saveAll(list);
    }

    private void saveWantedRegistry(FileDownloadHref fileDownloadHref) {
        System.out.println("DEBUG WantedRegistryUpdater: Saving Wanted FileDownloadingHref");
        fileDownloadHref.setDownloadingDate(LocalDateTime.now());
        fileDownloadHref.setInfoType(InfoType.WANTED);
        fileDownloadHrefService.save(fileDownloadHref);
    }

    private void updateWantedRegistry(FileDownloadHref fileDownloadHref) {
        System.out.println("DEBUG WantedRegistryUpdater: Updating Wanted FileDownloadingHref");
        fileDownloadHref.setDownloadingDate(LocalDateTime.now());
        fileDownloadHrefService.updateWantedVehiclesHref(fileDownloadHref);
    }


}
