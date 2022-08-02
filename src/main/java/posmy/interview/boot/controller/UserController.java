package posmy.interview.boot.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import posmy.interview.boot.domain.Users;
import posmy.interview.boot.dto.UserDataDTO;
import posmy.interview.boot.dto.UserResponseDTO;
import posmy.interview.boot.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  @PostMapping("/signin")
  public String login(@RequestParam String username, @RequestParam String password) {
    return userService.signin(username, password);
  }

  @GetMapping("/userList")
  public List<Users> userList() {
    return userService.getAllUsers();
  }

  @PostMapping("/signup")
  public String signup(@RequestBody UserDataDTO user) {
    return userService.signup(modelMapper.map(user, Users.class));
  }

  @PutMapping("/updateUser")
  public String updateUser(@RequestBody Users user) {
    return userService.updateUser(user);
  }

  @DeleteMapping("/delete")
  public void deleteUser(@RequestParam String username) {
    userService.delete(username);
  }

  @DeleteMapping("/deleteOwnAccount")
  public void deleteOwnAccount(HttpServletRequest req) {
    Users users = userService.whoami(req);
    userService.delete(users.getEmail());
  }

  @GetMapping(value = "/me")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public UserResponseDTO whoami(HttpServletRequest req) {
    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
  }
}
