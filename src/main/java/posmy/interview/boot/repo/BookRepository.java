package posmy.interview.boot.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import posmy.interview.boot.domain.Books;
import posmy.interview.boot.domain.Users;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Long> {
    List<Books> findAllByUsersAndStatus(Users users, String status);
}
