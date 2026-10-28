package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {
    private Long commentId;
    private Long postId;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;

    private String writerName; // JOIN용
}
