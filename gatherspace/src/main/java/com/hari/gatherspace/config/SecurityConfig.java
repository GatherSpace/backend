package com.hari.gatherspace.config;


 import com.hari.gatherspace.service.UserService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.authentication.AuthenticationProvider;
 import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
 import org.springframework.security.config.http.SessionCreationPolicy;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 import org.springframework.web.cors.CorsConfiguration;
 import org.springframework.web.cors.CorsConfigurationSource;
 import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

 import java.util.Arrays;
 import java.util.List;

@Configuration
 @EnableWebSecurity

 public class SecurityConfig {


     private final UserService userService;

     private final JwtAuthFilter jwtAuthFilter;

     @Autowired
     public SecurityConfig(UserService userService, JwtAuthFilter jwtAuthFilter) {
         this.userService = userService;
         this.jwtAuthFilter = jwtAuthFilter;
     }

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                 .csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(request -> request
                         .requestMatchers(
                                 "/api/signup",
                                 "/api/login",
                                 "/ws/**",
                                 "/",
                                 "/swagger-ui/**",
                                 "/api/refresh-token",
                                 "/v3/api-docs/**",
                                 "/swagger-resources/**",  // Add this
                                 "/configuration/**",      // Add this
                                 "/webjars/**",
                                 "/swagger-ui.html"
                         ).permitAll()
                         .anyRequest().authenticated())
                 .sessionManagement(session -> session
                         .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authenticationProvider(authenticationProvider())
                 .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

         return http.build();
     }

     @Bean
     CorsConfigurationSource corsConfigurationSource() {
         CorsConfiguration configuration = new CorsConfiguration();
         configuration.setAllowedOrigins(List.of("http://localhost:5173"));
         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
         configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
         configuration.setAllowCredentials(true);
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**", configuration);
         return source;
     }

     @Bean
     public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
     }

     @Bean
     public AuthenticationProvider authenticationProvider(){
         DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
         authenticationProvider.setUserDetailsService(userService);
         authenticationProvider.setPasswordEncoder(passwordEncoder());
         return authenticationProvider;
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
         return config.getAuthenticationManager();
     }
 }