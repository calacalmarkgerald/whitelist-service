package com.iaccessvision.exam.whitelistservice.repository;

import com.iaccessvision.exam.whitelistservice.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<App,Integer> {
}
