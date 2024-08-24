package com.itvdeant.gamestore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import com.itvdeant.gamestore.service.MyUserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http = http.csrf().disable();
		
		http.authorizeHttpRequests()
			.requestMatchers(HttpMethod.GET, "/products").permitAll()
			.requestMatchers(HttpMethod.GET, "/products/add").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.POST, "/products/add").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.GET, "/products/edit/{id}").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.POST, "/products/edit/{id}").hasAuthority("ADMIN")
			.requestMatchers(HttpMethod.GET, "/products/delete/{id}").hasAuthority("ADMIN")
			.anyRequest().permitAll()
			.and().formLogin(form -> form
                    .loginPage("/users/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?loginError=true"))
			.httpBasic(withDefaults())
			.authenticationProvider(DaoAuthenticationProvider());
		
		return http.build();
		
	}
	
	@Bean
	public DaoAuthenticationProvider DaoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.myUserDetailsService);
		provider.setPasswordEncoder(this.passwordEncode());
		return provider;
	}
	
	@Bean
	public AuthenticationManager AuthenticationManager(AuthenticationConfiguration configure) throws Exception {
		return configure.getAuthenticationManager();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService user() {
		UserDetails user = User.builder()
							.username("user")
							.password(passwordEncode().encode("password"))
							.roles("USER")
							.build();
		
		UserDetails admin = User.builder()
							.username("admin")
							.password(passwordEncode().encode("password"))
							.roles("ADMIN", "USER")
							.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}

}
