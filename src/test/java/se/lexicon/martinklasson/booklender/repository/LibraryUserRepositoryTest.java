package se.lexicon.martinklasson.booklender.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibraryUserRepositoryTest {

    LibraryUser libraryUser1;
    LibraryUser libraryUser2;

    @Autowired
    LibraryUserRepository libraryUserRepository;

    @BeforeEach
    void setUp() {
        libraryUser1 = new LibraryUser(LocalDate.parse("2020-01-10"), "Martin", "test@test.se");
        libraryUser2 = new LibraryUser(LocalDate.parse("2019-08-08"), "Anders", "anders@test.com");

        libraryUserRepository.save(libraryUser1);
        libraryUserRepository.save(libraryUser2);
    }
    @Test
    void findAll() {
        List<LibraryUser> testLibraryUsers = libraryUserRepository.findAll();

        assertNotNull(testLibraryUsers);
        assertEquals(testLibraryUsers.size(), 2);

    }
    @Test
    void findByUserId() {
    }
    @Test
    void findByEmailIgnoreCase() {
        LibraryUser foundUser1 = libraryUserRepository.findByEmailIgnoreCase("Test@test.se");
        LibraryUser foundUser2 = libraryUserRepository.findByEmailIgnoreCase("anders@test.com");

        assertNotNull(foundUser1);
        assertEquals(foundUser2, libraryUser2);
    }
}