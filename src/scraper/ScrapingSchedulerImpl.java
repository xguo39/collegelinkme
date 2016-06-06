package scraper;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

public class ScrapingSchedulerImpl implements ScrapingScheduler {
	private ScrapingStoreService _scrapingStoreService;
	private ScrapingService _scrapingService;
	private ScrapingResultSpliter _ScrapingResultSpliter;
	
	public ScrapingSchedulerImpl(ScrapingStoreService scrapingStoreService, ScrapingService scrapingService, ScrapingResultSpliter scrapingResultSpliter) {
		_scrapingStoreService = scrapingStoreService;
		_scrapingService = scrapingService;
		_ScrapingResultSpliter = scrapingResultSpliter;
	}

	public void batchScraping(int startIndex, String tableName, String htmlTableName, Scraper scraper, ScrapingResult scrapingResult) {
		batchScraping(startIndex, Integer.MAX_VALUE, tableName, htmlTableName, scraper, scrapingResult);
	}
	
	public void batchScraping(int startIndex, int toIndex, String tableName, String htmlTableName, Scraper scraper, ScrapingResult scrapingResultDummy) {
		if (startIndex < 0 || toIndex < 0 || startIndex > toIndex) return;		
		int currentIndex = startIndex;
		try {
			_scrapingStoreService.createScrapingTable(tableName, scrapingResultDummy);
			while(currentIndex <= toIndex && _scrapingStoreService.getHTML(htmlTableName, currentIndex).isPresent()) {
				System.out.println("Scraping index: " + currentIndex);
			    TagNode tagNode = new HtmlCleaner().clean(_scrapingStoreService.getHTML(htmlTableName, currentIndex).get());
				Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
				String url = _scrapingStoreService.getURL(htmlTableName, currentIndex).orElse("Empty");
				ScrapingResult scrapingResult = _scrapingService.singleScrape(scrapingResultDummy, scraper, doc, url);
				List<ScrapingResult> scrapingResults = _ScrapingResultSpliter.split(scrapingResult);
				for (ScrapingResult scrapingElement : scrapingResults) {
					_scrapingStoreService.storeScrapingResult(tableName, scrapingElement);
				}
				currentIndex++;
			}
		} catch (Exception e) {
			System.out.println(String.format("Throw exception for web page index: %d %s", currentIndex, e.toString()));
		}
	}
	
	public void singleScraping(int index, String tableName, String htmlTableName, Scraper scraper, ScrapingResult scrapingResult) {
		batchScraping(index, index, tableName, htmlTableName, scraper, scrapingResult);
	}
}
