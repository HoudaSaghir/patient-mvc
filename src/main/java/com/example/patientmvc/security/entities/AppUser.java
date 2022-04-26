package com.example.patientmvc.security.entities;

import com.example.patientmvc.security.entities.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String password;
    private  boolean active;
    @ManyToMany(fetch = FetchType.EAGER) //un utilisateur peu avoir plusieur role et un role conserne plusieur utilisateur
    //LAZY: quand j'appele  la methode GetAppRoles hibarnet va le chercher
    //EAGER:a chaque fois je vais charger un utilisateur apartir de la base de donnee automatiquement hibernate va charger tout les roles de cette etulisateur au memoire
    private List<AppRole> appRoles=new ArrayList<>();
}
