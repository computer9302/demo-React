package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> findByPostId(@Param("postId") Long postId);

    void insert(Comment comment);

    void delete(@Param("commentId") Long commentId);

    Comment findById(@Param("commentId") Long commentId);
}
