package jpabasic.testsecurity.service;

import jpabasic.testsecurity.dto.JoinDTO;
import jpabasic.testsecurity.entity.UserEntity;
import jpabasic.testsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void joinProcess(JoinDTO joinDTO) {
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if(isUser == true){
            return;
        }
        //db에 이미 동일한 이름을 가진 회원이 있는가?
        UserEntity data = new UserEntity();
        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setRole("ROLE_ADMIN");
        userRepository.save(data);
    }
}
