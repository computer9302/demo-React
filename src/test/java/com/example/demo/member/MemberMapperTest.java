package com.example.demo.member;

import com.example.demo.Member;
import com.example.demo.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void findByLoginId_회원조회(){
        Member member = memberMapper.findByLoginId("testuser");

        assertNotNull(member);
        assertEquals("testuser", member.getLoginId());
        assertEquals("테스트", member.getName());
    }

}
