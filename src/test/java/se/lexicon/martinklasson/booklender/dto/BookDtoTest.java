package se.lexicon.martinklasson.booklender.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {

    BookDto testObject = new BookDto();

    @BeforeEach
    void setUp(){
        testObject.setBookId(10);
        testObject.setTitle("Martin's Test Book");
        testObject.setMaxLoanDays(30);
        testObject.setFinePerDay(BigDecimal.valueOf(10));
        testObject.setAvailable(false);
        testObject.setReserved(true);
        testObject.setDescription("Best book ever");
    }

    @Test
    void successfullyCreated(){
        assertNotNull(testObject);
    }

    @Test
    void getBookId() {
        assertEquals(10, testObject.getBookId());
    }

    @Test
    void setBookId(){
        testObject.setBookId(15);
        assertEquals(15, testObject.getBookId());
    }

    @Test
    void getTitle() {
        assertEquals("Martin's Test Book", testObject.getTitle());
    }

    @Test
    void setTitle() {
        testObject.setTitle("Test");
        assertEquals("Test", testObject.getTitle());
    }

    @Test
    void isAvailable() {
        assertFalse(testObject.isAvailable());
    }

    @Test
    void setAvailable() {
        testObject.setAvailable(true);
        assertTrue(testObject.isAvailable());
    }

    @Test
    void isReserved() {
        assertTrue(testObject.isReserved());
    }

    @Test
    void setReserved() {
        testObject.setReserved(false);
        assertFalse(testObject.isReserved());
    }

    @Test
    void getMaxLoanDays() {
        assertEquals(30, testObject.getMaxLoanDays());
    }

    @Test
    void setMaxLoanDays() {
        testObject.setMaxLoanDays(35);
        assertEquals(35, testObject.getMaxLoanDays());
    }

    @Test
    void getFinePerDay() {
        assertEquals(BigDecimal.valueOf(10), testObject.getFinePerDay());
    }

    @Test
    void setFinePerDay() {
        testObject.setFinePerDay(BigDecimal.valueOf(15));
        assertEquals(BigDecimal.valueOf(15), testObject.getFinePerDay());
    }

    @Test
    void getDescription() {
        assertEquals("Best book ever", testObject.getDescription());
    }

    @Test
    void setDescription() {
        testObject.setDescription("Good book");
        assertEquals("Good book", testObject.getDescription());
    }
}