package org.enset.hospital.service;

import lombok.AllArgsConstructor;
import org.enset.hospital.entities.AppRole;
import org.enset.hospital.entities.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userService.loadUserByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(String.format("User %s Not Exist",username));

        //String[] roles =  user.getRoles().stream().map(AppRole::getRole).toArray(String[]::new);

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                //.roles(roles)
                .build();
    }
}
