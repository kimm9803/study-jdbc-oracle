package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "khs";
		String pw = "1234";
		
		Connection con = null;
		Statement stmt = null;
		// 많은 데이터를 한꺼번에 담을 객체
		ResultSet res = null;
		
		try {
			// OracleDriver 로딩
			Class.forName(driver);
			
			// Java와 Oracle 연결
			con = DriverManager.getConnection(url, id, pw);
			
			// 통신하는 객체 생성
			stmt = con.createStatement();
			
			// query문 작성
			String sql = "SELECT * FROM book";
			
			// executeQuery() -> select 할 때 사용하는 메서드
			res = stmt.executeQuery(sql);
			
			while (res.next()) {
				int bookId = res.getInt("book_id");
				String bookName = res.getString("book_name");
				String bookLoc = res.getString("book_loc");
				
				out.print("bookId : " + bookId + "</br>");
				out.print("bookName : " + bookName + "</br>");
				out.print("bookLoc : " + bookLoc + "</br>");
				out.print("-----------------------------</br>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)	res.close();
				if (stmt != null)	stmt.close();
				if (con != null)	con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
