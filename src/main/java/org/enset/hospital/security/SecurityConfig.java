package org.enset.hospital.security;
import lombok.AllArgsConstructor;
import org.enset.hospital.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) //protéger les methods utilisant des annotation
@AllArgsConstructor
public class SecurityConfig {

    private PasswordEncoder passwordEncoder;
    private UserDetailServiceImpl userDetailService;

    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }
    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        return new InMemoryUserDetailsManager(
                //User.withUsername("user1").password("{noop}1234").roles("USER").build(),
                User.withUsername("user1").password(passwordEncoder.encode("1234")).authorities("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).authorities("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).authorities("USER","ADMIN").build()
        );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity
                //.formLogin(Customizer.withDefaults())//form login
                .formLogin(f->f.loginPage("/login").defaultSuccessUrl("/").permitAll())
                //.rememberMe(Customizer.withDefaults())
                .authorizeHttpRequests(ar->ar.requestMatchers("/webjars/**").permitAll())

                //.authorizeHttpRequests(ar->ar.requestMatchers("/admin/**").hasAuthority("ADMIN"))
                //.authorizeHttpRequests(ar->ar.requestMatchers("/user/**").hasAuthority("USER"))

                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .exceptionHandling(ex->ex.accessDeniedPage("/notAuthorized"))
                .userDetailsService(userDetailService)
                .build();

    }
}