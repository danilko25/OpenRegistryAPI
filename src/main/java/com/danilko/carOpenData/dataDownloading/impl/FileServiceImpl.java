package com.danilko.carOpenData.dataDownloading.impl;

import com.danilko.carOpenData.dataDownloading.service.FileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class FileServiceImpl implements FileService {

    @Value("${application.downloads-directory-path}")
    private String downloadsDirectory;

    @Value("${application.unzip-directory-path}")
    private String unzipDirectory;


    @Override
    public String downloadAndExtractZip(String fileUrl, String fileName) throws IOException {
        String zipPath = downloadFile(fileUrl, downloadsDirectory + "/" + fileName + ".zip");
        return unzipFile(zipPath, unzipDirectory);
    }

    @Override
    public String downloadFileFromUrl(String fileUrl, String fileName) throws IOException {
        return downloadFile(fileUrl, downloadsDirectory + "/" + fileName + ".json");
    }

    private String downloadFile(String fileUrl, String destinationFilePath) {
        File destinationDir = new File(downloadsDirectory);

        if (!destinationDir.exists()) destinationDir.mkdirs();

        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destinationFilePath)) {
            byte dataBuffer[] = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 8192)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return destinationFilePath;
    }

    private String unzipFile(String zipFilePath, String extractDirectoryPath) throws IOException {
        File destinationDir = new File(extractDirectoryPath);

        if (!destinationDir.exists()) destinationDir.mkdirs();

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            File unzipFile;

            if (entry != null) {
                 unzipFile = new File(extractDirectoryPath, entry.getName());
                if (!entry.isDirectory()) {
                    extractFile(zipIn, unzipFile);
                }
                zipIn.closeEntry();
                return unzipFile.getPath();
            }
            else throw new IOException("Zip file does not exist");
           }
    }

    private void extractFile(ZipInputStream zipIn, File filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[8192];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
