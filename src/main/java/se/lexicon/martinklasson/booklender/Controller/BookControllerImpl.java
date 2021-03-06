package se.lexicon.martinklasson.booklender.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.martinklasson.booklender.dto.BookDto;
import se.lexicon.martinklasson.booklender.service.BookService;
import se.lexicon.martinklasson.booklender.service.BookServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/books")
public class BookControllerImpl implements BookController {
    public static final String ALL = "all";
    public static final String TITLE = "title";
    public static final String AVAILABLE = "available";
    public static final String RESERVED = "reserved";

    BookService bookService;

    @Autowired
    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @GetMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> findById(@PathVariable int bookId) {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @Override
    public ResponseEntity<Object> find(
            @RequestParam(name = "type", defaultValue = ALL) String type,
            @RequestParam(name = "value", defaultValue = ALL) String value) {

        switch (type.toLowerCase().trim()){
            case TITLE:
                return ResponseEntity.ok(bookService.findByTitle(value));
            case AVAILABLE:
                return ResponseEntity.ok(bookService.findByAvailable(Boolean.parseBoolean(value)));
            case RESERVED:
                return ResponseEntity.ok(bookService.findByReserved(Boolean.parseBoolean(value)));
            case ALL:
                return ResponseEntity.ok(bookService.findAll());
            default:
                throw new IllegalArgumentException("NOT A VALID TYPE: " + type);
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<BookDto> save(@Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(bookDto));
    }

    @Override
    @PutMapping
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.update(bookDto));
    }
}
