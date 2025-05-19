package com.junit.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

// 통합테스트: controller, service, repository를 메모리에 다 띄워서 테스트하는 거
// 단위테스트: 필요한 부분만 띄워서 테스트
@DataJpaTest    // DB와 관련된 컴포넌트만 메모리에 로딩 (controller, service는 메모리에 띄우지 X)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void 책등록() {
        // given (데이터 준비)
        String title = "junit";
        String author = "yong";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 2. 책 목록보기

    // 3. 책 한건보기

    // 4. 책 수정

    // 5. 책 삭제
}
