package se.lexicon.martinklasson.booklender.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;

public class LibraryUserDto {

    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    private int userId;
    private LocalDate regDate;
    @NotBlank(message = "Name has to be added")
    private String name;
    @Email(regexp = EMAIL_PATTERN)
    private String email;

    public LibraryUserDto() {
    }

    public LibraryUserDto(int userId, LocalDate regDate, String name, String email) {
        this.userId = userId;
        this.regDate = regDate;
        this.name = name;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUserDto that = (LibraryUserDto) o;
        return userId == that.userId &&
                Objects.equals(regDate, that.regDate) &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, regDate, name, email);
    }

    @Override
    public String toString() {
        return "LibraryUserDto{" +
                "userId=" + userId +
                ", regDate=" + regDate +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
