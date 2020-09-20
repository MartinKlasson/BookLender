package se.lexicon.martinklasson.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.martinklasson.booklender.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> findAll();
    Book findByBookId(int bookId);
    List<Book> findAllByTitleContainingIgnoreCase(String bookTitle);
    List<Book> findAllByReserved(boolean reserved);
    List<Book> findAllByAvailable(boolean available);

}
