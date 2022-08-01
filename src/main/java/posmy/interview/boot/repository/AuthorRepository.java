package posmy.interview.boot.repository;

import posmy.interview.boot.domain.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
