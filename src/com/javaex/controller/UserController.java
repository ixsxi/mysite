package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
