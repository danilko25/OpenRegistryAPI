package com.danilko.carOpenData.dataDownloading.impl;

import com.danilko.carOpenData.entity.FileDownloadHref;
import com.danilko.carOpenData.dataDownloading.service.RegistryWebPageParserService;
import com.danilko.carOpenData.entity.InfoType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistryWebPageParserServiceImpl implements RegistryWebPageParserService {

    @Value("${application.open_data.registrations.url}")
    private String registrationsOpenDataPageUrl;

    public List<FileDownloadHref> getFilesDownloadHrefs(){
        try {
            Document doc = Jsoup.connect(registrationsOpenDataPageUrl).get();
            return getDownloadHrefsFromDocument(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<FileDownloadHref> getDownloadHrefsFromDocument(Document doc) {
        var list = new ArrayList<FileDownloadHref>();
        var lis = doc.getElementsByClass("resource-list").first().children();
        for (int i = 0; i<lis.size()-1; i++){
            var r = lis.get(i);
            var descriptionElementText = r.getElementsByClass("resource-list__item-container-description").get(0).text();
            if (isMatches(descriptionElementText)){

                var year = Integer.parseInt(descriptionElementText.replaceAll("[^0-9]", ""));
                var dateKey = Year.of(year).atDay(1).atStartOfDay();
                var downloadUrl = r.getElementsByClass("resource-url-analytics").get(0).attr("href");

                //files under 2021 without VIN
                if(year>=2021){
                    var temp = new FileDownloadHref();
                    temp.setInfoType(InfoType.REGISTRATIONS);
                    temp.setDateKey(dateKey);
                    temp.setUrl(downloadUrl);
                    list.add(temp);
                }
            }
        }
        return list.stream().sorted(Comparator.comparingInt(item->item.getDateKey().getYear())).toList();
    }

    private boolean isMatches(String name){
        Pattern pattern = Pattern.compile("Дані щодо транспортних засобів за \\d{4} рік");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
