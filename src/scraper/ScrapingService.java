package scraper;

import java.util.List;

import org.w3c.dom.Document;

public interface ScrapingService {

    ScrapingResult singleScrape(ScrapingResult scrapingResult, Scraper Scraper, Document doc, String url) 
    		throws InstantiationException, IllegalAccessException, ClassNotFoundException;
}
