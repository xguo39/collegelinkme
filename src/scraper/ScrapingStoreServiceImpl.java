package scraper;

import java.io.IOException;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.sun.accessibility.internal.resources.accessibility;

public class ScrapingStoreServiceImpl implements ScrapingStoreService {
	private ScraperDatabaseExecutor _scraperDatabaseExecutor;
	
	public ScrapingStoreServiceImpl(ScraperDatabaseExecutor scraperDatabaseExecutor) {
		_scraperDatabaseExecutor = scraperDatabaseExecutor;
	}
	
	public void createScrapingTable(String tableName, ScrapingResult scrapingResult) throws SQLException {
		StringBuilder stringBuilder = new StringBuilder(String.format("CREATE TABLE IF NOT EXISTS `%s` (", tableName));
		stringBuilder.append("`RecordID` INT(11) NOT NULL AUTO_INCREMENT,");
		stringBuilder.append("`URL` text");
		scrapingResult.getMySQLStoreSchema().entrySet().stream().forEach(mapEntry -> {
			stringBuilder.append(String.format(",`%s` %s", mapEntry.getKey(), mapEntry.getValue()));
		});
		stringBuilder.append(", PRIMARY KEY (`RecordID`)");
		stringBuilder.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;");
		
	    _scraperDatabaseExecutor.runSql2(stringBuilder.toString());
	}
	
	public void storeScrapingResult(String tableName, ScrapingResult scrapingResult) throws SQLException {
		StringBuilder columnStringBuilder = new StringBuilder("`URL`");
		StringBuilder valuesStringBuilder = new StringBuilder(String.format("'%s'", scrapingResult.getUrl().orElse("Empty")));
		if (scrapingResult.getUrl().get().equals("http://bbs.gter.net/forum.php?mod=viewthread&tid=1955750&extra=page=1&filter=typeid&typeid=158&typeid=158")) {
			int a = 1;
		}
		
		scrapingResult.getMySQLStoreSchema().entrySet().stream().forEach(mapEntry -> {
			String fieldName = mapEntry.getKey();
			if (scrapingResult.getFieldByName(fieldName).isPresent()) {
				columnStringBuilder.append(String.format(", `%s`", fieldName));
				if (mapEntry.getValue().toLowerCase().equals("text") || mapEntry.getValue().toLowerCase().equals("longtext") ||
						mapEntry.getValue().toLowerCase().equals("mediumtext")) {
					valuesStringBuilder.append(String.format(", '%s'", scrapingResult.getFieldByName(fieldName).get()));
				} else {
					valuesStringBuilder.append(String.format(", %s", scrapingResult.getFieldByName(fieldName).get()));
				}
			}
		});
			
		String sql = String.format("INSERT INTO  `%s`.`%s` (%s) VALUES (%s);", 
				ScraperDatabaseConfig.DATABASE_NAME,
			    tableName,
			    columnStringBuilder.toString(),
			    valuesStringBuilder.toString());
		_scraperDatabaseExecutor.runSql2(sql);	
	}
	
	public void truncateScrapingTable(String tableName) throws SQLException {
		_scraperDatabaseExecutor.runSql2(String.format("TRUNCATE %s", tableName));
	}
	
	public void dropScrapingTable(String tableName) throws SQLException {
		_scraperDatabaseExecutor.runSql2(String.format("DROP TABLE %s", tableName));
	}
	
	public Optional<String> getHTML(String tableName, int indexInWebPageTable) throws SQLException, IOException {
		  String sql = String.format("select * from %s where RecordID = %s", tableName, indexInWebPageTable);
		  ResultSet rs = _scraperDatabaseExecutor.crawler_runSql(sql);

		  if(rs.next()) {
			  StringBuilder sb = new StringBuilder();
			  Reader in = rs.getCharacterStream("HTML");
			  int buf = -1;
			  while((buf = in.read()) > -1) {
			        sb.append((char)buf);
			  }
			  in.close();
			  return Optional.of(sb.toString());
		  } else {
			  return Optional.empty();
		  }
	}
	
	public Optional<String> getURL(String tableName, int indexInWebPageTable) throws SQLException, IOException {
		  String sql = String.format("select * from %s where RecordID = %s", tableName, 
				  indexInWebPageTable);
		  ResultSet rs = _scraperDatabaseExecutor.crawler_runSql(sql);
		  if(rs.next()) {
			  StringBuilder sb = new StringBuilder();
			  Reader in = rs.getCharacterStream("URL");
			  int buf = -1;
			  while((buf = in.read()) > -1) {
			        sb.append((char)buf);
			  }
			  in.close();
			  return Optional.of(sb.toString());
		  } else {
			  return Optional.empty();
		  }
	}
    
}
