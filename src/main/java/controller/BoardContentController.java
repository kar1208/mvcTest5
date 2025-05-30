package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import vo.BoardVo;

@SuppressWarnings("serial") //노란색줄 삭제
@WebServlet("/boardContent")
public class BoardContentController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = request.getParameter("idx")==null ? 0 : Integer.parseInt(request.getParameter("idx")); //중요
		
		BoardDao dao = new BoardDao();
		
		dao.setReadNumUpdate(idx); // 조회수 증가하기
		
		BoardVo vo = dao.getBoardContent(idx);
		
		request.setAttribute("vo", vo);
		String viewPage = "/WEB-INF/boardTest/boardContent.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
