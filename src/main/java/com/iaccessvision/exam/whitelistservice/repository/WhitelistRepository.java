package com.iaccessvision.exam.whitelistservice.repository;

import com.iaccessvision.exam.whitelistservice.model.App;
import com.iaccessvision.exam.whitelistservice.model.Environment;
import com.iaccessvision.exam.whitelistservice.model.Whitelist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WhitelistRepository extends JpaRepository<Whitelist, Integer> {
    @Query(value = "SELECT w from Whitelist w LEFT JOIN FETCH w.app a LEFT JOIN FETCH w.environment e "
            + "WHERE (:clientName is null or :clientName = '' or :clientName = w.clientName)"
            + "AND (:clientIp is null or :clientIp = w.clientIp)"
            + "AND (:appId is null or :appId = a.id)"
            + "AND (:environmentId is null or :environmentId = e.id)")
    Optional<Whitelist> findOne(String clientName, String clientIp, Integer appId, Integer environmentId);

    @Query(value = "SELECT w from Whitelist w LEFT JOIN FETCH w.app a LEFT JOIN FETCH w.environment e "
    + "WHERE (:clientName is null or :clientName = '' or :clientName = w.clientName)"
    + "AND (:appId is null or :appId = a.id)"
    + "AND (:environmentId is null or :environmentId = e.id)")
    List<Whitelist> findAllByClientNameAndEnvironmentAndApp(String clientName, Integer appId, Integer environmentId);

    @Query(value = "SELECT DISTINCT(w.clientIp) from Whitelist w LEFT JOIN w.app a LEFT JOIN w.environment e "
            + "WHERE (:clientName is null or :clientName = '' or :clientName = w.clientName)"
            + "AND (:appId is null or :appId = a.id)"
            + "AND (:environmentId is null or :environmentId = e.id)")
    List<String> findAllClientIpsByClientNameAndEnvironmentAndApp(String clientName, Integer appId, Integer environmentId);
}