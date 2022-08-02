package posmy.interview.boot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import posmy.interview.boot.domain.Users;
import posmy.interview.boot.exception.CustomException;
import posmy.interview.boot.repo.UserRepository;
import posmy.interview.boot.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  public String signin(String username, String password) {
    try {
      if (userRepository.findByEmailAndIsActive(username, true) == null ){
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username/password supplied");
      }
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      return jwtTokenProvider.createToken(username, userRepository.findByEmailAndIsActive(username, true).getAppUserRoles());
    } catch (AuthenticationException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username/password supplied");
    }
  }

  public String signup(Users appUser) {
    if (!userRepository.existsByEmail(appUser.getEmail())) {
      appUser.setIsActive(true);
      appUser.setIsVerified(true);
      appUser.setUpdatedAt(LocalDateTime.now());
      appUser.setCreatedAt(LocalDateTime.now());
      appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
      userRepository.save(appUser);
      return jwtTokenProvider.createToken(appUser.getEmail(), appUser.getAppUserRoles());
    } else {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username is already in use");
    }
  }


  public String updateUser(Users appUser) {
    Optional<Users> users = userRepository.findById(appUser.getId());
    if (users.isPresent()){
      users.get().setFirstname(appUser.getFirstname());
      users.get().setLastname(appUser.getLastname());
      userRepository.save(users.get());
      return "saved";
    }
    return "no";
  }

  public void delete(String username) {
    Users users = userRepository.findByEmail(username);
    users.setIsActive(false);
    userRepository.save(users);
  }

  public List<Users> getAllUsers(){
    return userRepository.findAllByIsActive(true);
  }

  public Users search(String username) {
    Users appUser = userRepository.findByEmail(username);
    if (appUser == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return appUser;
  }

  public Users whoami(HttpServletRequest req) {
    return userRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByEmail(username).getAppUserRoles());
  }

}
