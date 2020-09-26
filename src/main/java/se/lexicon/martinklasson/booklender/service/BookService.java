package se.lexicon.martinklasson.booklender.service;

import se.lexicon.martinklasson.booklender.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> findByReserved(boolean isReserved);
    List<BookDto> findByAvailable(boolean isAvailable);
    List<BookDto> findByTitle(String bookTitle);
    BookDto findById(int bookId);
    List<BookDto> findAll();
    BookDto create(BookDto bookDto);
    BookDto update(BookDto bookDto);
    boolean delete(int bookId);
}
