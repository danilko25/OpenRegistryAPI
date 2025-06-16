package com.danilko.carOpenData.service.impl;

import com.danilko.carOpenData.entity.FileDownloadHref;
import com.danilko.carOpenData.entity.InfoType;
import com.danilko.carOpenData.repository.FileDownloadHrefRepository;
import com.danilko.carOpenData.service.FileDownloadHrefService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileDownloadHrefServiceImpl implements FileDownloadHrefService {

    private final FileDownloadHrefRepository fileDownloadHrefRepository;

    @Override
    public Map<Integer, FileDownloadHref> loadAllRegistrationsAsMap() {
        var list = fileDownloadHrefRepository.findAllForRegistrations();
        var result = new HashMap<Integer, FileDownloadHref>();
        for (FileDownloadHref fileDownloadHref : list) {
            result.put(fileDownloadHref.getDateKey().getYear(), fileDownloadHref);
        }
        return result;
    }

    @Override
    public Optional<FileDownloadHref> getWantedFileDownloadHref() {
        return fileDownloadHrefRepository.findByInfoType(InfoType.WANTED);
    }

    @Override
    public void save(FileDownloadHref fileDownloadHref) {
        fileDownloadHrefRepository.save(fileDownloadHref);
    }


//    @Transactional
//    @Override
//    public void update(FileDownloadHref fileDownloadHref) {
//        if (fileDownloadHrefRepository.findByDateKey(fileDownloadHref.getDateKey())!=null) {
//            fileDownloadHrefRepository.updateFileDownloadHref(fileDownloadHref.getFileYear(), fileDownloadHref.getUrl());
//        }
//    }

    @Override
    public void updateWantedVehiclesHref(FileDownloadHref fileDownloadHref) {
        fileDownloadHrefRepository.updateFileDownloadHrefForWantedVehicles(fileDownloadHref.getDownloadingDate(),fileDownloadHref.getDateKey(), fileDownloadHref.getUrl());
    }

    @Override
    public void updateRegistrationsHref(FileDownloadHref fileDownloadHref) {
        if (fileDownloadHrefRepository.findByDateKey(fileDownloadHref.getDateKey())!=null) {
            fileDownloadHrefRepository.updateFileDownloadHrefForRegistrations(fileDownloadHref.getDownloadingDate(), fileDownloadHref.getDateKey(), fileDownloadHref.getUrl());
        }
    }
}
