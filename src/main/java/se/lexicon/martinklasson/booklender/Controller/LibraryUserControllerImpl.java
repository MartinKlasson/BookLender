package se.lexicon.martinklasson.booklender.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.martinklasson.booklender.dto.LibraryUserDto;
import se.lexicon.martinklasson.booklender.service.LibraryUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/api/users")
public class LibraryUserControllerImpl implements LibraryUserController {

    LibraryUserService libraryUserService;

    @Autowired
    public LibraryUserControllerImpl(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    @Override
    @GetMapping(path = "/{libraryUserId}")
    public ResponseEntity<LibraryUserDto> findById(@PathVariable int libraryUserId) {
        return ResponseEntity.ok(libraryUserService.findById(libraryUserId));
    }

    @Override
    @GetMapping(path = "/email/{libraryUserEmail}")
    public ResponseEntity<LibraryUserDto> findByEmail(@Valid @PathVariable("libraryUserEmail") String libraryUserEmail) {
        return ResponseEntity.ok(libraryUserService.findByEmail(libraryUserEmail));
    }

    @Override
    @GetMapping(path = "/all")
    public ResponseEntity<List<LibraryUserDto>> findAll() {
        return ResponseEntity.ok(libraryUserService.findAll());
    }

    @Override
    @PostMapping
    public ResponseEntity<LibraryUserDto> create(@Valid @RequestBody LibraryUserDto libraryUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryUserService.create(libraryUserDto));
    }

    @Override
    @PutMapping
    public ResponseEntity<LibraryUserDto> update(@RequestBody LibraryUserDto libraryUserDto) {
        return ResponseEntity.ok(libraryUserService.update(libraryUserDto));
    }
}
