package com.truper.test.repository;

import com.truper.test.model.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IBranchRepository extends JpaRepository<BranchEntity, Integer> {
}
