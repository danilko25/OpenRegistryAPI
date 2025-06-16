package com.danilko.carOpenData.dataDownloading.service;

import com.danilko.carOpenData.entity.FileDownloadHref;

import java.io.IOException;

public interface WantedVehiclesWebPageParser {
    FileDownloadHref parseFileDownloadHref() throws IOException;
}
