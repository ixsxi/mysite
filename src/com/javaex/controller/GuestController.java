package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;



@WebServlet("/guest")
public class GuestController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");	
		
		System.out.println("컨트롤러");
		String action = request.getParameter("action");
		System.out.println(action);
		
		
		if("list".equals(action)) {
			System.out.println("[리스트]");
			
			
			GuestbookDao guestbookdao = new GuestbookDao();
			List<GuestbookVo> guestbookList  = guestbookdao.getList(); // 리턴된 값 guestbookList
			
			System.out.println("controller-------------------------");
			System.out.println(guestbookList);
			
			request.setAttribute("gList", guestbookList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");	
			
		}else if("insert".equals(action)) {
	         System.out.println("[추가]");
	          // add list.jsp에서 넘어온 정보들을  vo 로 담는다 .
	         String name = request.getParameter("name");
	         String password = request.getParameter("pass");
	         String content = request.getParameter("content");
	         
	         GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
	         
	         GuestbookDao guestbookDao = new GuestbookDao();
	         guestbookDao.Insert(guestbookVo);
	         
	         WebUtil.redirect(request, response, "/mysite/guest?action=list");
	         
	      }else if("dform".equals(action)) {
			System.out.println("삭제");
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		}else if("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String pass = request.getParameter("pass");
			
			GuestbookVo guestbookVo = new GuestbookVo(no,pass);
			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.delete(guestbookVo);
			
			WebUtil.redirect(request, response, "/mysite/guest?action=list");
			
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
