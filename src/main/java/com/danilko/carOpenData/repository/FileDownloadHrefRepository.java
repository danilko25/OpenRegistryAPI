package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.FileDownloadHref;
import com.danilko.carOpenData.entity.InfoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FileDownloadHrefRepository extends JpaRepository<FileDownloadHref, Integer> {

    FileDownloadHref findByDateKey(LocalDateTime dateKey);

    @Query("SELECT f FROM FileDownloadHref f WHERE f.infoType = 'REGISTRATIONS'")
    List<FileDownloadHref> findAllForRegistrations();

    Optional<FileDownloadHref> findByInfoType(InfoType infoType);

    @Modifying
    @Query("UPDATE FileDownloadHref f SET f.url = :url, f.downloadingDate = :downloadingDate WHERE f.dateKey = :dateKey AND f.infoType ='REGISTRATIONS'")
    void updateFileDownloadHrefForRegistrations(LocalDateTime downloadingDate, LocalDateTime dateKey, String url);

    @Modifying
    @Query("UPDATE FileDownloadHref f SET f.url = :url, f.dateKey = :dateKey, f.downloadingDate = :downloadingDate WHERE f.infoType ='WANTED'")
    void updateFileDownloadHrefForWantedVehicles(LocalDateTime downloadingDate, LocalDateTime dateKey, String url);

}
