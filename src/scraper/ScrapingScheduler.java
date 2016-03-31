package scraper;

public interface ScrapingScheduler {
	void batchScraping(int startIndex, String tableName, String htmlTableName, Scraper scraper, ScrapingResult scrapingResult);
	
	void batchScraping(int startIndex, int toIndex, String tableName, String htmlTableName, Scraper scraper, ScrapingResult scrapingResult);
	
	void singleScraping(int index, String tableName, String htmlTableName, Scraper scraper, ScrapingResult scrapingResult);
}
