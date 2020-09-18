package se.lexicon.martinklasson.booklender.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryUserTest {

    LibraryUser testUser;

    @BeforeEach
    void setUp(){
        testUser = new LibraryUser(LocalDate.parse("2020-05-06"), "Anders Testsson", "anders@test.email");
    }

    @Test
    void getUserName(){
        String expected = "Anders Testsson";
        assertEquals(expected, testUser.getName());
    }

}
