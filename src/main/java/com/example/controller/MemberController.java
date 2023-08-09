package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Member;
import com.example.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	MemberService memberService;

	@RequestMapping("/member")
	public String addMember() {
		Member member = new Member();
		member.setOid("3");
		member.setAccount("weybrian");
		member.setPwd("test");
		memberService.addMember(member);
		return "New Member added";
	}

	@RequestMapping("/member/{oid}")
	public String queryMember(@PathVariable String oid) {
		StringBuilder sb = new StringBuilder();
		for (Member member : memberService.queryMember(oid)) {
			sb.append(member.toString());
		}
		return sb.toString();
	}
}
