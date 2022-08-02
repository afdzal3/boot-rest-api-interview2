package posmy.interview.boot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import posmy.interview.boot.domain.Users;
import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

  boolean existsByEmail(String email);

  Users findByEmail(String email);
  Users findByEmailAndIsActive(String email, boolean isActive);


  List<Users> findAllByIsActive(boolean isActive);

  @Transactional
  void deleteByEmail(String email);

}
