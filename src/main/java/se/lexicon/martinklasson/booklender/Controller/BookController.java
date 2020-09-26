package se.lexicon.martinklasson.booklender.Controller;

import org.springframework.http.ResponseEntity;
import se.lexicon.martinklasson.booklender.dto.BookDto;

public interface BookController {

    ResponseEntity<BookDto> findById(int bookId);
    ResponseEntity<Object> find(final String type, final String value);
    ResponseEntity<BookDto> save(BookDto bookDto);
    ResponseEntity<BookDto> update(BookDto bookDto);
}
