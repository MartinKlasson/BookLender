package se.lexicon.martinklasson.booklender.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;

import java.util.List;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, Integer> {

    List<LibraryUser> findAll();
    LibraryUser findByUserId(int userId);
    LibraryUser findByEmailIgnoreCase(String email);

}
