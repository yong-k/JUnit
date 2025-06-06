package com.junit.demo.dto.request;

import com.junit.demo.domain.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // Controller에서 Setter가 호출되면서 DTO에 값이 채워진다.
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
