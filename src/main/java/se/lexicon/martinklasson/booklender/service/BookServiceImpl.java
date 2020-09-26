package se.lexicon.martinklasson.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.martinklasson.booklender.dto.BookDto;
import se.lexicon.martinklasson.booklender.entity.Book;
import se.lexicon.martinklasson.booklender.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Configurable
public class BookServiceImpl implements BookService{

    BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    protected BookDto getBookDto(Book book){
        return new BookDto(
                book.getBookId(), book.getTitle(), book.isAvailable(), book.isReserved(),
                book.getMaxloanDays(), book.getFinePerDay(), book.getDescription());
    }

    protected Book getBook(BookDto bookDto){
        Book book = new Book(bookDto.getTitle(), bookDto.getMaxLoanDays(), bookDto.getFinePerDay(), bookDto.getDescription());
        return book;
    }

    protected List<BookDto> getBookDtoList(List<Book> bookList){
        List<BookDto> resultList = new ArrayList<>();
        for(Book book : bookList){
            BookDto bookDto = getBookDto(book);
            resultList.add(bookDto);
        }
        return resultList;
    }

    @Override
    public List<BookDto> findByReserved(boolean isReserved) {
        List<Book> foundBooks = bookRepository.findAllByReserved(isReserved);
        return getBookDtoList(foundBooks);
    }

    @Override
    public List<BookDto> findByAvailable(boolean isAvailable) {
        List<Book> foundBooks = bookRepository.findAllByAvailable(isAvailable);
        return getBookDtoList(foundBooks);
    }

    @Override
    public List<BookDto> findByTitle(String bookTitle) throws IllegalArgumentException{
        if(bookTitle == null || bookTitle.equals("")){
            throw new IllegalArgumentException("Title should not be empty.");
        }
        List<Book> foundBooks = bookRepository.findAllByTitleContainingIgnoreCase(bookTitle);
        return getBookDtoList(foundBooks);
    }

    @Override
    public BookDto findById(int bookId) throws IllegalArgumentException {
        Book book = bookRepository.findByBookId(bookId);
        return getBookDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        List<Book> foundbooks = bookRepository.findAll();
        return getBookDtoList(foundbooks);
    }


    @Override
    @Transactional
    public BookDto create(BookDto bookDto) throws RuntimeException {
//        if(bookRepository.findByBookId(bookDto.getBookId()).isAvailable()) {////isAvailable är inte bra!!! (isPresent????)
//            throw new RuntimeException("Book exists already, update instead");
//        }
        Book book = new Book(bookDto.getTitle(), bookDto.getMaxLoanDays(), bookDto.getFinePerDay(), bookDto.getDescription());
        book.setAvailable((bookDto.isAvailable()));
        book.setReserved(bookDto.isReserved());

        return getBookDto(bookRepository.save(book));
    }


    @Override
    @Transactional
    public BookDto update(BookDto bookDto) throws RuntimeException {
        Optional<Book> optionalBook = bookRepository.findById(bookDto.getBookId());
        if (!optionalBook.isPresent())
            throw new RuntimeException("Book does not exist, please create first");
        Book toUpdated = optionalBook.get();
        if (!toUpdated.getTitle().equals( bookDto.getTitle()))
            toUpdated.setTitle(bookDto.getTitle());
        if (toUpdated.isAvailable() != bookDto.isAvailable())
            toUpdated.setAvailable(bookDto.isAvailable());
        if (toUpdated.isReserved() != bookDto.isReserved())
            toUpdated.setReserved(bookDto.isReserved());
        if (toUpdated.getMaxloanDays()!= bookDto.getMaxLoanDays())
            toUpdated.setMaxloanDays(bookDto.getMaxLoanDays());
        if (!toUpdated.getFinePerDay().equals(bookDto.getFinePerDay()))
            toUpdated.setFinePerDay(bookDto.getFinePerDay());
        if (!toUpdated.getDescription().equals(bookDto.getDescription()))
            toUpdated.setDescription(bookDto.getDescription());

        return getBookDto(bookRepository.save(toUpdated));
    }
//        if(!bookRepository.existsById(bookDto.getBookId())){
//            throw new RuntimeException("It is not possible to update book. Create instead.");
//        }
//        Book updatedBook = bookRepository.findByBookId(bookDto.getBookId());
//        updatedBook.setTitle(bookDto.getTitle());
//        updatedBook.setAvailable(bookDto.isAvailable());
//        updatedBook.setReserved(bookDto.isReserved());
//        updatedBook.setFinePerDay(bookDto.getFinePerDay());
//        updatedBook.setDescription(bookDto.getDescription());
//
//        return getBookDto(bookRepository.save(updatedBook));
//    }

    @Override
    @Transactional
    public boolean delete(int bookId) throws IllegalArgumentException {
        boolean deleted = false;
        if(bookRepository.existsById(bookId)){
            bookRepository.delete(bookRepository.findByBookId(bookId));
            deleted = true;
        }
        return deleted;
    }
}
