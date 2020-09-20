package se.lexicon.martinklasson.booklender.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanDtoTest {

    LoanDto testObject = new LoanDto();
    LibraryUserDto testLoanTaker = new LibraryUserDto();
    BookDto testBook = new BookDto();

    @BeforeEach
    void setUp() {
        testLoanTaker.setUserId(11);
        testLoanTaker.setRegDate(LocalDate.parse("2020-09-10"));
        testLoanTaker.setName("Martin");
        testLoanTaker.setEmail("test@test.se");

        testBook.setBookId(1);
        testBook.setTitle("Book");
        testBook.setMaxLoanDays(30);
        testBook.setFinePerDay(BigDecimal.valueOf(10));
        testBook.setReserved(true);
        testBook.setAvailable(false);
        testBook.setDescription("Description");

        int id = 100;
        Long longId = new Long(id);
        testObject.setLoanId(longId);
        testObject.setLoanTaker(testLoanTaker);
        testObject.setBook(testBook);
        testObject.setLoanDate(LocalDate.parse("2020-09-09"));
        testObject.setExpired(false);
    }

    @Test
    void getLoanId() {
        assertEquals(100, testObject.getLoanId());
    }

    @Test
    void setLoanId(){
        Long loanId2 = new Long(200);
        testObject.setLoanId(loanId2);
        assertEquals(200, testObject.getLoanId());
    }

    @Test
    void getLoanTaker() {
        assertEquals("Martin", testObject.getLoanTaker().getName());
    }

    @Test
    void setLoanTaker() { //behövs den?????????????????????????????????
    }

    @Test
    void getBook() {
        assertEquals("Book", testObject.getBook().getTitle());
    }

    @Test
    void setBook() {
    }

    @Test
    void getLoanDate() {
        assertEquals(LocalDate.parse("2020-09-09"), testObject.getLoanDate());
    }

    @Test
    void setLoanDate() {
        testObject.setLoanDate(LocalDate.parse("2020-09-10"));
        assertEquals(LocalDate.parse("2020-09-10"), testObject.getLoanDate());
    }

    @Test
    void isExpired() {
        assertFalse(testObject.isExpired());
    }

    @Test
    void setExpired() {
        testObject.setExpired(true);
        assertTrue(testObject.isExpired());
    }
}