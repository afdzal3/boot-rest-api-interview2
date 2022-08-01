package posmy.interview.boot.repository.security;

import posmy.interview.boot.domain.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
  @Query(value = "SELECT * FROM users U WHERE U.EMAIL =:email", nativeQuery = true)
  User findUsersByEmail (@Param("email") String email);
}
