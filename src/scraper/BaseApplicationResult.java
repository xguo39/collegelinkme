package scraper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

abstract class BaseApplicationResult implements ScrapingResult {
    protected Map<String, String> _fieldValues = new HashMap<>();
    protected Map<String, String> _mySQLStoreSchema = new HashMap<>();
    private final static String URL_KEY = "Url";
    
    // Add all fields here
    public BaseApplicationResult() {
    	_mySQLStoreSchema.put("Undergraduate_School", "text");
    	_mySQLStoreSchema.put("Undergraduate_Major", "text");
    	_mySQLStoreSchema.put("Graduate_School", "text");
    	_mySQLStoreSchema.put("Graduate_Major", "text");
    	_mySQLStoreSchema.put("Undergraduate_Ranking", "text");
    	_mySQLStoreSchema.put("Graduate_Ranking", "text");
    	_mySQLStoreSchema.put("Undergraduate_GPA", "text");
    	_mySQLStoreSchema.put("Graduate_GPA", "text");
    	_mySQLStoreSchema.put("GRE_Overall", "text");
    	_mySQLStoreSchema.put("GRE_Analytical_Writing", "text");
    	_mySQLStoreSchema.put("GRE_Verbal", "text");
    	_mySQLStoreSchema.put("GRE_Math", "text");
    	_mySQLStoreSchema.put("GMAT_Overall", "text");    	
    	_mySQLStoreSchema.put("GMAT_Analytical_Writing", "text");
    	_mySQLStoreSchema.put("GMAT_Verbal", "text");
    	_mySQLStoreSchema.put("GMAT_Math", "text");
    	_mySQLStoreSchema.put("TOEFL_Overall", "text");
    	_mySQLStoreSchema.put("TOEFL_Reading", "text");
    	_mySQLStoreSchema.put("TOEFL_Listenning", "text");
    	_mySQLStoreSchema.put("TOEFL_Oral", "text");
    	_mySQLStoreSchema.put("TOEFL_Writing", "text");
    	_mySQLStoreSchema.put("IELTS_Overall", "text");
    	_mySQLStoreSchema.put("IELTS_Reading", "text");
    	_mySQLStoreSchema.put("IELTS_Listenning", "text");
    	_mySQLStoreSchema.put("IELTS_Oral", "text");
    	_mySQLStoreSchema.put("IETLS_Writing", "text");
    	_mySQLStoreSchema.put("Applied_school", "text");
    	_mySQLStoreSchema.put("Applied_Major", "text");
    	_mySQLStoreSchema.put("Program_Start_Year", "text");
    	_mySQLStoreSchema.put("Application_Result", "text");
    	_mySQLStoreSchema.put("Application_Round", "text");
    	_mySQLStoreSchema.put("Application_Degree", "text");
    	_mySQLStoreSchema.put("Program_Start_Semester", "text");
    	_mySQLStoreSchema.put("Offer_Notification_Time", "text");
    	_mySQLStoreSchema.put("User_Id", "text");
    	_mySQLStoreSchema.put("WebSite_Name", "text");
    	_mySQLStoreSchema.put("Notes", "longtext");
    	generateFiledMap(_fieldValues);
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
	
	abstract void generateFiledMap(Map<String, String> fieldMap);
}