package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.BoardVo;

public class BoardDao {
	private Connection conn = DbConnection.getConn();
//	private Connection conn2 = DbConnection.getConn();
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	String sql = "";
	BoardVo vo = null;
	
	public void pstmtClose() {
		if(pstmt !=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {

			}
		}
	}
	public void rsClose() {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {}
			finally {
				pstmtClose();
			}
		}
	}
	//게시글 전체 리스트
	public List<BoardVo> getBoardList() {
//		if(conn.equals(conn2)) {
//			System.out.println("conn과 conn2는 같은 객체입니다.");
//		}
//		else {
//			System.out.println("conn과 conn2는 다른 객체입니다.");
//		}
		List<BoardVo> vos = new ArrayList<BoardVo>();
		try {
			sql = "select * from boardTest order by idx desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setIdx(rs.getInt("idx"));
				vo.setName(rs.getString("name"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setHostIp(rs.getString("hostIp"));
				vo.setReadNum(rs.getInt("readNum"));
				vo.setwDate(rs.getDate("wDate"));
				vos.add(vo);  // vos <- 실제는 없는 용어 
			}
					
		} catch (SQLException e) {
			System.out.println("sql오류(getBoardList) : " + e.getMessage());
		} finally {
			rsClose();
		}
		return vos;
	}
	
	// 게시글 등록처리하기
	public int setBoardInput(BoardVo vo) {
		int res = 0;
		try {
			sql = "insert into boardTest values (default,?,?,?,?,default,default)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getHostIp());
			res = pstmt.executeUpdate(); //실행하는 횟수에 따라 정수가 넘어간다.
			
		} catch (SQLException e) {
			System.out.println("sql오류(setBoardInput) : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}
	
	// 글 조회수 증가하기
	public void setReadNumUpdate(int idx) {
		try {
			sql = "update boardTest set readNum = readNum + 1 where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql오류(setReadNumUpdate) : " + e.getMessage());
		} finally {
			pstmtClose();
		}
	}
	
	// 글 내용 조회하기 (select는 무조건 rs를 닫는다)
	public BoardVo getBoardContent(int idx) {
		vo = new BoardVo();
		try {
			sql = "select * from boardTest where idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
//				vo.setIdx(rs.getInt("idx"));
				vo.setIdx(idx);
				vo.setName(rs.getString("name"));
				vo.setwDate(rs.getDate("wDate"));
				vo.setReadNum(rs.getInt("readNum"));
				vo.setHostIp(rs.getString("hostIp"));
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				
			}
		} catch (SQLException e) {
			System.out.println("sql오류(getBoardContent) : " + e.getMessage());
		} finally {
			rsClose();
		}
		return vo;
	}
	
	// 게시글 삭제 처리
	public int setBoardDelete(int idx) {
		int res = 0;
		try {
			String sql = "delete from boardTest where idx = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("sql오류(setBoardDelete) : " + e.getMessage());
		} finally {
			pstmtClose();
		}
		return res;
	}
}
