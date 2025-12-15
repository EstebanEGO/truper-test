package com.truper.test.initial.load;

import com.truper.test.model.entity.BranchEntity;
import com.truper.test.model.entity.UserEntity;
import com.truper.test.repository.IBranchRepository;
import com.truper.test.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialLoad implements ApplicationRunner {
    @Autowired
    private IBranchRepository branchRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (branchRepository.count() == 0) {
            branchRepository.save(
                    BranchEntity.builder()
                            .name("CDMX")
                            .build()
            );
        }

        if (userRepository.count() == 0) {
            userRepository.save(
                    UserEntity.builder()
                            .email("admin@truper.com")
                            .password(passwordEncoder.encode("12345"))
                            .build()
            );
        }
    }
}
