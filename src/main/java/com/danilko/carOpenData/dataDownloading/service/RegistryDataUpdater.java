package com.danilko.carOpenData.dataDownloading.service;

import java.io.IOException;

public interface RegistryDataUpdater {
    void checkAndUpdate() throws IOException;
}
