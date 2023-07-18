package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Member;
import com.example.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	Member member;

	@Autowired
	MemberService memberService;

	@RequestMapping("/addMember")
	public String test() {
		member = new Member();
		member.setOid("1");
		member.setAccount("weybrian");
		member.setPwd("test");
		memberService.addMember(member);
		return "New Member added";
	}
}
