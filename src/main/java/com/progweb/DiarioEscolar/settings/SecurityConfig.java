package com.progweb.DiarioEscolar.settings;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.progweb.DiarioEscolar.settings.security.AuthenticationFilter;
import com.progweb.DiarioEscolar.settings.security.AuthorizationFilter;
import com.progweb.DiarioEscolar.settings.security.JWTUtil;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
	@Autowired
    private JWTUtil jwtUtil;
    
	private static final String[] PUBLIC_ROUTES = {
        "/v2/api-docs",
        "/signup",
        "/h2-console/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/login",
        "/alunos/**",
        "/professores/**",
        "/turmas/**",
        "/projetos/**"
    };

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic();
        http.cors().and().csrf().disable();
        http.headers().frameOptions().sameOrigin(); 

        http.addFilter(new AuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new AuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
        
        http.authorizeRequests()
            .antMatchers(HttpMethod.PATCH, "/turmas/**").hasAnyRole("PROF")
            .antMatchers(HttpMethod.PATCH, "/projetos/**").hasAnyRole("PROF")
            .antMatchers( PUBLIC_ROUTES).permitAll()
            .anyRequest().authenticated();
            
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS", "PATCH"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
