package com.junit.demo.controller;

import com.junit.demo.dto.response.BookRespDto;
import com.junit.demo.dto.request.BookSaveReqDto;
import com.junit.demo.dto.response.CommonRespDto;
import com.junit.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    // 1. 책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody BookSaveReqDto bookSaveReqDto) {
        BookRespDto bookRespDto = bookService.insertBook(bookSaveReqDto);
        CommonRespDto<?> commonRespDto = CommonRespDto.builder()
                .code(1).message("책등록 성공").body(bookRespDto).build();
        return new ResponseEntity<>(commonRespDto, HttpStatus.CREATED);    // 201
    }

    // 2. 책목록보기
    public ResponseEntity<?> getBookList() {
        return null;
    }

    // 3. 책한건보기
    public ResponseEntity<?> getBook() {
        return null;
    }

    // 4. 책삭제
    public ResponseEntity<?> deleteBook() {
        return null;
    }

    // 5. 책수정
    public ResponseEntity<?> updateBook() {
        return null;
    }
}
