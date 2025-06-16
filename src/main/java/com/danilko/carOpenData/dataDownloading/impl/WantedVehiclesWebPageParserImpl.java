package com.danilko.carOpenData.dataDownloading.impl;

import com.danilko.carOpenData.dataDownloading.service.WantedVehiclesWebPageParser;
import com.danilko.carOpenData.entity.FileDownloadHref;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WantedVehiclesWebPageParserImpl implements WantedVehiclesWebPageParser {

    @Value("${application.open_data.wanted_vehicles.url}")
    private String wantedVehiclesOpenDataPageUrl;

    @Override
    public FileDownloadHref parseFileDownloadHref() throws IOException {
        var result = new FileDownloadHref();
        Document doc = Jsoup.connect(wantedVehiclesOpenDataPageUrl).get();
        result.setUrl(getUrl(doc));
        result.setDateKey(getDate(doc));
        return result;
    }

    private String getUrl(Document doc) {
        Elements resources = doc.select("li.resource-list__item");
        for (Element resource : resources) {
            Element description = resource.selectFirst("p.resource-list__item-container-description");
            if (description != null && description.text().contains("масив даних")) {
                Element downloadLink = resource.selectFirst("a[href$=.json]");
                if (downloadLink != null) {
                    return downloadLink.attr("href");
                }
            }
        }
        throw new RuntimeException("Can't find url for downloading wantedRegistry");
    }

    private LocalDateTime getDate(Document doc) {
        Element updatedRow = doc.selectFirst("tr:has(th:contains(Востаннє оновлено)) span[data-datetime]");
        if (updatedRow != null) {
            String my = updatedRow.attr("data-datetime");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

            OffsetDateTime offsetDateTime = OffsetDateTime.parse(my, formatter);
            return offsetDateTime.toLocalDateTime();
        } else throw new RuntimeException("Something get changed in data.gov.ua, can't get date");
    }
}

