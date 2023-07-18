package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.Member;

@Repository
public class MemberDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void addMember(Member member) {
		System.out.println("EXCUTE INSERT MEMBER");
		jdbcTemplate.update("INSERT INTO member(OID, ACCOUNT, PWD) " + "VALUES (?,?,?)", member.getOid(),
				member.getAccount(), member.getPwd());
	}

}
