package com.junit.demo.domain;

import com.junit.demo.dto.response.BookRespDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Book {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length=50, nullable = false)
    private String title;

    @Column(length=20, nullable = false)
    private String author;

    @Builder
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public void update(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public BookRespDto toDto() {
        return BookRespDto.builder()
                .id(id)
                .title(title)
                .author(author)
                .build();
    }
}
