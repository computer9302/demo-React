package com.example.demo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    // Login_Id로 회원 1명 조회 (Day 1 연동 테스트용)
    Member findByLoginId(String loginId);

    // 아디디 존재 여부(1이면 중복)
    int countByLoginId(@Param("loginId") String loginId);

    // 회원 등록d
    void insert(Member member);
}
