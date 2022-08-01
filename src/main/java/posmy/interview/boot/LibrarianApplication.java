package posmy.interview.boot;

import posmy.interview.boot.service.security.LibraryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LibrarianApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibrarianApplication.class, args);
	}

	@Bean
	@Autowired
	CommandLineRunner initUsers (LibraryUserDetailsService userService) {
		return args -> {
		    userService.initUsers();
		};
	}
}
