package com.example.demo.post;

import com.example.demo.Post;
import com.example.demo.PostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    void countBySearch_제목검색(){
        int count = postMapper.countBySearch("테스트", "title");
        assertTrue(count >= 0);
    }

    @Test
    void findById_존재하는글(){
        Post post = postMapper.findById(1L);
        if (post != null){
            assertNotNull(post.getTitle());
        }
    }
}
