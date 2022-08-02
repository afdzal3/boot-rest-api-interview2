package posmy.interview.boot.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import posmy.interview.boot.domain.Books;

public interface BookRepository extends JpaRepository<Books, Long> {
}
