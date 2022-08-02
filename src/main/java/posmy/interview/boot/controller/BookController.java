package posmy.interview.boot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import posmy.interview.boot.domain.Books;
import posmy.interview.boot.repo.BookRepository;

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

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

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

    @GetMapping("/books")
    public List<Books> getAllReservations() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Books> getReservation(@PathVariable Long id) {
        Optional<Books> reservation = bookRepository.findById(id);
        return ResponseEntity.ok(reservation.get());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
