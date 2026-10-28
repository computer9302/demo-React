package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {
    private Long postId;
    private Long memberId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // 목록에서 JOIN으로 가져올 작성자 이름(DB 칼럼 아님)
    private String writerName;
}
