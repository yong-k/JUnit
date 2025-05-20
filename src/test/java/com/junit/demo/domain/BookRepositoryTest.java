package com.junit.demo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// 통합테스트: controller, service, repository를 메모리에 다 띄워서 테스트하는 거
// 단위테스트: 필요한 부분만 띄워서 테스트
@DataJpaTest    // DB와 관련된 컴포넌트만 메모리에 로딩 (controller, service는 메모리에 띄우지 X)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    //@BeforeAll  // 테스트 시작 전에 한번만 실행
    @BeforeEach // 각 테스트 시작 전에 한번씩 실행
    public void 데이터준비() {
        String title = "junit";
        String author = "yong";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }

    // 1. 책 등록
    @Test
    public void 책등록() {
        // given (데이터 준비)
        String title = "junit-spring";
        String author = "jeong";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }   // 트랜잭션 종료 (저장된 데이터 초기화)

    // 2. 책 목록보기
    @Test
    public void 책목록보기() {
        // given
        String title = "junit";
        String author = "yong";

        // when
        List<Book> booksPS = bookRepository.findAll();

        // then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    }   // 트랜잭션 종료 (저장된 데이터 초기화)

    // 3. 책 한건보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책한건보기() {
        // given
        String title = "junit";
        String author = "yong";

        // when
        Book bookPS = bookRepository.findById(1L).get();

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제() {
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        assertFalse(bookRepository.findById(id).isPresent());  // false일 때 성공
    }

    // 5. 책 수정
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책수정() {
        // given
        Long id = 1L;
        String title = "junit-spring";
        String author = "jeong";
        Book book = new Book(id, title, author);

        // when
        Book bookPS = bookRepository.save(book);

        // then
        assertEquals(id, bookPS.getId());
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }
}
