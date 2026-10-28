package com.example.demo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper,
                         PasswordEncoder passwordEncoder){
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Member login(String loginId, String password){
        Member member = memberMapper.findByLoginId(loginId);

        // 회원 없음
        if (member == null){
            return null;
        }

        // BCrypt 해시 비교 (실무 방식)
        if (!passwordEncoder.matches(password, member.getPassword())){
            return null;
        }

        return member;
    }

    public boolean isLoginIdAvailable(String loginId){
        if (loginId == null || loginId.isBlank()){
            return false;
        }
        return memberMapper.countByLoginId(loginId) == 0;
    }

    public void signup(String loginId, String password, String name){
        if (!isLoginIdAvailable(loginId)){
            throw new  IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (password == null || password.isBlank()){
            throw new IllegalArgumentException("비밀번호를 입력하세요.");
        }
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("이름을 입력하세요.");
        }

        Member member = new Member();
        member.setLoginId(loginId);
        member.setPassword(passwordEncoder.encode(password)); //BCrypt
        member.setName(name);
        memberMapper.insert(member);
    }




}
