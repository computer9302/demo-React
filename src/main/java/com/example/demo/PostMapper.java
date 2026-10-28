package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    // 전체 글 개수 (페이징 계산용)
    int countAll();

    // 목록 조회 (페이징)
    List<Post> findAllpaged(@Param("offset") int offset,
                            @Param("size") int size);

    // 상세 조회
    Post findById(@Param("postId") Long postId);

    // 등록
    void insert(Post post);

    // 수정
    void update(Post post);

    // 삭제
    void delete(@Param("postId") Long postId);

    int countBySearch(@Param("keyword") String keyword,
                      @Param("type") String type);

    List<Post> findBySearchPaged(@Param("offset") int offset,
                                 @Param("size") int size,
                                 @Param("keyword") String keyword,
                                 @Param("type") String type);

}
