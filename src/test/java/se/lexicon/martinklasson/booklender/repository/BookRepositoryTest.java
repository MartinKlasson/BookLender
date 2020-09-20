package se.lexicon.martinklasson.booklender.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.martinklasson.booklender.entity.Book;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    Book testBook1;
    Book testBook2;

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        testBook1 = new Book("Test Book", 30, new BigDecimal(10), "Test Book 1 Description");
        testBook2 = new Book("Test Book2", 20, new BigDecimal(10), "Test Book 2 description");
        testBook1.setAvailable(true);
        testBook1.setReserved(true);
        testBook2.setAvailable(false);
        testBook2.setReserved(true);
        bookRepository.save(testBook1);
        bookRepository.save(testBook2);
    }

    @Test
    void findAll() {
        List<Book> testBooks = bookRepository.findAll();

        assertNotNull(testBooks);
        assertEquals(testBooks.size(), 2);
    }
    @Test
    void findByBookId() {
        Book testBook = bookRepository.findByBookId(1);
        assertEquals(testBook.getTitle(), "Test Book");
        assertTrue(testBook.isAvailable());
    }

    @Test
    void findAllByTitleContainingIgnoreCase(){
        List<Book> possibleBooks = bookRepository.findAllByTitleContainingIgnoreCase("TeSt");
        assertNotNull(possibleBooks);
        assertTrue(possibleBooks.size()==2);
    }

    @Test
    void findAllByAvailable(){
        List<Book> availableBooks = bookRepository.findAllByAvailable(true);
        assertEquals(availableBooks.size(), 1);
        assertTrue(availableBooks.contains(testBook1));
        assertFalse(availableBooks.contains(testBook2));
    }

    @Test
    void findAllByReserved(){
        List<Book> reservedBooks = bookRepository.findAllByReserved(true);
        assertEquals(reservedBooks.size(), 2);
        assertTrue(reservedBooks.contains(testBook2));

    }

}