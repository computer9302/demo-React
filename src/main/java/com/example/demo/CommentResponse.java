package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String writerName;
    private String content;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment){
        return new CommentResponse(
                comment.getCommentId(),
                comment.getWriterName(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
