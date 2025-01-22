package jpabasic.testsecurity.service;

import jpabasic.testsecurity.dto.CustomUserDetails;
import jpabasic.testsecurity.entity.UserEntity;
import jpabasic.testsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);
        if(userData != null) { // 유저데이터가 널이 아닐떄 즉 데이터베이스에 데이터가 있을 떄
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
