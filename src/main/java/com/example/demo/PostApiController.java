package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;

    public PostApiController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    private Member getLoginMember(HttpSession session){
        Member member = (Member) session.getAttribute("loginMember");
        if (member == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return member;
    }

    // 댓글 등록 (AJAX)
    @PostMapping("/{postId}/comments")
    public CommentResponse addComment(@PathVariable Long postId,
                                      @RequestParam String content,
                                      HttpSession session){
        Member loginMember = getLoginMember(session);
        Comment comment = commentService.addComment(postId, loginMember, content);
        return CommentResponse.from(comment);
    }

    // 댓글 삭제 (AJAX)
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ApiResponse deleteComment(@PathVariable Long postId,
                                     @PathVariable Long commentId,
                                     HttpSession session){
        Member loginMember = getLoginMember(session);
        try {
            commentService.deleteComment(commentId, loginMember);
            return new ApiResponse(true, "댓글이 삭제되었습니다.");
        } catch(IllegalArgumentException e){
            return new ApiResponse(false, e.getMessage());
        }
    }

    // 게시글 삭제 (AJAX)
    @DeleteMapping("/{postId}")
    public ApiResponse deletePost(@PathVariable Long postId,
                                HttpSession session){
        Member loginMember = getLoginMember(session);
        try{
            postService.deletePost(postId, loginMember);
            return new ApiResponse(true, "삭제되었습니다.");
        }catch (IllegalArgumentException e){
            return new ApiResponse(false, e.getMessage());
        }
    }

    // 게시글 목록
    @GetMapping
    public PageDto<Post> list(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "") String keyword,
                              @RequestParam(defaultValue = "title") String type,
                              HttpSession session){
        getLoginMember(session);
        return postService.getPostList(page, keyword, type);
    }

    // 게시글 상세 + 댓글
    @GetMapping("/{postId}")
    public Map<String, Object> detail(@PathVariable Long postId, HttpSession session){
        getLoginMember(session);
        Post post = postService.getPost(postId);
        if (post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "글이 없습니다.");
        }
        return Map.of(
                "post", post,
                "comments", commentService.getComments(postId));

    }

    // 게시글 등록
    @PostMapping
    public ApiResponse create(@RequestParam String title,
                              @RequestParam String content,
                              HttpSession session){
        Member loginMember = getLoginMember(session);
        try {
            postService.createPost(loginMember, title, content);
            return new ApiResponse(true, "등록되었습니다.");
        }catch (IllegalArgumentException e){
            return new ApiResponse(false, e.getMessage());
        }

    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ApiResponse update(@PathVariable Long postId,
                              @RequestParam String title,
                              @RequestParam String content,
                              HttpSession session){
        Member loginMember = getLoginMember(session);
        try{
            postService.updatePost(postId, loginMember, title, content);
            return new ApiResponse(true, "수정되었습니다.");
        } catch (IllegalArgumentException e){
            return new ApiResponse(false, e.getMessage());
        }
    }

}
