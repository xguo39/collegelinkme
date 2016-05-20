package scraper;

import java.util.Map;

public class GterResult extends BaseApplicationResult {
    private final static String URL_KEY = "Url";
    
    // Add all fields here
    public GterResult() {}
    
    public void generateFiledMap(Map<String, String> fieldMap) {
    	fieldMap.put(URL_KEY, null);
    	fieldMap.put("Applied_school", null);
    	fieldMap.put("Applied_Major", null);
    	fieldMap.put("Application_Degree", null);
    	fieldMap.put("Application_Result", null);
    	fieldMap.put("Program_Start_Year", null);
    	fieldMap.put("Program_Start_Semester", null);
    	fieldMap.put("Offer_Notification_Time", null);
    	fieldMap.put("TOEFL_Overall", null);
    	fieldMap.put("GRE_Overall", null);
    	fieldMap.put("Undergraduate_School", null);
    	fieldMap.put("Undergraduate_Major", null);
    	fieldMap.put("Undergraduate_GPA", null);
    	fieldMap.put("Notes", null);
    	fieldMap.put("WebSite_Name", null);
    }
}