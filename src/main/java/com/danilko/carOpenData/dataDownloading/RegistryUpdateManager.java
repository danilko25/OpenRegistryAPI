package com.danilko.carOpenData.dataDownloading;

import com.danilko.carOpenData.dataDownloading.service.RegistryDataUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistryUpdateManager {

    private final List<RegistryDataUpdater> updaters;

    @Scheduled(cron = "0 0 4 * * ?")
    public void updateAllRegistries() throws IOException {
        for (RegistryDataUpdater updater : updaters) {
            updater.checkAndUpdate();
        }
    }
}