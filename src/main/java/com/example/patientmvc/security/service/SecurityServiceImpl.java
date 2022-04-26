package com.example.patientmvc.security.service;

import com.example.patientmvc.security.entities.AppRole;
import com.example.patientmvc.security.entities.AppUser;
import com.example.patientmvc.security.repositories.AppRoleRepository;
import com.example.patientmvc.security.repositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j //permet de donne un attrebu qui s'appele log qui permet de log
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl extends SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
       if(!password.equals(rePassword)) throw new RuntimeException("Password not match");
       String hachePWD=passwordEncoder.encode(password);
       AppUser appUser=new AppUser();
       appUser.setUserId(UUID.randomUUID().toString());
       appUser.setUsername(username);
       appUser.setPassword((hachePWD));
       appUser.setActive(true);
      AppUser savedAppUser= appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {

       AppRole appRole= appRoleRepository.findByRoleName(roleName);
       if(appRole!=null)throw new  RuntimeException("Role" +roleName+ "Already exist");
       appRole= new AppRole();
       appRole.setRoleName(roleName);
       appRole.setDescription(description);
       appRoleRepository.save(appRole);
        return appRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
      AppUser appUser= appUserRepository.findByUsername(username);
        if(appUser==null)throw new  RuntimeException("User not found");
      AppRole appRole= appRoleRepository.findByRoleName(roleName);
        if(appRole==null)throw new  RuntimeException("Role not found");
      appUser.getAppRoles().add(appRole);
      //appUserRepository.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser= appUserRepository.findByUsername(username);
        if(appUser==null)throw new  RuntimeException("User not found");
        AppRole appRole= appRoleRepository.findByRoleName(roleName);
        if(appRole==null)throw new  RuntimeException("Role not found");
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadByUserName(String username) {

        return appUserRepository.findByUsername(username);
    }
}
