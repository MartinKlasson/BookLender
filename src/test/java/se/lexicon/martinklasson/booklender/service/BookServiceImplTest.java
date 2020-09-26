package se.lexicon.martinklasson.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.martinklasson.booklender.dto.BookDto;
import se.lexicon.martinklasson.booklender.entity.Book;
import se.lexicon.martinklasson.booklender.repository.BookRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookServiceImplTest {

    BookServiceImpl testObject;
    Book testBook1;
    Book testBook2;

    BookDto bookDto1 = new BookDto();
    BookDto bookDto2 = new BookDto();

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        testObject = new BookServiceImpl(bookRepository);
        testBook1 = new Book("Test Book 1", 30, BigDecimal.valueOf(10), "A test book for one");
        testBook1.setReserved(true);
        testBook1.setAvailable(true);
        bookRepository.save(testBook1);
        bookDto1 = testObject.getBookDto(testBook1);

        testBook2 = new Book("Test Book 2", 30, BigDecimal.valueOf(10), "A test book for two");
        testBook2.setReserved(false);
        testBook2.setAvailable(false);
        bookRepository.save(testBook2);
        bookDto2 = testObject.getBookDto(testBook2);


    }

    @Test
    void findByReserved() {
        assertEquals(1, testObject.findByReserved(false).size());
        assertEquals(1, testObject.findByReserved(true).size());
    }

    @Test
    void findByAvailable() {
        assertEquals(1, testObject.findByAvailable(false).size());
        assertEquals(1, testObject.findByAvailable(true).size());
        assertTrue(testObject.findByAvailable(true).contains(bookDto1));
        assertFalse(testObject.findByAvailable(true).contains(bookDto2));
    }

    @Test
    void findByTitle() {
        String title1 = "Test Book 1";
        String title2 = "Test Book 2";

        assertTrue(testObject.findByTitle(title1).contains(bookDto1));
        assertEquals(1, testObject.findByTitle(title1).size());
        assertTrue(testObject.findByTitle(title2).contains(bookDto2));
        assertFalse(testObject.findByTitle(title1).contains(bookDto2));
    }

    @Test
    void findById() {
        assertEquals(bookDto1, testObject.findById(bookDto1.getBookId()));
        assertEquals(bookDto2, testObject.findById(bookDto2.getBookId()));
    }

    @Test
    void findAll() {
        assertEquals(2, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(bookDto2));
        assertTrue(testObject.findAll().contains(bookDto1));
    }

    @Test
    void create() {
        BookDto bookDto3 = new BookDto();
        bookDto3.setTitle("Created Test Book");
        bookDto3.setMaxLoanDays(30);
        bookDto3.setFinePerDay(BigDecimal.valueOf(10));
        bookDto3.setDescription("Created book in test");

        bookDto3 = testObject.create(bookDto3);

        assertEquals(3, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(bookDto3));
        assertEquals(3, bookDto3.getBookId());

    }

    @Test
    void update() {
        bookDto1.setDescription("New description");
        bookDto1.setMaxLoanDays(20);
        testObject.update(bookDto1);

        assertEquals(20, testObject.findById(bookDto1.getBookId()).getMaxLoanDays());
        assertEquals("New description", testObject.findById(bookDto1.getBookId()).getDescription());
    }

    @Test
    void delete() {

        assertTrue(testObject.delete(testBook1.getBookId()));
        assertFalse(testObject.findAll().contains(bookDto1));
        assertEquals(1, testObject.findAll().size());

    }
}