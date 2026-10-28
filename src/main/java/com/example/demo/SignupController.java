package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    private final MemberService memberService;

    public SignupController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signup")
    public String signPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String loginId,
                         @RequestParam String password,
                         @RequestParam String name,
                         Model model){
        try {
            memberService.signup(loginId, password, name);
            return "redirect:/login";
        }catch (IllegalArgumentException e){
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("loginId", loginId);
            model.addAttribute("name", name);
            return "signup";
        }
    }
}
