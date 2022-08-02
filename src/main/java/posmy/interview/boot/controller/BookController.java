package posmy.interview.boot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import posmy.interview.boot.domain.Books;
import posmy.interview.boot.domain.Users;
import posmy.interview.boot.repo.BookRepository;
import posmy.interview.boot.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Transactional
public class BookController {

    private final BookRepository bookRepository;
    private final UserService userService;

    public BookController(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/books")
    public ResponseEntity<Books> createBooks(@RequestBody Books books) throws URISyntaxException {
        if (books.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new books cannot already have an ID");
        }
        Books result = bookRepository.save(books);
        return ResponseEntity
                .created(new URI("/api/books/" + result.getId()))
                .body(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/books/{id}")
    public ResponseEntity<Books> updateBooks(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody Books books
    ) {
        if (books.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, books.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Books result = bookRepository.save(books);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/books")
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/books/{id}")
    public ResponseEntity<Books> getBook(@PathVariable Long id) {
        Optional<Books> Book = bookRepository.findById(id);
        return ResponseEntity.ok(Book.get());
    }

    @GetMapping("/booksByUser")
    public List<Books> getBooksByUser(HttpServletRequest req) {
        Users users = userService.whoami(req);
        List<Books> books = bookRepository.findAllByUsersAndStatus(users, "BORROWED");
        return books;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
