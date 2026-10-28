package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApiController {

    private final MemberService memberService;

    public MemberApiController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/api/members/check-loginId")
    public DuplicateCheckResponse checkLoginId(@RequestParam String loginId){

        boolean available = memberService.isLoginIdAvailable(loginId);

        if (available){
            return new DuplicateCheckResponse(true, "사용 가능한 아이디입니다.");
        }else {
            return new DuplicateCheckResponse(false, "이미 사용 중인 아이디입니다.");
        }
    }
}
