package scraper;

import java.io.File;
import java.io.IOException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;

// Download GSON from http://www.java2s.com/Code/Jar/g/Downloadgson22jar.htm
// Download Apache Commons IO from http://commons.apache.org/proper/commons-io/download_io.cgi
// Download HTMLCleaner from http://htmlcleaner.sourceforge.net/download.php
// Download Avro tool from http://mvnrepository.com/artifact/org.apache.avro/avro-tools/1.7.7
// Follow http://www.tutorialspoint.com/avro/serialization_by_generating_class.htm to generate the Java class from avro schema

public class Main {
	
	public static void main(String[] args) throws Exception {
        String profileString = FileUtils.readFileToString(new File("src/scraper/Wrappers/ProfileTest.txt"));
        TagNode tagNode = new HtmlCleaner().clean(profileString);
        Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = "//div[@class = 'views-field views-field-field-person-research-interests']//li/text()";
        NodeList nodeList = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        
        for (int i = 0; i < nodeList.getLength(); ++i) {
        	System.out.println(nodeList.item(i).getNodeValue());
        }

                
//        String wrapperString = FileUtils.readFileToString(new File("src/scraper/Wrappers/GatechCEEProfessorExtractor.json"));
//        Gson gson = new Gson();
//        Extractor gatechCEEExtractor = gson.fromJson(wrapperString, Extractor.class);
//        System.out.println(gatechCEEExtractor.toString());
        
        Extractor gatechCEEExtractor = AvroUtils.readSpecificRecord("scraper/Wrappers/GatechCEEProfessorExtractor.json", 
        		Extractor.class);
        
        
        
	}

}
