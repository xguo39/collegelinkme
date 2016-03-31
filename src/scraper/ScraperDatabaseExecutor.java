package scraper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class ScraperDatabaseExecutor {
 
	private Connection conn = null;
	private Connection crawler_conn = null;
 
	public ScraperDatabaseExecutor() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = String.format("jdbc:mysql://localhost:3306/%s", ScraperDatabaseConfig.DATABASE_NAME);
			conn = DriverManager.getConnection(url, ScraperDatabaseConfig.DATABASE_USER, ScraperDatabaseConfig.DATABASE_PASSWORD);
			System.out.println("conn built");
			
			String crawler_url = String.format("jdbc:mysql://localhost:3306/%s", ScraperDatabaseConfig.CRAWLER_DATABASE_NAME);
			crawler_conn = DriverManager.getConnection(crawler_url, ScraperDatabaseConfig.DATABASE_USER, ScraperDatabaseConfig.DATABASE_PASSWORD);	
			System.out.println("crawler_conn built");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
 
	public ResultSet runSql(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.executeQuery(sql);
	}
 
	public boolean runSql2(String sql) throws SQLException {
		Statement sta = conn.createStatement();
		return sta.execute(sql);
	}
	
	public ResultSet crawler_runSql(String sql) throws SQLException {
		Statement sta = crawler_conn.createStatement();
		return sta.executeQuery(sql);
	}
 
	@Override
	protected void finalize() throws Throwable {
		if (conn != null || !conn.isClosed()) {
			conn.close();
		}
	}
}
