package com.example.patientmvc.security.service;

import com.example.patientmvc.security.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationNotification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
   private SecurityService securityService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //tecnique classic
        AppUser appUser=securityService.loadByUserName(username);//la couche service pour aller a la base de donne pour chercher utilisateur sachant son username
      /*  Collection<GrantedAuthority> authorities=new ArrayList ();//
        appUser.getAppRoles().forEach(role -> {//je vais parcourir les roles de utilisateur qui j'ai recupere "appUser"
         SimpleGrantedAuthority authority=new SimpleGrantedAuthority(role.getRoleName());//pour chaque role je vais cree un objet de type "SimpleGrandAuthority"
       authorities.add(authority);
        });*/
        //API streams
        Collection<GrantedAuthority> authorities1=
                appUser.getAppRoles()
                        .stream()
                        .map(role->new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList());

        User  user = new User(appUser.getUsername(), appUser.getPassword(), authorities1);//transfere les donnes de appUser ver User
        return user;
    }
}
