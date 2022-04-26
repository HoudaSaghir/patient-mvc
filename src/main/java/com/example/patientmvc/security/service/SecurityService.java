package com.example.patientmvc.security.service;

import com.example.patientmvc.security.entities.AppRole;
import com.example.patientmvc.security.entities.AppUser;

public abstract class SecurityService  {
    public abstract AppUser saveNewUser(String username, String password, String rePassword);
    public abstract AppRole saveNewRole(String roleName, String description);
    public abstract void addRoleToUser(String username, String roleName);
    public abstract void removeRoleFromUser(String username, String roleName);
    public abstract AppUser loadByUserName(String username);
}
