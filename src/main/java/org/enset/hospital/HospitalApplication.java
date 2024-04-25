package org.enset.hospital;
import org.enset.hospital.entities.AppRole;
import org.enset.hospital.entities.AppUser;
import org.enset.hospital.entities.Patient;
import org.enset.hospital.repository.AppRoleRepository;
import org.enset.hospital.repository.AppUserRepository;
import org.enset.hospital.service.HospitalServiceImp;
import org.enset.hospital.service.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

//    @Bean
    CommandLineRunner start(HospitalServiceImp hospitalServiceImp){
        return args -> {
            Stream.of("Mohamed","Ali","Damia")
                    .forEach(name->{
                        Patient patient = new Patient();
                        patient.setNom(name);
                        patient.setScore((int) (Math.random() * 15));
                        patient.setDateNaissance(new Date());
                        patient.setMalade(true);
                        hospitalServiceImp.AddPatient(patient);
                    });
        };
    }

    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args->{

            //UserDetails us1 = jdbcUserDetailsManager.loadUserByUsername("user11");
            //if(us1 == null){
                   jdbcUserDetailsManager.createUser(User.withUsername("user1")
                    .password(passwordEncoder.encode("1234"))
                           .authorities("USER").build());
//}
            //UserDetails us2 = jdbcUserDetailsManager.loadUserByUsername("user22");
            //if(us2 == null)
                jdbcUserDetailsManager.createUser(User.withUsername("user2")
                    .password(passwordEncoder.encode("1234"))
                        .authorities("USER").build());

            //UserDetails adm = jdbcUserDetailsManager.loadUserByUsername("admin1");
            //if(adm == null)
                jdbcUserDetailsManager.createUser(User.withUsername("admin2")
                    .password(passwordEncoder.encode("1234"))
                        .authorities("USER","ADMIN").build());
        };
    }

   // @Bean
    CommandLineRunner commandLineRunner(IUserService iUserService) {
        return args -> {

            iUserService.saveUser("user1","1234",
                    "us1@gmail.ma","1234");

             iUserService.saveUser("user2","1234",
                    "us2@gmail.ma","1234");

            iUserService.saveUser("admin","1234",
                    "admin@gmail.ma","1234");

           iUserService.saveRole("USER");
           iUserService.saveRole("ADMIN");

            iUserService.addRoleToUser("user1","USER");
            iUserService.addRoleToUser("user2","USER");
            iUserService.addRoleToUser("admin","ADMIN");
            iUserService.addRoleToUser("admin","USER");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
