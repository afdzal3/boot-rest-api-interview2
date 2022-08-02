package posmy.interview.boot.dto;

import lombok.Data;
import posmy.interview.boot.domain.UserRole;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponseDTO {
  private Long id;
  private String email;
  List<UserRole> appUserRoles;
}
