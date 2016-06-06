package scraper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProfessorProfileResult implements ScrapingResult {
    private Map<String, String> _fieldValues = new HashMap<>();
    private Map<String, String> _mySQLStoreSchema = new HashMap<>();
    private final static String URL_KEY = "Url";
    
    // Add all fields here
    public ProfessorProfileResult() {
    	_fieldValues.put(URL_KEY, null);
    	_fieldValues.put("Name", null);
    	_fieldValues.put("Research_Interest", null);
    	_mySQLStoreSchema.put("Name", "text");
    	_mySQLStoreSchema.put("Research_Interest", "longtext");
    }
    
    public Optional<String> getUrl() {
    	if (_fieldValues.get(URL_KEY) == null) {
    		return Optional.empty();
    	} else {
    	    return Optional.of(_fieldValues.get(URL_KEY));
    	}
    }
    
    public void setUrl(String url) {
    	_fieldValues.put(URL_KEY, url);
    }
    
	public void setFieldByName(String fieldName, String value) throws NoSuchFieldException {
		if (!_fieldValues.containsKey(fieldName)) {
			throw new NoSuchFieldException("Do not have field: " + fieldName);
		} else {
			_fieldValues.put(fieldName, value);
		}
	}

	public Optional<String> getFieldByName(String fieldName) {
		if (!_fieldValues.containsKey(fieldName)) {
			return Optional.empty();
		} else {
			return Optional.of(_fieldValues.get(fieldName));
		}		
	}
	
	public boolean hasField(String fieldName) {
		return _fieldValues.containsKey(fieldName);
	}
    
	public Map<String, String> getMySQLStoreSchema() {
		return _mySQLStoreSchema;
	}
	
	public Map<String, String> getFieldMap() {
		return _fieldValues;
	}
}
