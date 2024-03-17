package com.ceos19.springeverytime.repository;

import com.ceos19.springeverytime.domain.Category;
import com.ceos19.springeverytime.domain.Post;
import com.ceos19.springeverytime.domain.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
@Transactional
public class PostRepositoryTests {
    @Autowired
    EntityManager em;
    @Autowired
    PostRepository postRepository;

    @Test
    public void 게시글_생성_테스트() throws Exception {
        //given
        User user1 = createUser("user1");
        Category category = createCategory(user1);
        Post post1 = new Post("첫번째 글", "첫번째 글입니다.", true, new Date(), new Date(), user1, category);

        em.persist(user1);
        em.persist(category);

        //when
        postRepository.save(post1);

        //then
        Assertions.assertEquals(em.find(Post.class, post1.getPostId()), post1);
    }

    private User createUser(String id) {
        return new User(
                id,
                "1234",
                "nickname",
                "kim",
                "computer",
                "20",
                "test@example.com",
                true,
                new Date());
    }

    private Category createCategory(User manager) {
        Category freeCategory = new Category("자유게시판", "자유롭게 이야기 해봐요", manager);
        return freeCategory;
    }

}