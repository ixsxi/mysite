package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


@WebServlet("/Board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//*************디닥*************//
		String action = request.getParameter("action");
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		
		if("list".equals(action)) {
			System.out.println("리스트들어옴");
			BoardDao boardDao = new BoardDao();
			
			
			List<BoardVo> boardList  = boardDao.getList();
			
			
			System.out.println(boardList);
			
			request.setAttribute("bList", boardList);
			
			WebUtil.forward(request, response, "WEB-INF/views/board/list.jsp");
		}else if("read".equals(action)) {
			System.out.println("읽기");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getread(no);
			
			request.setAttribute("boardVo", boardVo);
			System.out.println(boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
			
		}else if("Wform".equals(action)) {
			System.out.println("글쓰기 폼 ");
			
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}else if("insert".equals(action)) {
			System.out.println("글쓰기 메소드 실행");
			
		 	String title = request.getParameter("title");
			String content = request.getParameter("content");
			//로그인한 정보를 고유의값 no 로 가지고오기.
			int uNum = ((UserVo)session.getAttribute("authUser")).getNo();
			
			BoardVo boardVo = new BoardVo(title,content,uNum);
			BoardDao boardDao =new BoardDao();
			boardDao.write(boardVo);
			
		
			WebUtil.redirect(request, response, "/mysite/Board?action=list");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
