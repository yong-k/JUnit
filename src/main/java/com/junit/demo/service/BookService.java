package com.junit.demo.service;

import com.junit.demo.domain.Book;
import com.junit.demo.domain.BookRepository;
import com.junit.demo.dto.BookRespDto;
import com.junit.demo.dto.BookSaveReqDto;
import com.junit.demo.util.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책등록
    @Transactional(rollbackFor = RuntimeException.class)    // RuntimeException 발생하면 rollback
    public BookRespDto insertBook(BookSaveReqDto dto) {
        // 서비스단에서 DTO로 데이터 받아서, 그걸 entity로 바꿔서 save하고, DTO로 응답해주기
        // 영속화된 객체는 절대 service 단에서 빠져나가지 못하게 막아야한다.
        Book bookPS = bookRepository.save(dto.toEntity());
        if (bookPS != null) {
            // 메일보내기 메서드 호출 (return true or false)
            if (!mailSender.send())
                throw new RuntimeException("메일이 전송되지 않았습니다.");
        }
        return new BookRespDto().toDto(bookPS);
    }

    // 2. 책목록보기
    public List<BookRespDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(new BookRespDto()::toDto)
                .collect(Collectors.toList());
    }

    // 3. 책한건보기
    public BookRespDto getBook(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent())
            return new BookRespDto().toDto(bookOP.get());
        else
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
    }

    // 4. 책삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // 5. 책수정
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateBook(Long id, BookSaveReqDto dto) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }   // 메서드 종료시에 더티체킹(flush)으로 update된다.
}
