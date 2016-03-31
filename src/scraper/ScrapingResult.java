package scraper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public interface ScrapingResult {
	Optional<String> getUrl();
	
	void setUrl(String url);
	
	void setFieldByName(String fieldName, String value) throws NoSuchFieldException;

	Optional<String> getFieldByName(String fieldName);
	
	boolean hasField(String fieldName);
	
    Map<String, String> getMySQLStoreSchema();
}
