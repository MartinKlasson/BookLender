package se.lexicon.martinklasson.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.martinklasson.booklender.dto.BookDto;
import se.lexicon.martinklasson.booklender.dto.LibraryUserDto;
import se.lexicon.martinklasson.booklender.dto.LoanDto;
import se.lexicon.martinklasson.booklender.entity.Book;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;
import se.lexicon.martinklasson.booklender.entity.Loan;
import se.lexicon.martinklasson.booklender.repository.BookRepository;
import se.lexicon.martinklasson.booklender.repository.LibraryUserRepository;
import se.lexicon.martinklasson.booklender.repository.LoanRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LoanServiceImplTest {

    LoanServiceImpl testObject;
    LibraryUserServiceImpl libraryUserService;
    BookServiceImpl bookService;

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    LibraryUserRepository libraryUserRepository;

    Loan loan1;
    Loan loan2;
    LoanDto loanDto1;
    LoanDto loanDto2;

    Book book1;
    Book book2;
    BookDto bookDto1;
    BookDto bookDto2;

    LibraryUser libraryUser1;
    LibraryUser libraryUser2;
    LibraryUserDto libraryUserDto1;
    LibraryUserDto libraryUserDto2;

    @BeforeEach
    void setUp() {
        testObject = new LoanServiceImpl(loanRepository, bookRepository, libraryUserRepository);
        bookService = new BookServiceImpl(bookRepository);
        libraryUserService = new LibraryUserServiceImpl(libraryUserRepository);

        book1 = new Book("Test Book 1", 30, BigDecimal.valueOf(10), "A Test Book");
        book1 = bookRepository.save(book1);
        bookDto1 = bookService.getBookDto(book1);

        book2 = new Book("Test Book 2", 30, BigDecimal.valueOf(10), "Another Test Book");
        book2 = bookRepository.save(book2);
        bookDto2 = bookService.getBookDto(book2);

        libraryUser1 = new LibraryUser(LocalDate.parse("2020-08-22"), "Martin", "test@test.com");
        libraryUser1 = libraryUserRepository.save(libraryUser1);
        libraryUserDto1 = libraryUserService.getLibraryUserDto(libraryUser1);

        libraryUser2 = new LibraryUser(LocalDate.parse("2020-03-31"), "Mats", "mats@mats.com");
        libraryUser2 = libraryUserRepository.save(libraryUser2);
        libraryUserDto2 = libraryUserService.getLibraryUserDto(libraryUser2);

        loan1 = new Loan(libraryUser1, book1, LocalDate.parse("2020-08-10"), true);
        loan1 = loanRepository.save(loan1);
        loanDto1 = testObject.getLoanDto(loan1);

        loan2 = new Loan(libraryUser2, book2, LocalDate.parse("2020-09-10"), false);
        loan2 = loanRepository.save(loan2);
        loanDto2 = testObject.getLoanDto(loan2);

    }

    @Test
    void successfullyCreated(){
        assertNotNull(book1);
        assertNotNull(book2);
        assertNotNull(bookDto1);
        assertNotNull(bookDto2);
        assertNotNull(libraryUser1);
        assertNotNull(libraryUser2);
        assertNotNull(libraryUserDto1);
        assertNotNull(libraryUserDto2);
        assertNotNull(loan1);
        assertNotNull(loan2);
        assertNotNull(loanDto1);
        assertNotNull(loanDto2);
    }

    @Test
    void findById() {
        assertEquals(loanDto1, testObject.findById(loanDto1.getLoanId()));
        assertEquals(loanDto2, testObject.findById(loanDto2.getLoanId()));
    }

    @Test
    void findByBookId() {
        assertEquals(1,testObject.findByBookId(bookDto1.getBookId()).size());
        assertEquals(1, testObject.findByBookId(bookDto2.getBookId()).size());

        assertTrue(testObject.findByBookId(bookDto1.getBookId()).contains(loanDto1));
        assertFalse(testObject.findByBookId(bookDto2.getBookId()).contains(loanDto1));
    }

    @Test
    void findByUserId() {
        assertEquals(1, testObject.findByUserId(loanDto1.getLoanTaker().getUserId()).size());
        assertTrue(testObject.findByUserId(loanDto2.getLoanTaker().getUserId()).contains(loanDto2));
        assertFalse(testObject.findByUserId(loanDto1.getLoanTaker().getUserId()).contains(loanDto2));
    }

    @Test
    void findByExpired() {
        assertFalse(testObject.findByExpired(false).contains(loanDto1));
        assertTrue(testObject.findByExpired(true).contains(loanDto1));
        assertEquals(1, testObject.findByExpired(false).size());
    }

    @Test
    void findAll() {
        assertEquals(2, testObject.findAll().size());
    }

    @Test
    void create() {
        LoanDto loanDto3 = new LoanDto();
        loanDto3.setLoanTaker(libraryUserDto1);
        loanDto3.setBook(bookDto1);
        loanDto3.setLoanDate(LocalDate.now());
        loanDto3.setExpired(false);

        loanDto3 = testObject.create(loanDto3);

        assertEquals(3, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(loanDto3));
    }

    @Test
    void update() {
        loanDto2.setLoanDate(LocalDate.parse("2020-09-22"));
        loanDto2 = testObject.update(loanDto2);
        assertEquals(LocalDate.parse("2020-09-22"), loanDto2.getLoanDate());

        loanDto1.setBook(testObject.getBookDto(book2));
        loanDto1 = testObject.update(loanDto1);
        assertEquals(bookDto2, loanDto1.getBook());

        loanDto1.setLoanTaker(libraryUserDto2);
        loanDto1 = testObject.update(loanDto1);
        assertEquals(libraryUserDto2, loanDto1.getLoanTaker());
    }

    @Test
    void delete() {
        testObject.delete(loanDto1.getBook().getBookId());
        assertEquals(1, testObject.findAll().size());
        assertFalse(testObject.findAll().contains(loanDto1));
    }
}