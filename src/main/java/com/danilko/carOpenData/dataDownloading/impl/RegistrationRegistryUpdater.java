package com.danilko.carOpenData.dataDownloading.impl;

import com.danilko.carOpenData.dataDownloading.service.CsvProcessingService;
import com.danilko.carOpenData.dataDownloading.service.RegistryDataUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.danilko.carOpenData.entity.FileDownloadHref;
import com.danilko.carOpenData.entity.InfoType;
import com.danilko.carOpenData.repository.*;
import com.danilko.carOpenData.service.*;
import com.danilko.carOpenData.dataDownloading.service.FileService;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RegistrationRegistryUpdater implements RegistryDataUpdater {
    private final CsvProcessingService csvProcessingService;
    private final OperationRepository operationRepository;
    private final DepartmentRepository departmentRepository;
    private final VehicleRepository vehicleRepository;
    private final ActionRepository actionRepository;
    private final FileDownloadHrefService fileDownloadHrefService;
    private final RegistryWebPageParserServiceImpl registryWebPageParserService;
    private final ActionService actionService;
    private final FileService fileService;

    @Override
    public void checkAndUpdate() throws IOException {

        var savedHrefsMap = fileDownloadHrefService.loadAllRegistrationsAsMap();
        var actualHrefsListFromWeb = registryWebPageParserService.getFilesDownloadHrefs();

        for (FileDownloadHref actualWebHref : actualHrefsListFromWeb) {

            var urlFromWeb = actualWebHref.getUrl();
            var yearFromWeb = actualWebHref.getDateKey().getYear();
            var savedHrefForYear = savedHrefsMap.get(yearFromWeb);

            if (savedHrefForYear != null) {
                if (!savedHrefForYear.getUrl().equals(urlFromWeb)) {
                    actionService.deleteAllByRegDateYear(yearFromWeb);
                    downloadFullRegistry(urlFromWeb, yearFromWeb);
                    updateHref(actualWebHref);
                }
            } else {
                downloadFullRegistry(urlFromWeb, yearFromWeb);
                saveHref(actualWebHref);
            }
        }
    }

    private void downloadFullRegistry(String urlFromWeb, Integer yearFromWeb) throws IOException {
        System.out.println("DEBUG RegistrationRegistryUpdater: Downloading csv for year: " + yearFromWeb);
        var pathOfDownloadedFile = fileService.downloadAndExtractZip(urlFromWeb, String.valueOf(yearFromWeb));
        save(pathOfDownloadedFile);
    }

    @Transactional
    protected void save(String csvFilePath) throws IOException {
        System.out.println("DEBUG RegistrationRegistryUpdater: Saving registration entities");
        var registry = csvProcessingService.parseCsvFile(csvFilePath);
        departmentRepository.updateAll(registry.getDepartmentsToUpdate());
        operationRepository.updateAll(registry.getOperationsToUpdate());
        vehicleRepository.updateAll(registry.getVehiclesToUpdate());

        departmentRepository.saveAll(registry.getNewDepartments());
        operationRepository.saveAll(registry.getNewOperations());
        vehicleRepository.saveAll(registry.getNewVehicles());
        actionRepository.saveAll(registry.getNewActions());
    }

    private void saveHref(FileDownloadHref href) {
        System.out.println("DEBUG RegistrationRegistryUpdater: Saving registrations FileDownloadingHref");
        href.setDownloadingDate(LocalDateTime.now());
        href.setInfoType(InfoType.REGISTRATIONS);
        fileDownloadHrefService.save(href);
    }

    private void updateHref(FileDownloadHref href) {
        System.out.println("DEBUG RegistrationRegistryUpdater: Updating registrations FileDownloadingHref");
        href.setDownloadingDate(LocalDateTime.now());
        href.setInfoType(InfoType.REGISTRATIONS);
        fileDownloadHrefService.updateRegistrationsHref(href);
    }
}
