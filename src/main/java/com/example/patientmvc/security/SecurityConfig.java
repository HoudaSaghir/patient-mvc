package com.example.patientmvc.security;

import com.example.patientmvc.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration //toutes class qui utilise la notation @Conf..=c'est une class qui instancier en prend au milieu(spring quand il demare les premiers class qu'il va instancier c'est les class configuration
@EnableWebSecurity//c'est pour dire a spring security je veux accueillir la securite web
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired //injection des depondance
private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {//coment spring security va chercher les utilisateurs et les roles

       // PasswordEncoder passwordEncoder=passwordEncoder();
        //in memory authentification
         /*
        String encoderPWD=passwordEncoder.encode("1234");
        System.out.println(encoderPWD);
 //les utilisateurs qui ont le droit d'acceder a l'appliation il sera stocke au memoire(c'est a moi de precise)
        auth.inMemoryAuthentication().withUser("user1").password(encoderPWD).roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("1111")).roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("2345")).roles("USER","ADMINE");
*/

/*
        //jdbc authentification
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")  //pour chercher utilisateurs
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?") //charger les roles de cette utilisateurs
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);
*/


        //strategie user details service
      /*  auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return null;
            }
        });*/
        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.formLogin();//demande a spring security de etuliser un formulaire d'antentification(formulaire par defaut)
     http.authorizeRequests().antMatchers("/").permitAll();//permitAll=ça n'esite pas une authentification
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
      http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
     http.authorizeRequests().antMatchers("/webjars/**").permitAll();
      http.authorizeRequests().anyRequest().authenticated();//tout les requetes http néssicite une antentification
      http.exceptionHandling().accessDeniedPage("/403");
    }


}
