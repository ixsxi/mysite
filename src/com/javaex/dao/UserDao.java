package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	
	//connection
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	
	//close
	public void close() {
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
	//생성자
	
	
	//메소드 gs .se
	
	//메소드 일반
	
	//
	public int userInsert(UserVo userVo) {
		
		int count = -1;
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		    // 3. SQL문 준비 / 바인딩 / 실행
		    String query = "";
		    query +=" insert into users ";
		    query +=" values(SEQ_USER_NO.nextval,?,?,?,?) ";
			
			pstmt= conn.prepareStatement(query);
			pstmt.setString(1,userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
			count = pstmt.executeUpdate();
			
		    // 4.결과처리
			 
			System.out.println(count + " 건이 저장되었습니다.");
			
			
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} finally {
		   
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

		
		return count;
	}
		//유저 1명 정보 가져오기(로그인한사람)

		public UserVo getUser(String id, String pass) {
			
			UserVo userVo = null;
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
			// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 얻어오기
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
				conn = DriverManager.getConnection(url, "webdb", "webdb");
				
			// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				
				query += " select no, id, password, name, gender ";
				query += " from users ";
				query += " where id = ? ";
				query += " and password = ? ";
			
				//System.out.println(query);
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, pass);
				
				rs = pstmt.executeQuery();
				
			// 4.결과처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String id2 =rs.getString("id");
					String password =rs.getString("password");
					String name =rs.getString("name");
					String gender =rs.getString("gender");
					
					//생성자가 없는경우 세터를 이용할수있따.
					userVo = new UserVo(no, id2, password, name, gender);
					
				}
				
			} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
			System.out.println("error:" + e);
			} finally {
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
			}return userVo;
		}
		
		
		/*
		//회원정보 수정할때 회원정보 불러옴
		public UserVo getUserInfo(int no) {
			
			UserVo userVo = null;
			
			this.getConnection();
			
			try {
			    // 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " select  no, ";
				query += " 		   id, ";
				query += " 		   password, ";
				query += " 		   name, ";
				query += " 		   gender ";
				query += " from users ";
				query += " where no = ? ";
				
				//System.out.println(query);//쿼리 확인용
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();
				
			    // 4.결과처리
				while(rs.next()) {
					no = rs.getInt("no");
					String id = rs.getString("id");
					String password = rs.getString("password");
					String name = rs.getString("name");
					String gender = rs.getString("gender");
					
					//생성자가 없는 경우 setter 이용--> 좋은방법 아님. 차라리 Vo에서 생성자 만들어 두는게 편함
					 userVo = new UserVo(no, id, password, name, gender);
					
				}
				
			}  catch (SQLException e) {
			    System.out.println("error:" + e);
			} 
			this.close();
			
			return userVo;
		}
		*/
		//회원정보 수정
		public UserVo getUserupdate(UserVo userVo) {
			
			
			this.getConnection();
			
			try {
			    // 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " update users ";
				query += " set password = ?, ";
				query += " 	   name = ?, ";
				query += "     gender = ? ";
				query += " where no = ? ";
				
				//System.out.println(query);//쿼리 확인용
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, userVo.getPw());
				pstmt.setString(2, userVo.getName());
				pstmt.setString(3, userVo.getGender());
				pstmt.setInt(4, userVo.getNo());
				
				int count = pstmt.executeUpdate();
				
			    // 4.결과처리
				
			}  catch (SQLException e) {
			    System.out.println("error:" + e);
			} 
			this.close();
			
			return userVo;
		}
		
		
		
		
		
}











