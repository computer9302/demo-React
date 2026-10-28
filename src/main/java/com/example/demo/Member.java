package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Member {
    private Long memberId;
    private String loginId;
    private String password;
    private String name;
    private LocalDateTime createdAt;
}
