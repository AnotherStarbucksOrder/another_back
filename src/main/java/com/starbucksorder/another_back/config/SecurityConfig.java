package com.starbucksorder.another_back.config;

import com.starbucksorder.another_back.security.filter.JwtFilter;
import com.starbucksorder.another_back.security.handler.AuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private AuthenticationHandler AuthenticationHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll()
                        .requestMatchers("/auth/signin","/home/**","/category/**","/menu/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // user가 돌기전에 jwtFilter 돌겠다
                .exceptionHandling(exception -> exception.authenticationEntryPoint(AuthenticationHandler));
        return http.build(); // 위 설정 기반으로 만들어줌
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedOrigin("*");
//        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }




//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().disable(); // form 로그인 x
//        http.httpBasic().disable(); // 기본 인증 셋팅값 x
//        http.cors();                // restApi에서 주로 사용
//
//        // 인증 중에 세션 X, 토큰 인증 방식 사용할 때!
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeRequests()
//                .antMatchers("/admin/**")
//                .hasRole("ADMIN")
//                .anyRequest()
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(new CustomAccessDeniedHandler());
//
//             .antMatchers("/auth/signin","/home/**","/category/**","/menu/**").permitAll()
//
//        // 'Authentication - 인증의 관련된 모든 정보' 객체 만들어주지 못하였을 때 발생 할 handler
//        http.exceptionHandling().authenticationEntryPoint(AuthenticationHandler);
//
//        // jwtFilter
//        // username~ 전에 jwtFilter가 돌게
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}
