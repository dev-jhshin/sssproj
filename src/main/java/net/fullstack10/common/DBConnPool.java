
package net.fullstack10.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnPool {
	public Connection conn;
	public Statement stmt;
	public PreparedStatement pstm;
	public ResultSet rs;

	//기본 생성자
	public DBConnPool() {
		try {
			// 커넥션 풀(DataSource) 얻기
			Context initCtx = new InitialContext();
			Context ctx = (Context)initCtx.lookup("java:comp/env");
			DataSource source = (DataSource)ctx.lookup("jdbc_maria");
		
			// 커넥션 풀을 통해 연결 얻기
			conn = source.getConnection();

			System.out.println("================================");
			String now = 
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			System.out.print("[✅][" + now + "] DB 커넥션 풀 연결 성공(기본생성자)");
			System.out.println("conn String : "+ conn);
			System.out.println("================================");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("================================");
			System.out.println("DB 커넥션 풀 연결 에러(기본생성자)");
			System.out.println(e.getMessage());
			System.out.println("================================");
		}
	}

	//두 번째 생성자
	public DBConnPool(String ct, String ds) {
		try {
			// 커넥션 풀(DataSource) 얻기
			Context initCtx = new InitialContext();
			Context ctx = (Context)initCtx.lookup(ct);
			DataSource source = (DataSource)ctx.lookup(ds);
		
			// 커넥션 풀을 통해 연결 얻기
			conn = source.getConnection();

			System.out.println("================================");
			String now = 
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			System.out.println("[✅][" + now + "] DB 커넥션 풀 연결 성공(두 번째 생성자)");
			System.out.println("conn String : "+ conn);
			System.out.println("================================");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("================================");
			System.out.println("DB 커넥션 풀 연결 에러(두 번째 생성자)");
			System.out.println(e.getMessage());
			System.out.println("================================");
		}
	}
	
	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// 연결 해제(리소스 자원 반납)
	public void close() {
		try {
			if (rs != null) rs.close();
	        if (stmt != null) stmt.close();
	        if (pstm != null) pstm.close();
			if ( conn != null ) conn.close();
			
			System.out.println("================================");
			String now = 
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			System.out.println("[🔌][" + now + "] DB 커넥션 풀 자원 반납");
			System.out.println("================================");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("================================");
			System.out.println(e.getMessage());
			System.out.println("================================");
		}
	}
	
}
