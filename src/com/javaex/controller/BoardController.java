package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;


@WebServlet("/Board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//*************디닥*************//
		String action = request.getParameter("action");
		
		request.setCharacterEncoding("UTF-8");
		
		
		
		if("list".equals(action)) {
			System.out.println("리스트들어옴");
			BoardDao boardDao = new BoardDao();
			
			
			List<BoardVo> boardList  = boardDao.getList();
			
			
			System.out.println(boardList);
			
			request.setAttribute("bList", boardList);
			
			WebUtil.forward(request, response, "WEB-INF/views/board/list.jsp");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
