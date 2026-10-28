package com.example.demo.member;

import com.example.demo.Member;
import com.example.demo.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void login_성공(){
        Member member = memberService.login("testuser", "1234");
        assertNotNull(member);
    }

    @Test
    void login_비밀번호틀림(){
        Member member = memberService.login("testuser", "wrong");
        assertNull(member);
    }
}
