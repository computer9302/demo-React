package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentMapper commentMapper;

    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public List<Comment> getComments(Long postId){
        return commentMapper.findByPostId(postId);
    }

    public Comment addComment(Long postId, Member loginMember, String content){
        if (content == null || content.isBlank()){
            throw new IllegalArgumentException("댓글을 입력하세요.");
        }

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setMemberId(loginMember.getMemberId());
        comment.setContent(content);
        commentMapper.insert(comment);

        Comment saved = commentMapper.findById(comment.getCommentId());
        saved.setWriterName(loginMember.getName());
        return saved;
    }

    public void deleteComment(Long commentId, Member loginMember){
        Comment comment = commentMapper.findById(commentId);

        if (comment == null){
            throw new IllegalArgumentException("댓글이 없습니다.");
        }

        if (!comment.getMemberId().equals(loginMember.getMemberId())){
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        commentMapper.delete(commentId);
    }
}
