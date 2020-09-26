package se.lexicon.martinklasson.booklender.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.martinklasson.booklender.dto.LibraryUserDto;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;
import se.lexicon.martinklasson.booklender.repository.LibraryUserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LibraryUserServiceImplTest {
    LibraryUserServiceImpl testObject;
    LibraryUserDto libraryUserDto1;
    LibraryUserDto libraryUserDto2;

    LibraryUser libraryUser1;
    LibraryUser libraryUser2;

    @Autowired
    LibraryUserRepository libraryUserRepository;

    @BeforeEach
    void setUp() {
        testObject = new LibraryUserServiceImpl(libraryUserRepository);

        libraryUser1 = new LibraryUser(LocalDate.parse("2020-01-04"), "Martin", "test@test.com");
        libraryUserRepository.save(libraryUser1);
        libraryUserDto1 = testObject.getLibraryUserDto(libraryUser1);

        libraryUser2 = new LibraryUser(LocalDate.parse("2020-08-04"), "Anders", "anders@test.com");
        libraryUserRepository.save(libraryUser2);
        libraryUserDto2 = testObject.getLibraryUserDto(libraryUser2);

    }

    @Test
    void successfullyAdded(){
        assertNotNull(libraryUser1);
        assertNotNull(libraryUser2);
        assertNotNull(libraryUserDto1);
        assertNotNull(libraryUserDto2);
    }

    @Test
    void findById() {
        assertEquals(libraryUserDto1, testObject.findById(libraryUser1.getUserId()));
        assertEquals(libraryUserDto2, testObject.findById(libraryUser2.getUserId()));
        assertNotEquals(testObject.findById(libraryUser1.getUserId()), testObject.findById(libraryUser2.getUserId()));
    }

    @Test
    void findByEmail() {
        String email1 = "test@test.com";
        String email2 = "anders@test.com";
        assertEquals(libraryUserDto1, testObject.findByEmail(email1));
        assertEquals(libraryUserDto2, testObject.findByEmail(email2));

    }

    @Test
    void findAll() {
        assertEquals(2, testObject.findAll().size());
        assertTrue(testObject.findAll().contains(libraryUserDto1));
        assertTrue(testObject.findAll().contains(libraryUserDto2));

    }

    @Test
    void create() {
        LibraryUserDto libraryUserDto3 = new LibraryUserDto();
        libraryUserDto3.setRegDate(LocalDate.parse("2020-09-11"));
        libraryUserDto3.setName("Kalle");
        libraryUserDto3.setEmail("kalle@test.se");

        libraryUserDto3 = testObject.create(libraryUserDto3);
        assertNotNull(libraryUserDto3);
        assertEquals(3, testObject.findAll().size());
        assertEquals(libraryUserDto3, testObject.findByEmail(libraryUserDto3.getEmail()));
        assertEquals(libraryUserDto3, testObject.findById(libraryUserDto3.getUserId()));
    }

    @Test
    void update() {
        libraryUserDto1.setEmail("stina@test.com");
        testObject.update(libraryUserDto1);
        assertEquals("Martin", testObject.findById(libraryUserDto1.getUserId()).getName());
        assertEquals("stina@test.com", testObject.findById(libraryUserDto1.getUserId()).getEmail());
        assertEquals(2, testObject.findAll().size());
    }

    @Test
    void delete() {
        assertTrue(testObject.delete(libraryUserDto1.getUserId()));
        assertEquals(1,testObject.findAll().size());
        assertFalse(testObject.findAll().contains(libraryUserDto1));
        assertTrue(testObject.findAll().contains(libraryUserDto2));
    }
}