package com.ceos19.springeverytime.service;

import com.ceos19.springeverytime.common.EntityGenerator;
import com.ceos19.springeverytime.domain.category.domain.Category;
import com.ceos19.springeverytime.domain.category.service.CategoryService;
import com.ceos19.springeverytime.domain.user.domain.User;
import com.ceos19.springeverytime.domain.category.dto.request.CategoryCreateRequest;
import com.ceos19.springeverytime.global.exception.BadRequestException;
import com.ceos19.springeverytime.domain.category.repository.CategoryRepository;
import com.ceos19.springeverytime.domain.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    CategoryService categoryService;

    User user1, user2;

    @BeforeEach
    void 테스트_셋업() {
        user1 = EntityGenerator.generateUser("test1");
        user2 = EntityGenerator.generateUser("test2");
    }

    @Test
    @DisplayName("게시판 생성 테스트")
    void 게시판_생성_테스트() {
        // given
        Category category = EntityGenerator.generateCategory(user1);
        CategoryCreateRequest request = CategoryCreateRequest.of(category.getName(), category.getDescription());
        given(categoryRepository.save(any(Category.class))).willReturn(category);
        given(userRepository.findByLoginId(anyString())).willReturn(Optional.of(user1));

        // when
        Category newCategory = categoryService.createCategory("testuser", request);

        // then
        assertThat(newCategory).isEqualTo(category);
    }

    @Test
    @DisplayName("게시판 관리자 변경 테스트")
    void 게시판_관리자_변경_테스트() {
        // given
        Category category = EntityGenerator.generateCategory(user1);
        given(categoryRepository.findById(any())).willReturn(Optional.of(category));

        // when
        Category findCategory = categoryService.findById(1L);
        findCategory.changeManager(user2);

        // then
        assertThat(findCategory.getManager()).isEqualTo(user2);
    }

    @Test
    @DisplayName("14일 이전 게시판 삭제시 오류 테스트")
    void 게시판_생성_후_14일_이전_삭제_테스트() {
        // given
        Category category = EntityGenerator.generateCategory(user1);
        category.setDateForTest(LocalDateTime.now());
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));

        // when
        // then
        assertThatThrownBy(() -> {categoryService.delete(1L);})
                .isInstanceOf(BadRequestException.class);

    }

    @Test
    @DisplayName("14일 이후 게시판 삭제 테스트")
    void 게시판_생성_후_14일_이후_삭제_테스트() {
        // given
        Category category = EntityGenerator.generateCategory(user1);
        category.setDateForTest(LocalDateTime.of(2024, 3, 1, 0, 0));
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));

        // when
        categoryService.delete(1L);

        // then
        verify(categoryRepository, times(1)).delete(category);
    }
}
