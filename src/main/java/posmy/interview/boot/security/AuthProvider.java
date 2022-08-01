/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package posmy.interview.boot.security;

/**
 *
 * @author afdzal
 */

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import posmy.interview.boot.model.Attempts;
import posmy.interview.boot.model.User;
import posmy.interview.boot.repository.AttemptsRepository;
import posmy.interview.boot.repository.UserRepository;

@Component
public class AuthProvider implements AuthenticationProvider {

    private static final int ATTEMPTS_LIMIT = 3;
    @Autowired
    private SecurityUserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AttemptsRepository attemptsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        
        String username = authentication.getName();
        UserDetails usr = userDetailsService.loadUserByUsername(username);
        String pwd = userDetailsService.loadUserByUsername(username).getPassword();

        Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(username);

        if (userAttempts.isPresent()) {
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(0);
            attemptsRepository.save(attempts);

        }

        if (userDetailsService == null) {
            throw new BadCredentialsException("1000");
        }
        if (!pwd.matches(usr.getPassword())) {
            throw new BadCredentialsException("1000");
        }

      //  List<Right> userRights = rightRepo.getUserRights(username);
        return authentication;
    }

    ;
   private void processFailedAttempts(String username, User user) {
        Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(username);
        if (userAttempts.isEmpty()) {
            Attempts attempts = new Attempts();
            attempts.setUsername(username);
            attempts.setAttempts(1);
            attemptsRepository.save(attempts);
        } else {
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(attempts.getAttempts() + 1);
            attemptsRepository.save(attempts);

            if (attempts.getAttempts() + 1
                    > ATTEMPTS_LIMIT) {
                user.setAccountNonLocked(false);
                userRepository.save(user);
                throw new LockedException("Too many invalid attempts. Account is locked!!");
            }
        }
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    //public boolean supports(Class<?> authentication) {  return true;    }
}
