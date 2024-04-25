package org.enset.hospital.service;

import org.enset.hospital.entities.AppRole;
import org.enset.hospital.entities.AppUser;

public interface IUserService {
    AppUser saveUser(String username, String password, String email, String confirmPassword);
    AppRole saveRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleToUser(String username, String role);
    AppUser loadUserByUsername(String username);

}
