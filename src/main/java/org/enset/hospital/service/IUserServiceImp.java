package org.enset.hospital.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.enset.hospital.entities.AppRole;
import org.enset.hospital.entities.AppUser;
import org.enset.hospital.repository.AppRoleRepository;
import org.enset.hospital.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class IUserServiceImp implements IUserService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public AppUser saveUser(String username, String password, String email, String confirmPassword) {
        AppUser user1 = appUserRepository.findByUsername(username);
        if (user1!=null)
            throw new RuntimeException("User Already Exist");

        if(!Objects.equals(password, confirmPassword))
            throw new RuntimeException("Password Not Correct");

        return appUserRepository.save(AppUser.builder()
                .userId(UUID.randomUUID().toString())
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                .build());
    }

    @Override
    public AppRole saveRole(String role) {
        AppRole role1 = appRoleRepository.findByRole(role);
        if (role1!=null)
            throw new RuntimeException("Role Already Exist");

        return appRoleRepository.save(AppRole.builder().roleId(UUID.randomUUID().toString()).role(role).build());
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser user = appUserRepository.findByUsername(username);
        AppRole role1 = appRoleRepository.findByRole(role);
        if (user == null)
            throw new RuntimeException("User Not Exist");

        if (role1 ==null)
            throw new RuntimeException("Role Not Exist");

        user.getRoles().add(role1);
        //appUserRepository.save(user); /:Gérer automatique par Transaction
    }

    @Override
    public void removeRoleToUser(String username, String role) {
        AppUser user = appUserRepository.findByUsername(username);
        AppRole role1 = appRoleRepository.findByRole(role);

        if (user == null)
            throw new RuntimeException("User Not Exist");

        if (role1 ==null)
            throw new RuntimeException("Role Not Exist");

        user.getRoles().remove(role1);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null)
            throw new RuntimeException("User Not Exist");
        return user;
    }
}
