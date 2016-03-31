package scraper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface ScrapingStoreService {
	void createScrapingTable(String tableName, ScrapingResult scrapingResult) throws SQLException;
	void storeScrapingResult(String tableName, ScrapingResult scrapingResult) throws SQLException;
	void truncateScrapingTable(String tableName) throws SQLException;
	void dropScrapingTable(String tableName) throws SQLException;
	
	Optional<String> getHTML(String tableName, int indexInWebPageTable) throws SQLException, IOException;
	Optional<String> getURL(String tableName, int indexInWebPageTable) throws SQLException, IOException;
}
