package se.lexicon.martinklasson.booklender.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;

public class LoanDto {


    private long loanId;
    @NotNull(message = "Loan need a loan taker to be created.")
    private LibraryUserDto loanTaker;
    @NotNull(message = "Loan need a book to be created.")
    private BookDto book;
    @PastOrPresent(message = "Loan can't have a future date.")
    private LocalDate loanDate;
    private boolean expired;

    public LoanDto() {
    }

    public LoanDto(long loanId, LibraryUserDto loanTaker, BookDto book, LocalDate loanDate, boolean expired) {
        this.loanId = loanId;
        this.loanTaker = loanTaker;
        this.book = book;
        this.loanDate = loanDate;
        this.expired = expired;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public LibraryUserDto getLoanTaker() {
        return loanTaker;
    }

    public void setLoanTaker(LibraryUserDto loanTaker) {
        this.loanTaker = loanTaker;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDto loanDto = (LoanDto) o;
        return loanId == loanDto.loanId &&
                expired == loanDto.expired &&
                Objects.equals(loanTaker, loanDto.loanTaker) &&
                Objects.equals(book, loanDto.book) &&
                Objects.equals(loanDate, loanDto.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, loanTaker, book, loanDate, expired);
    }

    @Override
    public String toString() {
        return "LoanDto{" +
                "loanId=" + loanId +
                ", loanTaker=" + loanTaker +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", expired=" + expired +
                '}';
    }
}
