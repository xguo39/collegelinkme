package scraper;

// Download HTMLCleaner from http://htmlcleaner.sourceforge.net/download.php

public class Main {
	public static ScraperDatabaseExecutor scraperDatabaseExecutor = new ScraperDatabaseExecutor();
	public static ScrapingStoreService scrapingStoreService = new ScrapingStoreServiceImpl(scraperDatabaseExecutor);
	public static ScrapingService scrapingService = new ScrapingServiceImpl();
	public static ScrapingScheduler scrapingScheduler = new ScrapingSchedulerImpl(scrapingStoreService, scrapingService);
	
	public static void main(String[] args) throws Exception {
		Scraper scraper = new GterScraper();
		ScrapingResult scrapingResult = new GterResult();
		scrapingStoreService.dropScrapingTable("Gter_Application_Result");
		scrapingScheduler.batchScraping(1, 2, "Gter_Application_Result", "Gter_new_Web_Pages", scraper, scrapingResult);
	}
}
