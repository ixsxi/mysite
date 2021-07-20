package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestbookVo;

public class BoardDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	// 자원정리
		private void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
	
		public List<BoardVo> getList(){
			//리스트 생성
			List<BoardVo> boardList = new ArrayList<BoardVo>();
			getConnection();
			
			try {
				//sql 문 준비
				String query = "";
				query += " select b.no, ";
				query += " b.title, ";
				query += " b.content, ";
				query += " b.hit, ";
				query += " b.reg_date, ";
				query += " b.user_no, ";
				query += " u.name ";
				query += " from board b ,users u ";
				query += " where b.user_no = u.no ";
				
				
			pstmt = conn.prepareStatement(query);
			
			rs= pstmt.executeQuery();
				
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String title =rs.getString("title");
				String content =rs.getString("content");
				int hit =rs.getInt("hit");
				String reg_date =rs.getString("reg_date");
				int user_no =rs.getInt("user_no");
				String name =rs.getString("name");
			
				BoardVo  boardVo = new BoardVo(no,title,content,hit,reg_date,user_no,name);
				boardList.add(boardVo);
			}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			//자원정리
			close();
			return boardList;
			
			
		}
	
		public BoardVo getread(int num) {
			System.out.println("메소드 실행");
			BoardVo boardVo=null;
			
			getConnection();
			
			
			
			try {
				//sql 문 준비
				String query = "";
				query += " select b.no, ";
				query += " b.title, ";
				query += " b.content, ";
				query += " b.hit, ";
				query += " b.reg_date, ";
				query += " b.user_no, ";
				query += " u.name ";
				query += " from board b ,users u ";
				query += " where b.user_no = u.no ";
				query += " and b.no = ? ";
				
				
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, num);
				
				rs = pstmt.executeQuery();
				
				
				//결과처리
				
				while(rs.next()) {
					int no = rs.getInt("no");
					String title = rs.getString("title");
					String content = rs.getString("content");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					String name = rs.getString("name");
				
					boardVo = new BoardVo(no,title,content,hit,reg_date,name);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			close();
			
			
			
			return boardVo;
			
			
		}
		
	
}
