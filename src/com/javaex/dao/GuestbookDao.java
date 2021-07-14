package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	//db 연결
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
		
		
		//1명 정보 가져오기
		public GuestbookVo getNo(int guestbookNo) {
			GuestbookVo guestbookvo = null;
			
			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " select no, ";
				query += "         name, ";
				query += "         password, ";
				query += "         content, ";
				query += "         reg_date ";	
				query += " from guestbook ";
				query += " where no = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, guestbookNo);
				
				rs = pstmt.executeQuery();
			
				// 4.결과처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String reg_date = rs.getString("reg_date");
					
					guestbookvo = new GuestbookVo(no, name, password, content,reg_date);
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 4.결과처리

			close();
			return guestbookvo;

			
		}
		
		//한명 정보 삭제

		public int delete(GuestbookVo no) {
			int count = 0;
			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " delete from guestbook ";
				query += " where password = ? ";
				query += " and no = ? ";
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setString(1, no.getPassword());// ?(물음표) 중 1번째, 순서중요
				pstmt.setInt(2, no.getNo());

				count = pstmt.executeUpdate(); // 쿼리문 실행

				// 4.결과처리
				// System.out.println(count + "건 삭제되었습니다.");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();
			return count;
		}
		
		
		
		
		
		
		//등록하기
		 public int Insert(GuestbookVo guestbookVo) {
		      int count = 0;
		      getConnection();

		      try {

		         // 3. SQL문 준비 / 바인딩 / 실행
		         String query = ""; 
		         query += " insert into guestbook ";
		         query += " values(seq_no.nextval, ?, ?, ?, SYSDATE) ";
		         

		         pstmt = conn.prepareStatement(query);

		         pstmt.setString(1, guestbookVo.getName());
		         pstmt.setString(2, guestbookVo.getPassword());
		         pstmt.setString(3, guestbookVo.getContent());

		         count = pstmt.executeUpdate();

		         // 4.결과처리
		         // System.out.println("[" + count + "건 추가되었습니다.]");

		      } catch (SQLException e) {
		         System.out.println("error:" + e);
		      }
		      close();
		      return count;
		   }

		
		
		//게스트북 리스트 가져오기 메소드
	public List<GuestbookVo> getList(){
		//리스트 생성
		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();
		getConnection();
		
		try {
			//sql 문 준비
			String query = "";
			query += " select no, ";
			query += " name, ";
			query += " password, ";
			query += " content, ";
			query += " reg_date ";
			query += " from guestbook ";
			
		pstmt = conn.prepareStatement(query);
		
		rs= pstmt.executeQuery();
			
		//결과처리
		while(rs.next()) {
			int no = rs.getInt("no");
			String name =rs.getString("name");
			String password =rs.getString("password");
			String content =rs.getString("content");
			String reg_date =rs.getString("reg_date");
			
			GuestbookVo  guestbookvo = new GuestbookVo(no,name,password,content,reg_date);
			guestbookList.add(guestbookvo);
		}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		//자원정리
		close();
		return guestbookList;
		
		
	}
		
		
}
