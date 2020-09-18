package se.lexicon.martinklasson.booklender.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.martinklasson.booklender.entity.Book;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;
import se.lexicon.martinklasson.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    private LibraryUser libraryUser;
    private Book book;
    private Book loanedBook;

    private Loan testLoan1;
    private Loan testLoan2;

    @BeforeEach
    void setUp(){
        libraryUser = new LibraryUser(LocalDate.parse("2020-01-04"), "Test Klasson", "test@yahoo.se");
        book = new Book("Test Book 1", 30, new BigDecimal(10), "This is test book 1");
        loanedBook = new Book("Test Book 2", 10, new BigDecimal(10), "This is test book 2");
        loanedBook.setReserved(true);
        testLoan1 = new Loan(libraryUser, loanedBook, LocalDate.now().minusDays(5), false);
        testLoan2 = new Loan(libraryUser, loanedBook, LocalDate.now().minusDays(12), true);
    }

    @Test
    void getLibraryUser(){
        LibraryUser expected = libraryUser;
        assertEquals(expected, testLoan1.getLoanTaker());
    }

    @Test
    void isOverdue(){
        assertFalse(testLoan1.isOverdue());
        assertTrue(testLoan2.isOverdue());
    }

    @Test
    void getFine(){
        BigDecimal expectedFine = new BigDecimal(20);
        assertEquals(expectedFine, testLoan2.getFine());
    }

    @Test
    void extendedLoan(){
        boolean extended = true;
        assertFalse(testLoan1.extendedLoan(5));
    }

}
