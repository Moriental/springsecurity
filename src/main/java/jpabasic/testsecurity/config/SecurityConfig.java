package jpabasic.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
//        return hierarchy;
//    } 계층 권한, 계층 권한의 뜻은 A>B>C 즉 hasAnyRole로 하나 하나 전부 작성하게 되면 코드가 길어지고 
//      가독성이 안좋아짐 그래서 메소드를 통해서 구현
// https://www.youtube.com/watch?v=y0PXQgrkb90&list=PLJkjrxxiBSFCKD9TRKDYn7IE96K2u3C3U

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                );
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        //.defaultSuccessUrl("/admin")
                        .permitAll());
//        http
//                .csrf((auth) -> auth.disable());
        http
                .sessionManagement((auth) -> auth
                        //false 초과시 기존 세션 하나 삭제
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)); //true 초과시 새로운 로그인 차단
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());
        return http.build();
    }
//    @Bean inmemory database 데이터베이스를 사용하지 않고 메모리에 간단하게 저장하는 유저정보
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.builder()
//                .username("user1")
//                .password(bCryptPasswordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("user2")
//                .password(bCryptPasswordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }
}


