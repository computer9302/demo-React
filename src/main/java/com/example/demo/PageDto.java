package com.example.demo;

import lombok.Getter;

import java.util.List;

@Getter
public class PageDto<T> {

    private final List<T> list; // 현재 페이지 글 목록
    private final int page; // 현재 페이지 (1부터)
    private final int size; // 페이지당 개수
    private final int totalCount; // 전체 글 수
    private final int totalPages; // 전체 페이지 수

    public PageDto(List<T> list , int page, int size, int totalCount) {
        this.totalCount = totalCount;
        this.size = size;
        this.list = list;
        this.page = page;
        this.totalPages = (int) Math.ceil((double)totalCount / size);
    }

    public boolean hasPrev(){
        return page > 1;
    }

    public boolean hasNext(){
        return page < totalPages;
    }

    public int prevPage(){
        return page - 1;
    }

    public int nextPage(){
        return page + 1;
    }
}
