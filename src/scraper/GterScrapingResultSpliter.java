package scraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class GterScrapingResultSpliter implements ScrapingResultSpliter {
	private String START_SEMESTER_KEY = "Program_Start_Semester";
	private String APPLICATION_DEGREE_KEY = "Application_Degree";
	private String APPLIED_MAJOR_KEY = "Applied_Major";
	private String PROGRAM_START_YEAR_KEY = "Program_Start_Year";
	private String APPLIED_SCHOOL_KEY = "Applied_school";
	private String APPLICATION_RESULT_KEY = "Application_Result";
	private String OFFER_NOTIFICATION_TIME_KEY = "Offer_Notification_Time";
	private List<String> keys = Arrays.asList(START_SEMESTER_KEY, APPLICATION_DEGREE_KEY, APPLIED_MAJOR_KEY, PROGRAM_START_YEAR_KEY, 
			APPLIED_SCHOOL_KEY, APPLICATION_RESULT_KEY, OFFER_NOTIFICATION_TIME_KEY);
	
	public GterScrapingResultSpliter() {	
	}
	
	public List<ScrapingResult> split(ScrapingResult scrapingResult) {
		List<ScrapingResult> results = new ArrayList<>();
		Map<String, List<String>> variedValues = new HashMap<String, List<String>>();	
		Map<String, String> fieldMap = scrapingResult.getFieldMap();
	
		String[] appliedSchools = fieldMap.get(APPLIED_SCHOOL_KEY).trim().split("\\s+");
		int numOfResult = appliedSchools.length;
		
		keys.forEach(key -> {
			String[] values = fieldMap.get(key).trim().split("\\s+");
			if (values.length != numOfResult) System.out.println("The number of values " + key + " do not match total number"
					+ " of result " + numOfResult);
			List<String> valuesList = Arrays.asList(values);
			
			variedValues.put(key, valuesList);
		});
		
		for (int i = 0; i < numOfResult; ++i) {
			int index = i;
			ScrapingResult newSrapingResult = new GterResult((GterResult)scrapingResult);
			variedValues.entrySet().forEach(entry -> {
				try {
					newSrapingResult.setFieldByName(entry.getKey(), entry.getValue().get(index));
				} catch (NoSuchFieldException e) {
					System.out.println("Do not have the field: " + entry.getKey());
				}
			});
			
			newSrapingResult.getFieldMap().entrySet().forEach(entry -> {
				try {
					newSrapingResult.setFieldByName(entry.getKey(), entry.getValue().replace("%20", " "));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			results.add(newSrapingResult);
		}
		
		return results;
	}
}
