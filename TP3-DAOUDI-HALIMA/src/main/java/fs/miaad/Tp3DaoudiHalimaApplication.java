package fs.miaad;
import fs.miaad.entities.Patient;
import fs.miaad.repository.PatientRepository;
import fs.miaad.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;
@SpringBootApplication
public class Tp3DaoudiHalimaApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(Tp3DaoudiHalimaApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
     /* patientRepository.save(new Patient(null , "halima1" , new Date(), false ,40));
      patientRepository.save(new Patient(null , "halima2" , new Date(), true ,120));
      patientRepository.save(new Patient(null , "halima3" , new Date(), false ,70));

      */
    }
   // @Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder)
    {
        return args -> {
            if (!jdbcUserDetailsManager.userExists("user1")) {
                jdbcUserDetailsManager.createUser(User.withUsername("user1")
                        .password(passwordEncoder.encode("0000"))
                        .roles("USER").build());
            }

            if (!jdbcUserDetailsManager.userExists("user2")) {
                jdbcUserDetailsManager.createUser(User.withUsername("user2")
                        .password(passwordEncoder.encode("0000"))
                        .roles("USER").build());
            }

            if (!jdbcUserDetailsManager.userExists("admin")) {
                jdbcUserDetailsManager.createUser(User.withUsername("admin")
                        .password(passwordEncoder.encode("0000"))
                        .roles("USER", "ADMIN").build());
            }
        };
    }

    @Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService)
    {
        return args->{
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");

            accountService.addNewUser("user11", "1111", "user11@gmail.com", "1111");
            accountService.addNewUser("user22", "1111", "user22@gmail.com", "1111");
            accountService.addNewUser("admin0", "1111", "admin0@gmail.com", "1111");

            accountService.addRoleToUser("user11", "USER");
            accountService.addRoleToUser("user22", "USER");
            accountService.addRoleToUser("admin0", "USER");
            accountService.addRoleToUser("admin0", "ADMIN");
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
