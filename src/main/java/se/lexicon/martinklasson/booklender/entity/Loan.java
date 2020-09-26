package se.lexicon.martinklasson.booklender.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private LibraryUser loanTaker;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private Book book;

    private LocalDate loanDate;
    private boolean expired;

    public Loan() {
    }

    public Loan(LibraryUser loanTaker, Book book, LocalDate loanDate, boolean expired) {
        this.loanTaker = loanTaker;
        this.book = book;
        this.loanDate = loanDate;
        this.expired = expired;
    }

    public int getLoanDays(LocalDate loanDate, LocalDate date){
        return (int)DAYS.between(loanDate, date);
    }

    public boolean isOverdue(){
        LocalDate now = LocalDate.now();
        int loanDays = getLoanDays(loanDate, now);
        return loanDays > book.getMaxloanDays();
    }

    public BigDecimal getFine(){
        BigDecimal fine = new BigDecimal(0);
        int daysOverdue = getLoanDays(loanDate, LocalDate.now()) - book.getMaxloanDays();

        if(isOverdue()){
            //System.out.println("In getFine, isOverdue");
            fine = BigDecimal.valueOf(daysOverdue).multiply(book.getFinePerDay());
        }
        return fine;
    }
    //This changes the MaxLoanDays in Book class... is it ok??????
    public boolean extendedLoan(int days){
        if(book.isReserved() || isOverdue()){
            return false;
        }
        book.setMaxloanDays(book.getMaxloanDays()+days);
        return true;
    }

    public long getLoanId() {
        return loanId;
    }

    public LibraryUser getLoanTaker() {
        return loanTaker;
    }

    public void setLoanTaker(LibraryUser loanTaker) {
        this.loanTaker = loanTaker;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
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

    public void setExpired(boolean terminated) {
        this.expired = terminated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loanId == loan.loanId &&
                expired == loan.expired &&
                Objects.equals(loanTaker, loan.loanTaker) &&
                Objects.equals(book, loan.book) &&
                Objects.equals(loanDate, loan.loanDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanId, loanTaker, book, loanDate, expired);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", loanTaker=" + loanTaker +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", terminated=" + expired +
                '}';
    }
}
