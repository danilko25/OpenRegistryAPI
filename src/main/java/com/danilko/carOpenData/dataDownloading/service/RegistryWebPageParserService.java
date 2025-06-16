package com.danilko.carOpenData.dataDownloading.service;

import com.danilko.carOpenData.entity.FileDownloadHref;

import java.util.List;

public interface RegistryWebPageParserService {
    List<FileDownloadHref> getFilesDownloadHrefs();
}
