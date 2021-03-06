package se.lexicon.martinklasson.booklender.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class LibraryUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private LocalDate regDate;
    private String name;

    @Email
    @Column(unique = true) //Behövs namn på column? ex: name=email, unique=true?????????????????????
    private String email;

    public LibraryUser() {
    }

    public LibraryUser(LocalDate regDate, String name, String email) {
        this.regDate = regDate;
        this.name = name;
        this.email = email;
    }

    //If user is present it has an id > 0...
    public boolean isPresent(){
        return userId > 0;
    }

    public int getUserId() {
        return userId;
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
        LibraryUser that = (LibraryUser) o;
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
        return "LibraryUser{" +
                "userId=" + userId +
                ", regDate=" + regDate +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
