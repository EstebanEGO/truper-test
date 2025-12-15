package com.truper.test.initial.load;

import com.truper.test.model.entity.BranchEntity;
import com.truper.test.repository.IBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialLoad implements ApplicationRunner {
    @Autowired
    private IBranchRepository branchRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (branchRepository.count() == 0) {
            branchRepository.save(
                    BranchEntity.builder()
                            .name("CDMX")
                            .build()
            );
        }
    }
}
