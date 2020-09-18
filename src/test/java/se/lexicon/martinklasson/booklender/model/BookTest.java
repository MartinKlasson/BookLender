package se.lexicon.martinklasson.booklender.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.martinklasson.booklender.entity.Book;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {

    private Book testBook;

    @BeforeEach
    void setUp(){
        testBook = new Book(
                "Test Book",
                30,
                new BigDecimal(10),
                "Test Description"
        );
    }

    @Test
    void getTitle(){
        String expected = "Test Book";
        assertEquals(expected, testBook.getTitle());
    }


}
