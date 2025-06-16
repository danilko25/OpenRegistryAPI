package com.danilko.carOpenData.service;

import com.danilko.carOpenData.entity.FileDownloadHref;

import java.util.Map;
import java.util.Optional;

public interface FileDownloadHrefService {

    Map<Integer, FileDownloadHref> loadAllRegistrationsAsMap();

    Optional<FileDownloadHref> getWantedFileDownloadHref();

    void save(FileDownloadHref fileDownloadHref);

//    void update(FileDownloadHref fileDownloadHref);
    void updateWantedVehiclesHref(FileDownloadHref fileDownloadHref);
    void updateRegistrationsHref(FileDownloadHref fileDownloadHref);
}
