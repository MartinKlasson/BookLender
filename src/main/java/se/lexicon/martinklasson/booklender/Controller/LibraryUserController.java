package se.lexicon.martinklasson.booklender.Controller;


import org.springframework.http.ResponseEntity;
import se.lexicon.martinklasson.booklender.dto.LibraryUserDto;

import java.util.List;

public interface LibraryUserController {

    ResponseEntity<LibraryUserDto> findById(int libraryUserId);
    ResponseEntity<LibraryUserDto> findByEmail(String email);
    ResponseEntity<List<LibraryUserDto>> findAll();
    ResponseEntity<LibraryUserDto> create(LibraryUserDto libraryUserDto);
    ResponseEntity<LibraryUserDto> update(LibraryUserDto libraryUserDto);

}
