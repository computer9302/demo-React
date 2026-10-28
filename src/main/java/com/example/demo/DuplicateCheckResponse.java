package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateCheckResponse {
    private boolean available; // true = 사용 가능
    private String message;
}
