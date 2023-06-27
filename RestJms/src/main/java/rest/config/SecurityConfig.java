package rest.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import rest.entity.User;
import rest.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Bean BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration();
    	configuration.setAllowedOrigins(Arrays.asList("*"));
    	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    	configuration.applyPermitDefaultValues();
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
    	source.registerCorsConfiguration("/**",configuration);
    	return source;
    }
    
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
        	User user = userRepository.findByUser(username)
        				.orElseThrow(() -> new UsernameNotFoundException("Could not find the user: " + username));
        	System.out.println("Usuario:"+username);
            return new org.springframework.security.core.userdetails.User(
            		user.getUser(), passwordEncoder().encode(user.getPassword()),
                    true, true, true, true,
                    AuthorityUtils.createAuthorityList(user.getRoles()));
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/AppEntity/**").permitAll()
        	.antMatchers("/restapi/user/login/**").permitAll()
        	.anyRequest().fullyAuthenticated()
        	.and().httpBasic()
        	.and().csrf().disable()
        	.cors()
        	;
    }
    
}
