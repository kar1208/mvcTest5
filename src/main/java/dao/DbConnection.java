package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/springproject";
	private String user = "atom";
	private String password = "1234";
	

	//싱글톤으로 connection(conn)개체를 만들어서 사용하기
	private static Connection conn; // 메소드영역에 올리는 것 static,자주쓰는것,정적 멤버,생성 안해도 쓰고싶을 때,누구나 쓸 수 있게끔
	@SuppressWarnings("unused")
	private static DbConnection instance = new DbConnection();
	
	private DbConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 검색 오류~" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("데이터베이스 연동 실패~" + e.getMessage());
		}
	}
	
	public static Connection getConn() {
		return conn;
	}
//	public static DbConnection getInstance() {
//		return instance;
//	}
	
	
}
