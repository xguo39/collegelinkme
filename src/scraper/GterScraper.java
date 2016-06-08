package scraper;

import java.util.HashMap;
import java.util.Map;

public class GterScraper implements Scraper {
	private Map<String, String> _extractors = new HashMap<String, String>();
	
	// Add all extractor here
	public GterScraper(){
		_extractors.put("Applied_school", 
				"//div[@class='typeoption']//table[contains(@summary, 'offer')]//th[contains(.,'申请学校')]/following-sibling::td//text()");
		_extractors.put("Application_Degree", 
				"//div[@class='typeoption']//table[contains(@summary, 'offer')]//th[contains(.,'学位')]/following-sibling::td//text()");
		_extractors.put("Applied_Major", 
				"//div[@class='typeoption']//table[contains(@summary, 'offer')]//th[contains(.,'专业')]/following-sibling::td//text()");
		_extractors.put("Application_Result", 
				"//div[@class='typeoption']//table[contains(@summary, 'offer')]//th[contains(.,'申请结果')]/following-sibling::td//text()");
		_extractors.put("Program_Start_Year", 
				"//div[@class='typeoption']//table[contains(@summary, 'offer')]//th[contains(.,'入学年份')]/following-sibling::td//text()");
		_extractors.put("Program_Start_Semester", 
				"//div[@class='typeoption']//table[contains(@summary, 'offer')]//th[contains(.,'入学学期')]/following-sibling::td//text()");
		_extractors.put("Offer_Notification_Time", 
				"//div[@class='typeoption']//table[contains(@summary, 'offer')]//th[contains(.,'通知时间')]/following-sibling::td//text()");
		_extractors.put("TOEFL_Overall", 
				"//div[@class='typeoption']//th[contains(.,'TOEFL')]/following-sibling::td/text()");	
		_extractors.put("GRE_Overall", 
				"//div[@class='typeoption']//th[contains(.,'GRE')]/following-sibling::td/text()");
		_extractors.put("Undergraduate_School", 
				"//div[@class='typeoption']//th[contains(.,'本科学校档次')]/following-sibling::td/text()");
		_extractors.put("Undergraduate_Major", 
				"//div[@class='typeoption']//th[contains(.,'本科专业')]/following-sibling::td/text()");
		_extractors.put("Undergraduate_GPA", 
				"//div[@class='typeoption']//th[contains(.,'本科成绩和算法')]/following-sibling::td/text()");
		_extractors.put("Notes", 
				"//div[@class='typeoption']//th[contains(.,'其他说明')]/following-sibling::td/text()");
		_extractors.put("WebSite_Name", "Gter");
	};
	
	public Map<String, String> getExtractors() {
		return _extractors;
	}
}

