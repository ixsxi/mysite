package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[uesr Controller]");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		//텍스트 인코딩
		
		System.out.println(action);
		
		if("joinForm".equals(action)) {
			System.out.println("[userController.joinForm]");
			//회원가입폼
			//회원가입폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
			//회원가입	
		}else if("join".equals(action)) {
			System.out.println("[userController.join]");
		
		
		
		
		//*********회원가입**********
		
		//파라미터 꺼내기
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		
		//확인작업
		System.out.println(id+","+pw+","+name+","+gender);
		
		//vo로 만들기
		UserVo userVo = new UserVo(id,pw,name,gender);
		System.out.println(userVo);
		
		
		//dao.insert(vo) --> db저장
		
		UserDao userDao = new UserDao();
		int count = userDao.userInsert(userVo);
		//포워드
		WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		
		}else if("loginForm".equals(action)) {
			System.out.println("loginForm");
			
			//로그인 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}else if("login".equals(action)) {
				// 테스트
			System.out.println("login테스트 성공");
			
			
			//파라미터 값 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			
			System.out.println(id+","+password);
			
			//dao 회원정보 조회하기(세션 저장용)
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(id, password);
			if(userVo != null) {
				//성공 일때 (아이디 , 비번 일치했을때) 세션에 저장
				HttpSession session =request.getSession();
				session.setAttribute("authUser", userVo); // jsp에 데이터전달 request.setAttibute();
				WebUtil.redirect(request, response, "/mysite/main"); // 메인으로 
			}else {
				System.out.println("로그인 실패");
				
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			}
			
		
			
			
		}else if("logout".equals(action)) {
			System.out.println("logout test");
			
			//세션에 있는 "authUser"의 정보 삭제
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite/main");
			
			
			
			
		}else if("modifyForm".equals(action)) {
			
			System.out.println("테스트 모디피");
			//세션요청 
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int authUserNo = authUser.getNo();
			
			//로그인 사용자 정보
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
			//메소드
		}else if("modify".equals(action)) {
			
			HttpSession session = request.getSession();
			
			//no 은 사용자가 입력하는것이 아니기때문에 이렇게 써야한다.
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			//파라미터는 사용자가 입력한것을 가져온다.
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//담기 vo 
			UserVo userVo = new UserVo(no,password,name,gender);
			UserDao userDao = new UserDao();
			userDao.getUserupdate(userVo);
			
			//세션 네임 재설정....???
			
			((UserVo)session.getAttribute("authUser")).setName(name);
			
			WebUtil.redirect(request, response, "/mysite/main");
			
			
		}
		
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
