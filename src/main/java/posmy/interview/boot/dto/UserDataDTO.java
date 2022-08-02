package posmy.interview.boot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import posmy.interview.boot.domain.UserRole;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDataDTO {
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  List<UserRole> appUserRoles;
}
