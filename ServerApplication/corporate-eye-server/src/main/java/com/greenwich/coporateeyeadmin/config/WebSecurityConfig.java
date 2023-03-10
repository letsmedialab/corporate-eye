package com.greenwich.coporateeyeadmin.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private DataSource dataSource;
     
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled from cuser where username=? ")
            .authoritiesByUsernameQuery("select username, role from cuser where username=?")
        ;
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().and().csrf().disable();
        http
        .authorizeRequests()
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        .antMatchers("/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
        .anyRequest().authenticated()
        
        .and()
        
        .exceptionHandling().accessDeniedPage("/403")
     
        .and()      
           
           
            .formLogin().permitAll()
            .and()
            .logout().permitAll().and().httpBasic();     
    }
}
