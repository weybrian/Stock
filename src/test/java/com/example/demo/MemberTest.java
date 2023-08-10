package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.model.Member;
import com.example.service.MemberService;

@SpringBootTest // 加這個註解會讓程式啟動後，DI service，測試程式才能拿到service
public class MemberTest {

	@Autowired
	MemberService memberService;

	@Test
	public void addMember() {
		Member member = new Member();
		member.setOid("999");
		member.setAccount("test");
		member.setPwd("test");
		memberService.addMember(member);

		assertEquals("999", memberService.queryMember("999").get(0).getOid());
	}
}
