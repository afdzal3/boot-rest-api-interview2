package posmy.interview.boot;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import posmy.interview.boot.domain.UserRole;
import posmy.interview.boot.domain.Users;
import posmy.interview.boot.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... params) throws Exception {

        Users usersAdmin = null;
        Users user = null;
        try {
            usersAdmin = userService.search("admin@localhost");
            user = userService.search("user@localhost");
        }catch (Exception ex){

        }
//        Users usersAdmin = userService.search("admin@localhost");
//        Users user = userService.search("user@localhost");

        if (usersAdmin == null){
            Users admin = new Users();
            admin.setFirstname("admin");
            admin.setPassword("admin");
            admin.setEmail("admin@localhost");
            admin.setAppUserRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ROLE_ADMIN)));
            userService.signup(admin);
        }

        if (user == null){
            Users client = new Users();
            client.setFirstname("user");
            client.setPassword("user");
            client.setEmail("user@localhost");
            client.setAppUserRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ROLE_CLIENT)));
            userService.signup(client);
        }

    }

}
