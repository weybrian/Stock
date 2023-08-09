package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Member;

@Repository
public class MemberDao {
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
	static final String USER = "oracle";
	static final String PASS = "oracle";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void addMember(Member member) {
		Connection conn = null;
		PreparedStatement stmt = null;

		System.out.println("EXCUTE INSERT MEMBER");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql = "INSERT INTO member(OID, ACCOUNT, PWD) VALUES (?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getOid());
			stmt.setString(2, member.getAccount());
			stmt.setString(3, member.getPwd());
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Member> queryMember(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<Member> list = new ArrayList<>();

		System.out.println("EXCUTE queryMember");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String sql = "SELECT * FROM member WHERE OID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();

			Member member = new Member();
			while (rs.next()) {
				// Retrieve by column name
				member.setOid(rs.getString("oid"));
				member.setAccount(rs.getString("account"));
				member.setPwd(rs.getString("pwd"));
				list.add(member);
			}
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
