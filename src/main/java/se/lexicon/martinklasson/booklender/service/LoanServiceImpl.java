package se.lexicon.martinklasson.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.martinklasson.booklender.dto.BookDto;
import se.lexicon.martinklasson.booklender.dto.LoanDto;
import se.lexicon.martinklasson.booklender.entity.Loan;
import se.lexicon.martinklasson.booklender.repository.BookRepository;
import se.lexicon.martinklasson.booklender.repository.LibraryUserRepository;
import se.lexicon.martinklasson.booklender.repository.LoanRepository;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;
import se.lexicon.martinklasson.booklender.entity.Book;
import se.lexicon.martinklasson.booklender.dto.LibraryUserDto;

import java.util.ArrayList;
import java.util.List;


@Service
@Configurable
public class LoanServiceImpl implements LoanService{

    LoanRepository loanRepository;
    BookRepository bookRepository;
    LibraryUserRepository libraryUserRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository, LibraryUserRepository libraryUserRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.libraryUserRepository = libraryUserRepository;
    }

    public LibraryUserDto getLibraryUserDto(LibraryUser libraryUser){
        LibraryUserDto libraryUserDto = new LibraryUserDto();
        libraryUserDto.setUserId(libraryUser.getUserId());
        libraryUserDto.setRegDate(libraryUser.getRegDate());
        libraryUserDto.setName(libraryUser.getName());
        libraryUserDto.setEmail(libraryUser.getEmail());
        return libraryUserDto;
    }

    @Transactional
    public LibraryUser getLibraryUser(LibraryUserDto libraryUserDto){
        return libraryUserRepository.findByUserId(libraryUserDto.getUserId());
    }

    public BookDto getBookDto(Book book){
        BookDto bookDto = new BookDto();
        bookDto.setBookId(book.getBookId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAvailable(book.isAvailable());
        bookDto.setReserved(book.isReserved());
        bookDto.setMaxLoanDays(book.getMaxloanDays());
        bookDto.setFinePerDay(book.getFinePerDay());
        bookDto.setDescription(book.getDescription());
        return bookDto;
    }

    @Transactional
    public Book getBook(BookDto bookDto){
        return bookRepository.findByBookId(bookDto.getBookId());//or else argument?????
    }

    protected LoanDto getLoanDto(Loan loan){
        LoanDto loanDto = new LoanDto();
        loanDto.setLoanId(loan.getLoanId());
        loanDto.setLoanTaker(getLibraryUserDto(loan.getLoanTaker()));
        loanDto.setBook(getBookDto(loan.getBook()));
        loanDto.setLoanDate(loan.getLoanDate());
        loanDto.setExpired(loan.isExpired());
        return loanDto;
    }

    protected List<LoanDto> getLoanDtoList(List<Loan> loanList){
        List<LoanDto> resultList = new ArrayList<>();
        for (Loan loan : loanList){
            LoanDto loanDto = getLoanDto(loan);
            resultList.add(loanDto);
        }
        return resultList;
    }

    @Override
    public LoanDto findById(long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Cannot find loan with that id!"));
        return getLoanDto(loan);
    }

    @Override
    public List<LoanDto> findByBookId(int bookId) {
        List<Loan> foundLoans = loanRepository.findAllByBook_BookId(bookId);
        return getLoanDtoList(foundLoans);
    }

    @Override
    public List<LoanDto> findByUserId(int userId) {
        List<Loan> foundLoans = loanRepository.findAllByLoanTaker_UserId(userId);
        return getLoanDtoList(foundLoans);
    }

    @Override
    public List<LoanDto> findByExpired(boolean isExpired) {
        List<Loan> foundLoans = loanRepository.findAllByExpired(isExpired);
        return getLoanDtoList(foundLoans);
    }

    @Override
    public List<LoanDto> findAll() {
        List<Loan> foundLoans = loanRepository.findAll();
        return getLoanDtoList(foundLoans);
    }

    @Override
    @Transactional
    public LoanDto create(LoanDto loanDto) {
        //throw exception om loan id redan finns
        Loan loan = new Loan(getLibraryUser(loanDto.getLoanTaker()),
                    getBook(loanDto.getBook()),
                    loanDto.getLoanDate(),
                    loanDto.isExpired());

        return getLoanDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public LoanDto update(LoanDto loanDto) {
        //throw exception om inte lån med valt lån id finns, välja create istället
        Loan loan = loanRepository.findById(loanDto.getLoanId()).get();
        if(!loan.getLoanTaker().equals(getLibraryUser(loanDto.getLoanTaker()))){
            loan.setLoanTaker(getLibraryUser(loanDto.getLoanTaker()));
        }
        if(!loan.getBook().equals(getBook(loanDto.getBook()))){
            loan.setBook(getBook(loanDto.getBook()));
        }
        if(loan.getLoanDate() != loanDto.getLoanDate()){
            loan.setLoanDate(loanDto.getLoanDate());
        }
        if(loan.isOverdue() != loanDto.isExpired()){
            loan.setExpired(loanDto.isExpired());
        }
        return getLoanDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public boolean delete(int bookId) throws IllegalArgumentException {
        boolean deleted = false;
        if(loanRepository.findAllByBook_BookId(bookId).isEmpty()){
            throw new IllegalArgumentException("There are no loans of book with that id!");
        }else{
            List<Loan> foundLoans = loanRepository.findAllByBook_BookId(bookId);
            for(Loan loan : foundLoans){
                loanRepository.delete(loan);
                deleted = true;
            }
        }
        return deleted;
    }
}
