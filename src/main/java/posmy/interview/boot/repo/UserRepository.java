package posmy.interview.boot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import posmy.interview.boot.domain.Users;
import javax.transaction.Transactional;

public interface UserRepository extends JpaRepository<Users, Long> {

  boolean existsByEmail(String email);

  Users findByEmail(String email);

  @Transactional
  void deleteByEmail(String email);

}
