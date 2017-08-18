package org.donnex.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	
	public SecurityConfiguration() {
		super(true);
	}	
	
	@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable().authorizeRequests()
         .antMatchers("/").permitAll()
         .antMatchers(HttpMethod.POST, "/login").permitAll()
         .antMatchers(HttpMethod.POST, "/users").permitAll()
//         .anyRequest().permitAll()
         .anyRequest().authenticated()
         .and()
         // We filter the api/login requests
         .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                 UsernamePasswordAuthenticationFilter.class)
         // And filter other requests to check the presence of JWT in header
         .addFilterBefore(new JWTAuthenticationFilter(),
                 UsernamePasswordAuthenticationFilter.class);
			
		
		
	}
	
    @Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }
	

}
