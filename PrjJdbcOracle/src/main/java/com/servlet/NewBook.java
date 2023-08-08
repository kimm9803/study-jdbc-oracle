package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NewBook")
public class NewBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String bookName = request.getParameter("book_name");
		String bookLoc = request.getParameter("book_loc");
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "khs";
		String pw = "1234";
		
		Connection con = null;
		Statement stmt = null;
		
		/*
		 * JDBC 실행 순서
		 * Driver loading -> Connection -> Statement -> query -> run
		 */
		try {
			// OracleDriver 로딩
			Class.forName(driver);
			
			// Java와 Oracle 연결
			con = DriverManager.getConnection(url, id, pw);
			
			// 통신하는 객체를 만듦
			stmt = con.createStatement();
			
			// query문 작성
			String sql = "INSERT INTO book(book_id, book_name, book_loc)";
				   sql += "VALUES(BOOK_SEQ.NEXTVAL, '" + bookName + "', '" + bookLoc + "')";
			
			// query문 전송
		    // executeUpdate() -> 수정, 삽입, 삭제를 할 때 쓰는 메서드
		    // 데이터가 한 행이 들어갔기 때문에 result = 1;
			int result = stmt.executeUpdate(sql);
			
			if (result == 1) {
				out.print("INSERT SUCCESS!!");
			} else {
				out.print("INSERT FAIL!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 리소스 해제
				if (stmt != null)	stmt.close();
				if (con  != null)	stmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
