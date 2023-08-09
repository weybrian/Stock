package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.MemberDao;
import com.example.model.Member;

@Service
public class MemberService {

	@Autowired
	MemberDao memberDao;

	public void addMember(Member member) {
		memberDao.addMember(member);
	}

	public List<Member> queryMember(String oid) {
		return memberDao.queryMember(oid);
	}

}
