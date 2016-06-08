package scraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private String TOEFL_OVERALL_KEY = "TOEFL_Overall";
	private String TOEFL_READING_KEY = "TOEFL_Reading";
	private String TOEFL_LISTENNING_KEY = "TOEFL_Listenning";
	private String TOEFL_ORAL_KEY = "TOEFL_Oral";
	private String TOEFL_WRITING_KEY = "TOEFL_Writing";
	private List<String> toeflKeys = Arrays.asList(TOEFL_OVERALL_KEY, TOEFL_READING_KEY, TOEFL_LISTENNING_KEY, TOEFL_ORAL_KEY, TOEFL_WRITING_KEY);
	
	private String GRE_OVERALL_KEY = "GRE_Overall";
	private String GRE_AW_KEY = "GRE_Analytical_Writing";
	private String GRE_VERBAL = "GRE_Verbal";
	private String GRE_MATH = "GRE_Math";
    private List<String> GREKeys = Arrays.asList(GRE_OVERALL_KEY, GRE_AW_KEY, GRE_VERBAL, GRE_MATH);
	
	public GterScrapingResultSpliter() {	
	}
	
	public List<ScrapingResult> split(ScrapingResult scrapingResult) {
		List<ScrapingResult> results = new ArrayList<>();
		Map<String, List<String>> variedValues = new HashMap<String, List<String>>();	
		Map<String, String> fieldMap = scrapingResult.getFieldMap();
		
		splitToefl(scrapingResult);
		splitGRE(scrapingResult);
		
		String[] appliedSchools = fieldMap.get(APPLIED_SCHOOL_KEY).trim().split("\\s+");
		int numOfResult = appliedSchools.length;
		String url = fieldMap.get("Url");
		
		keys.forEach(key -> {
			String[] values = fieldMap.get(key).trim().split("\\s+");
			if (values.length != numOfResult) System.out.println("The number " + values.length + "of values " + key + " do not match total number"
					+ " of result " + numOfResult + " url: " + url);
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
					if (entry.getValue() != null) {
						newSrapingResult.setFieldByName(entry.getKey(), entry.getValue().replace("%20", " "));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			results.add(newSrapingResult);
		}
		
		return results;
	}
		
	public void splitToefl(ScrapingResult scrapingResult) {
		scrapingResult.getFieldByName(TOEFL_OVERALL_KEY).ifPresent(score -> {
			String regex = "\\b(:)(\\s)(\\d{2,3})\\b";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(score.replace("%20", " "));
			int index = 0;
			while(m.find()) {
				String s = m.group(3);
				try {
					scrapingResult.setFieldByName(toeflKeys.get(index), s);
					index++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void splitGRE(ScrapingResult scrapingResult) {
		scrapingResult.getFieldByName(GRE_OVERALL_KEY).ifPresent(score -> {
			String regex = "\\b(:)(\\s)(\\d{1,3})\\b";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(score.replace("%20", " "));
			int index = 0;
			while(m.find()) {
				String s = m.group(3);
				try {
					scrapingResult.setFieldByName(GREKeys.get(index), s);
					index++;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}
