package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/posts")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "title") String type,
                       HttpSession session,
                       Model model){

        // 로그인 체크 (Day2 세션 재사용)
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null){
            return "redirect:/login";
        }

        PageDto<Post> pageDto = postService.getPostList(page, keyword, type);

        model.addAttribute("pageDto", pageDto);
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);
        return "post/list";
    }

    // 공통: 로그인 회원 가져오기 (private 메서드)
    private Member getLoginMember(HttpSession session){
        return (Member) session.getAttribute("loginMember");
    }

    // ==== 등록 ====
    @GetMapping("/posts/new")
    public String writeForm(HttpSession session){
        if (getLoginMember(session) == null){
            return "redirect:/login";
        }
        return "post/write";
    }

    @PostMapping("/posts")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         HttpSession session){
        Member loginMember = getLoginMember(session);
        if (loginMember == null){
            return "redirect:/login";
        }

        postService.createPost(loginMember, title, content);
        return "redirect:/posts";
    }

    // ==== 상세 ====
    @GetMapping("/posts/{postId}")
    public String detail(@PathVariable Long postId,
                         HttpSession session,
                         Model model){
        Member loginMember = getLoginMember(session);
        if (loginMember == null){
            return "redirect:/login";
        }

        Post post = postService.getPost(postId);
        if (post == null){
            return "redirect:/posts";
        }

        List<Comment> comments = commentService.getComments(postId);

        model.addAttribute("post", post);
        model.addAttribute("loginMember", loginMember); // 수정/삭제 버튼 표시용
        model.addAttribute("comments", comments);
        return "post/detail";
    }

    // ==== 수정 ====
    @GetMapping("/posts/{postId}/edit")
    public String editForm(@PathVariable Long postId,
                           HttpSession session,
                           Model model){
        Member loginMember = getLoginMember(session);
        if (loginMember == null){
            return "redirect:/login";
        }

        Post post = postService.getPost(postId);
        if (post == null){
            return "redirect:/posts";
        }

        // 본인 글만 수정 폼 접근
        if (!post.getMemberId().equals(loginMember.getMemberId())){
            return "redirect:/posts/" + postId;
        }

        model.addAttribute("post", post);
        return "post/edit";
    }

    @PostMapping("/posts/{postId}/edit")
    public String update(@PathVariable Long postId,
                         @RequestParam String title,
                         @RequestParam String content,
                         HttpSession session){
        Member loginMember = getLoginMember(session);
        if (loginMember == null){
            return "redirect:/login";
        }

        postService.updatePost(postId, loginMember, title, content);
        return "redirect:/posts/" + postId;
    }

    // ==== 삭제 ====
    @PostMapping("/posts/{postId}/delete")
    public String delete(@PathVariable Long postId,
                         HttpSession session){
        Member loginMember = getLoginMember(session);
        if (loginMember == null){
            return "redirect:/login";
        }

        postService.deletePost(postId, loginMember);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{postId}/comments")
    public String addComment(@PathVariable Long postId,
                             @RequestParam String content,
                             HttpSession session){
        Member loginMember = getLoginMember(session);
        if (loginMember == null){
            return "redirect:/login";
        }

        commentService.addComment(postId, loginMember, content);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId,
                                @PathVariable Long commentId,
                                HttpSession session){
        Member loginMember = getLoginMember(session);
        if (loginMember == null){
            return "redirect:/login";
        }

        commentService.deleteComment(commentId, loginMember);
        return "redirect:/posts/" + postId;
    }
}

