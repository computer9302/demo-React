package com.example.demo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/login";
    }

    // 로그인 화면(GET /Login)
    @GetMapping("/login")
    public String loginPage(){
        return "login"; //-> Login.jsp
    }

    // 로그인 처리 (POST /Login)
    @PostMapping("/login")
    public String login(@RequestParam String loginId,
                        @RequestParam String password,
                        HttpSession session,
                        Model model){

        Member member = memberService.login(loginId, password);

        if(member == null){
            model.addAttribute("errorMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }

        // 세션에 로그인 회원 저장 (비밀번호는 넣지 않기)
        member.setPassword(null);
        session.setAttribute("loginMember", member);
        return "redirect:/main";
    }

    // 메인 (GET /main)
    @GetMapping("/main")
    public String main(HttpSession session, Model model, HttpServletResponse response){
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null){
            return "redirect:/login"; // 비로그인 -> 로그인 페이지
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        model.addAttribute("loginMember", loginMember);
        return "main";
    }

    // 로그아웃(POST / logout)
    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate(); // 세션 삭제
        return "redirect:/login";
    }
}
