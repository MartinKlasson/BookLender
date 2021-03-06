package se.lexicon.martinklasson.booklender.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class BookDto {

    //Id generated automatically
    private int bookId;
    @NotBlank(message = "Title is compulsary")
    @Size(min = 1)
    private String title;
    private boolean available;
    private boolean reserved;
    @PositiveOrZero
    private int maxLoanDays;
    @PositiveOrZero
    private BigDecimal finePerDay;
    private String description;

    public BookDto() {
    }

    public BookDto(int bookId, String title, boolean available, boolean reserved, int maxLoanDays, BigDecimal finePerDay, String description) {
        this.bookId = bookId;
        this.title = title;
        this.available = available;
        this.reserved = reserved;
        this.maxLoanDays = maxLoanDays;
        this.finePerDay = finePerDay;
        this.description = description;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getMaxLoanDays() {
        return maxLoanDays;
    }

    public void setMaxLoanDays(int maxLoanDays) {
        this.maxLoanDays = maxLoanDays;
    }

    public BigDecimal getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(BigDecimal finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return bookId == bookDto.bookId &&
                available == bookDto.available &&
                reserved == bookDto.reserved &&
                maxLoanDays == bookDto.maxLoanDays &&
                Objects.equals(title, bookDto.title) &&
                Objects.equals(finePerDay, bookDto.finePerDay) &&
                Objects.equals(description, bookDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, available, reserved, maxLoanDays, finePerDay, description);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", available=" + available +
                ", reserved=" + reserved +
                ", maxLoanDays=" + maxLoanDays +
                ", finePerDay=" + finePerDay +
                ", description='" + description + '\'' +
                '}';
    }
}
