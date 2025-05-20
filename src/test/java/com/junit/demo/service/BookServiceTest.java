package com.junit.demo.service;

import com.junit.demo.domain.BookRepository;
import com.junit.demo.dto.BookRespDto;
import com.junit.demo.dto.BookSaveReqDto;
import com.junit.demo.util.MailSenderStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    // 문제점 --> service 만 테스트하고 싶은데, repository 까지 함께 테스트되고 있음
    @Test
    public void 책등록하기() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit");
        dto.setAuthor("yong");

        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();
        // 가짜로 bookRepository 만들기

        // when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.insertBook(dto);

        // then
        assertEquals(dto.getTitle(), bookRespDto.getTitle());
        assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
    }
}
