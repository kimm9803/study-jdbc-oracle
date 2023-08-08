package com.servlet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.servlet.dto.BookDTO;

public class BookDAO {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "khs";
	String pw = "1234";
	
	public BookDAO() {
		// 클래스 로딩, 드라이버 로딩 처리 로직
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 메서드 호출시 DB로부터 받은 값들을 list에 담음
	public ArrayList<BookDTO> select() {
		ArrayList<BookDTO> list = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet res = null;
		
		try {
			con = DriverManager.getConnection(url, id, pw);
			String sql = "SELECT * FROM book";
			pstmt = con.prepareStatement(sql);
			res = pstmt.executeQuery();
			
			while (res.next()) {
				int bookId = res.getInt("book_id");
				String bookName = res.getString("book_name");
				String bookLoc = res.getString("book_loc");
				
				BookDTO bookDTO = new BookDTO(bookId, bookName, bookLoc);
				list.add(bookDTO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null)	res.close();
				if (pstmt != null)	res.close();
				if (con != null)	res.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
}
