package com.junit.demo.service;

import com.junit.demo.domain.Book;
import com.junit.demo.domain.BookRepository;
import com.junit.demo.dto.BookRespDto;
import com.junit.demo.dto.BookSaveReqDto;
import com.junit.demo.util.MailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기() {
        // given (파라미터로 들어올 데이터)
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit");
        dto.setAuthor("yong");

        // stub (행동정의, 가설)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.insertBook(dto);

        // then
//        assertEquals(dto.getTitle(), bookRespDto.getTitle());
//        assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    public void 책목록보기() {
        // given

        // stub
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit", "yong"));
        books.add(new Book(2L, "spring", "jeong"));
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookRespDto> bookRespDtoList = bookService.getAllBooks();

        // then
        assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo("junit");
        assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo("yong");
        assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo("spring");
        assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo("jeong");
    }

    @Test
    public void 책한건보기() {
        // given
        Long id = 1L;

        // stub
        Book book = new Book(1L, "junit", "yong");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.getBook(id);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    public void 책수정하기() {
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("spring");
        dto.setAuthor("jeong");

        // stub
        Book book = new Book(1L, "junit", "yong");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.updateBook(id, dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }
}
