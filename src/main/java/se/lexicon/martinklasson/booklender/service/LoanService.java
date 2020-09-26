package se.lexicon.martinklasson.booklender.service;

import se.lexicon.martinklasson.booklender.dto.LoanDto;
import se.lexicon.martinklasson.booklender.entity.Loan;

import java.util.List;

public interface LoanService {

    LoanDto findById(long loanId);
    List<LoanDto> findByBookId(int bookId);
    List<LoanDto> findByUserId(int userId);
    List<LoanDto> findByExpired(boolean isExpired);
    List<LoanDto> findAll();
    LoanDto create(LoanDto loanDto);
    LoanDto update(LoanDto loanDto);
    boolean delete(int bookId);

}
