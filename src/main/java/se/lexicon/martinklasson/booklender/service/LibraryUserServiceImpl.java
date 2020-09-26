package se.lexicon.martinklasson.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.martinklasson.booklender.dto.LibraryUserDto;
import se.lexicon.martinklasson.booklender.entity.LibraryUser;
import se.lexicon.martinklasson.booklender.repository.LibraryUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
public class LibraryUserServiceImpl implements LibraryUserService {

    LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserServiceImpl(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    //Converts entity-LibraryUser to dto-LibraryUser
    public LibraryUserDto getLibraryUserDto(LibraryUser libraryUser){
        LibraryUserDto libraryUserDto = new LibraryUserDto();
        libraryUserDto.setUserId(libraryUser.getUserId());
        libraryUserDto.setRegDate(libraryUser.getRegDate());
        libraryUserDto.setName(libraryUser.getName());
        libraryUserDto.setEmail(libraryUser.getEmail());
        return libraryUserDto;
    }

    //Converts List<LibraryUser> to List<LibraryUserDto>
    protected List<LibraryUserDto> getLibraryUserDtoList(List<LibraryUser> libraryUsers){
        List<LibraryUserDto> resultList = new ArrayList<>();
        for(LibraryUser libraryUser : libraryUsers){
            LibraryUserDto libraryUserDto = getLibraryUserDto(libraryUser);
            resultList.add(libraryUserDto);
        }
        return resultList;
    }

    @Override
    public LibraryUserDto findById(int userId) throws IllegalArgumentException{
        if(!libraryUserRepository.existsById(userId)) throw new IllegalArgumentException("User id " + userId + " does not exist.");
        LibraryUser libraryUser = libraryUserRepository.findByUserId(userId);
        return getLibraryUserDto(libraryUser);
    }

    @Override
    public LibraryUserDto findByEmail(String email) throws IllegalArgumentException {
        if(email == null) {
            throw new IllegalArgumentException("Email should not be empty, fill in valid email address.");
        }
        if(libraryUserRepository.findByEmailIgnoreCase(email) == null) {
            throw new IllegalArgumentException("Did not find library user with email: " + email);
        }
        LibraryUser libraryUser = libraryUserRepository.findByEmailIgnoreCase(email);
        return getLibraryUserDto(libraryUser);

    }

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> foundLibraryUsers = libraryUserRepository.findAll();
        return getLibraryUserDtoList(foundLibraryUsers);
    }

    @Override
    @Transactional
    public LibraryUserDto create(LibraryUserDto libraryUserDto) throws RuntimeException {
        if(libraryUserRepository.existsById(libraryUserDto.getUserId())) {
            throw new RuntimeException("Library user already exists. Choose update instead");
        }
        LibraryUser createLibraryUser = new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
        return getLibraryUserDto(libraryUserRepository.save(createLibraryUser));
    }

    @Override
    @Transactional
    public LibraryUserDto update(LibraryUserDto libraryUserDto) throws RuntimeException {
        if(!libraryUserRepository.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user does not exist, please create new library user instead.");
        LibraryUser libraryUser = libraryUserRepository.findByUserId(libraryUserDto.getUserId());
        if(libraryUser.getRegDate() != libraryUserDto.getRegDate()){
            libraryUser.setRegDate(libraryUserDto.getRegDate());
        }
        if(!libraryUser.getName().equals(libraryUserDto.getName())){
            libraryUser.setName(libraryUserDto.getName());
        }
        if(!libraryUser.getEmail().equalsIgnoreCase(libraryUserDto.getEmail())) {
            libraryUser.setEmail(libraryUserDto.getEmail());
        }
        return getLibraryUserDto(libraryUserRepository.save(libraryUser));

    }

    @Override
    @Transactional
    public boolean delete(int userId) throws RuntimeException {
        boolean deleted = false;
        if(!libraryUserRepository.findByUserId(userId).isPresent()){
            throw new RuntimeException("Can not find library user with id: " + userId);
        }
        if(libraryUserRepository.existsById(userId)){
            libraryUserRepository.delete(libraryUserRepository.findByUserId(userId));
            deleted = true;
        }
        return deleted;
    }

//    @ExceptionHandler
//    public ResponseEntity<LoanTakerErrorResponse> handleException (LoanTakerNotFoundException exception){
//        LoanTakerErrorResponse errorResponse = new LoanTakerErrorResponse();
//
//        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
//        errorResponse.setMessage(exception.getMessage());
//        errorResponse.setTimeStamp(System.currentTimeMillis());
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
}
