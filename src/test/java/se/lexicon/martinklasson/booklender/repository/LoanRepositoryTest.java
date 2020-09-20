package se.lexicon.martinklasson.booklender.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.martinklasson.booklender.entity.Book;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;
import se.lexicon.martinklasson.booklender.entity.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LoanRepositoryTest {

    Book testBook1;
    Book testBook2;
    Book testBook3;

    LibraryUser testLibraryUser1;
    LibraryUser testLibraryUser2;

    Loan testLoan1;
    Loan testLoan2;
    Loan testLoan3;

    @Autowired
    LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        testBook1 = new Book("Test Book", 30, new BigDecimal(10), "Test Book 1 Description");
        testBook2 = new Book("Test Book2", 20, new BigDecimal(10), "Test Book 2 description");
        testBook3 = new Book("Test Book 3", 20, new BigDecimal(10),"Test Book Description");
        testBook1.setAvailable(true);
        testBook1.setReserved(true);
        testBook2.setAvailable(false);
        testBook2.setReserved(true);

        testLibraryUser1 = new LibraryUser(LocalDate.parse("2020-01-10"), "Martin", "test@test.se");
        testLibraryUser2 = new LibraryUser(LocalDate.parse("2019-08-08"), "Anders", "anders@test.com");

        testLoan1 = new Loan(testLibraryUser1, testBook1, LocalDate.now(), false);
        testLoan2 = new Loan(testLibraryUser2, testBook2, LocalDate.parse("2020-08-01"), true);
        testLoan3 = new Loan(testLibraryUser2, testBook3, LocalDate.now(), false);

        loanRepository.save(testLoan1);
        loanRepository.save(testLoan2);
        loanRepository.save(testLoan3);

    }

    @Test
    void findAll() {
        List<Loan> bookLoans = loanRepository.findAll();
        assertTrue(bookLoans.size() == 3);
        assertTrue(bookLoans.contains(testLoan1));
    }

    @Test
    void findAllByLoanTaker_UserId() {
        List<Loan> foundLoans1 = loanRepository.findAllByLoanTaker_UserId(testLibraryUser1.getUserId());
        List<Loan> foundLoans2 = loanRepository.findAllByLoanTaker_UserId(testLibraryUser2.getUserId());

        assertNotNull(foundLoans1);
        assertTrue(foundLoans2.size() == 2);
        assertEquals(foundLoans1.get(0).getLoanTaker().getName(), "Martin");
    }

    @Test
    void findAllByBook_BookId() {
        List<Loan> foundLoans1 = loanRepository.findAllByBook_BookId(testBook1.getBookId());
        List<Loan> foundLoans2 = loanRepository.findAllByBook_BookId(testBook2.getBookId());

        assertNotNull(foundLoans1);
        assertTrue(foundLoans2.size() == 1);
    }

    @Test
    void findAllByExpired() {
        List<Loan> foundLoans1 = loanRepository.findAllByExpired(true);
        List<Loan> foundLoans2 = loanRepository.findAllByExpired(false);

        assertTrue(foundLoans1.size() == 1);
        assertTrue(foundLoans2.size() == 2);
        assertEquals(foundLoans1.get(0).getLoanTaker().getName(), "Anders" );
    }
}