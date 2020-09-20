package se.lexicon.martinklasson.booklender.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryUserDtoTest {

    LibraryUserDto testObject = new LibraryUserDto();

    @BeforeEach
    void setUp() {
        testObject.setUserId(11);
        testObject.setRegDate(LocalDate.parse("2020-09-10"));
        testObject.setName("Martin");
        testObject.setEmail("test@test.se");
    }

    @Test
    void getUserId() {
        assertEquals(11, testObject.getUserId());
    }

    @Test
    void setUserId(){
        testObject.setUserId(20);
        assertEquals(20, testObject.getUserId());

    }

    @Test
    void getRegDate() {
        assertEquals(LocalDate.parse("2020-09-10"), testObject.getRegDate());
    }

    @Test
    void setRegDate() {
        testObject.setRegDate(LocalDate.parse("2020-09-20"));
        assertEquals(LocalDate.parse("2020-09-20"), testObject.getRegDate());
    }

    @Test
    void getName() {
        assertEquals("Martin", testObject.getName());
    }

    @Test
    void setName() {
        testObject.setName("Anders");
        assertEquals("Anders", testObject.getName());
    }

    @Test
    void getEmail() {
        assertEquals("test@test.se", testObject.getEmail());
    }

    @Test
    void setEmail() {
        testObject.setEmail("test1@test.com");
        assertEquals("test1@test.com", testObject.getEmail());
    }
}