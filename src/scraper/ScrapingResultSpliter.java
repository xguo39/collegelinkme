package scraper;

import java.util.List;

public interface ScrapingResultSpliter {
	List<ScrapingResult> split(ScrapingResult scrapingResult);
}
