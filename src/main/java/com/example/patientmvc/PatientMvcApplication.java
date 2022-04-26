package com.example.patientmvc;

import com.example.patientmvc.entities.Patient;
import com.example.patientmvc.repositorises.PatientRepository;
import com.example.patientmvc.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientMvcApplication.class, args);
    }
    @Bean//la methode va executer automatiquement au demarage
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   // @Bean
CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
patientRepository.save
        (new Patient(null, "houda", new Date(), false,122 ));
            patientRepository.save
                    (new Patient(null, "Ahmed", new Date(), true,122 ));
            patientRepository.save
                    (new Patient(null, "Yasmine", new Date(), true,545 ));
            patientRepository.save
                    (new Patient(null, "Salman", new Date(), false,321 ));

            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());

            });
        };
}
//@Bean
CommandLineRunner saveUsers(SecurityService securityService){
        return  args -> {
          securityService.saveNewUser("Houda","1234","1234");
            securityService.saveNewUser("Mehdi","1234","1234");
            securityService.saveNewUser("bader","1234","1234");

            securityService.saveNewRole("USER","");
            securityService.saveNewRole("ADMIN","");

            securityService.addRoleToUser("Houda","USER");
            securityService.addRoleToUser("Houda","ADMIN");
            securityService.addRoleToUser("Mehdi","USER");
            securityService.addRoleToUser("bader","USER");
        };
}
}
