package posmy.interview.boot.service;

import posmy.interview.boot.domain.model.AuthorDto;
import posmy.interview.boot.domain.model.BookDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
   List<AuthorDto> getAll();
   List<AuthorDto> getByName(String name);
   List<AuthorDto> getByBookTitle(String title);
   AuthorDto createAuthor(AuthorDto author);
   List<AuthorDto> createAll(List<AuthorDto> authors);
   Optional<AuthorDto> get(Long id);
   Optional<AuthorDto> addNewBookToAuthor(Long id, BookDto source);
   Optional<AuthorDto> updateAuthorById(Long byId, AuthorDto source);
   Optional<AuthorDto> addBookById(Long id, Long bookId);
   Optional<AuthorDto> removeBookById(Long id, Long bookId);
   Optional<AuthorDto> deleteAuthorById(Long id);
}
