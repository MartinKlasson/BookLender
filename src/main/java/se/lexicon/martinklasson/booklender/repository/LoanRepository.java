package se.lexicon.martinklasson.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.martinklasson.booklender.entity.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findAll();
    List<Loan> findAllByLoanTaker_UserId(int loanTakerId);
    List<Loan> findAllByBook_BookId(Integer bookId);
    List<Loan> findAllByExpired(boolean isExpired);

}
