package scraper;

// Download HTMLCleaner from http://htmlcleaner.sourceforge.net/download.php

public class Main {
	public static ScraperDatabaseExecutor scraperDatabaseExecutor = new ScraperDatabaseExecutor();
	public static ScrapingStoreService scrapingStoreService = new ScrapingStoreServiceImpl(scraperDatabaseExecutor);
	public static ScrapingService scrapingService = new ScrapingServiceImpl();
	public static ScrapingScheduler scrapingScheduler = new ScrapingSchedulerImpl(scrapingStoreService, scrapingService);
	
	public static void main(String[] args) throws Exception {
		Scraper scraper = new ProfessorProfileScraper();
		ScrapingResult scrapingResult = new ProfessorProfileResult();
		scrapingStoreService.dropScrapingTable("Gatech_CEE_Faculty_Profile");
		scrapingScheduler.batchScraping(1, "Gatech_CEE_Faculty_Profile", "Gatech_CEE_FacultyProfile_Web_Pages", scraper, scrapingResult);
	}
}
