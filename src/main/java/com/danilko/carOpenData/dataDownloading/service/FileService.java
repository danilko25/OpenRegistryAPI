package com.danilko.carOpenData.dataDownloading.service;

import java.io.IOException;

public interface FileService {
    String downloadAndExtractZip(String fileUrl, String fileName) throws IOException;
    String downloadFileFromUrl(String fileUrl, String fileName) throws IOException;
}
