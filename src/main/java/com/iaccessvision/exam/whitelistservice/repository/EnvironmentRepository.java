package com.iaccessvision.exam.whitelistservice.repository;

import com.iaccessvision.exam.whitelistservice.model.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Integer> {
}
