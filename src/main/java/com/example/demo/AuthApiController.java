package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthApiController {

    private final MemberService memberService;

    public AuthApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 로그인
    @PostMapping("/login")
    public ApiResponse login(@RequestParam String loginId,
                             @RequestParam String password,
                             HttpSession session){
        Member member = memberService.login(loginId, password);
        if (member == null){
            return new ApiResponse(false, "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        member.setPassword(null);
        session.setAttribute("loginMember", member);
        return new ApiResponse(true, "로그인 성공");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ApiResponse logout(HttpSession session){
        session.invalidate();
        return new ApiResponse(true, "로그아웃되었습니다.");
    }

    // 현재 로그인 사용자 정보
    @GetMapping("/me")
    public Map<String, Object> me(HttpSession session){
        Member member = (Member) session.getAttribute("loginMember");
        if (member == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return Map.of(
                "memberId", member.getMemberId(),
                "loginId", member.getLoginId(),
                "name", member.getName()
        );
    }

    // 회원가입
    @PostMapping("/signup")
    public ApiResponse signup(@RequestParam String loginId,
                              @RequestParam String password,
                              @RequestParam String name){
        try{
            memberService.signup(loginId, password, name);
            return new ApiResponse(true, "회원가입이 완료되었습니다.");
        } catch(IllegalArgumentException e){
            return new ApiResponse(false, e.getMessage());
        }
    }
}
