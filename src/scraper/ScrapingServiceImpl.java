package scraper;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ScrapingServiceImpl implements ScrapingService {
	private XPath xpath = XPathFactory.newInstance().newXPath();
	
	public ScrapingServiceImpl() {
	}
	
	public ScrapingResult singleScrape(ScrapingResult scrapingResultInput, Scraper scraper, Document doc, String url) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Class classDefinition = Class.forName(scrapingResultInput.getClass().getName());
		ScrapingResult scrapingResult = (ScrapingResult) classDefinition.newInstance();
		scrapingResult.setUrl(url);
		
		Iterator it = scraper.getExtractors().entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, String> mapEntry = (Entry<String, String>) it.next();
			try {
				String fieldName = mapEntry.getKey();
				if (scrapingResult.hasField(fieldName)) {
					StringBuilder stringBuilder = new StringBuilder();
					NodeList nodeList = (NodeList) xpath.compile(mapEntry.getValue()).evaluate(doc, XPathConstants.NODESET);
					for (int i = 0; i < nodeList.getLength(); ++i) {
						stringBuilder.append(nodeList.item(i).getNodeValue().trim().replace("`", "'").replace("'", "''"));
						stringBuilder.append(" ");
					}
					scrapingResult.setFieldByName(fieldName, stringBuilder.toString());
				} else {
					System.out.println("ScrapingResult does not contain field: " + fieldName);
				}
			} catch (XPathExpressionException | NoSuchFieldException e) {
				System.out.println("Scraping throws exception: " + e);
			}     
		}
		return scrapingResult;
	}
}
