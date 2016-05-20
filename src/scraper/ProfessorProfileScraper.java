package scraper;

import java.util.HashMap;
import java.util.Map;

public class ProfessorProfileScraper implements Scraper {
	private Map<String, String> _extractors = new HashMap<String, String>();
	
	// Add all extractor here
	public ProfessorProfileScraper(){
		_extractors.put("Research_Interest", 
				"//div[@class = 'views-field views-field-field-person-research-interests']//li/text()");
		_extractors.put("Name", 
				"//div[@class = 'views-field views-field-title']//h3/text()");
	};
	
	public Map<String, String> getExtractors() {
		return _extractors;
	}
}
