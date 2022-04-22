package com.iaccessvision.exam.whitelistservice.service;

import com.iaccessvision.exam.whitelistservice.dto.WhitelistRequest;
import com.iaccessvision.exam.whitelistservice.dto.WhitelistResponse;
import com.iaccessvision.exam.whitelistservice.exception.EntityNotFoundException;
import com.iaccessvision.exam.whitelistservice.exception.WhitelistingException;
import com.iaccessvision.exam.whitelistservice.model.App;
import com.iaccessvision.exam.whitelistservice.model.Environment;
import com.iaccessvision.exam.whitelistservice.model.Whitelist;
import com.iaccessvision.exam.whitelistservice.repository.AppRepository;
import com.iaccessvision.exam.whitelistservice.repository.EnvironmentRepository;
import com.iaccessvision.exam.whitelistservice.repository.WhitelistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class WhitelistService {
    private final WhitelistRepository whitelistRepository;
    private final AppRepository appRepository;
    private final EnvironmentRepository environmentRepository;
    private final Logger logger = LoggerFactory.getLogger(WhitelistService.class);

    public WhitelistService(WhitelistRepository whitelistRepository, AppRepository appRepository, EnvironmentRepository environmentRepository) {
        this.whitelistRepository = whitelistRepository;
        this.appRepository = appRepository;
        this.environmentRepository = environmentRepository;
    }

    public List<String> getWhitelistedClientIps(String clientName, Integer appId, Integer environmentId) {
        List<String> clientIps = this.whitelistRepository.findAllClientIpsByClientNameAndEnvironmentAndApp(clientName, appId, environmentId);
        return clientIps;
    }

    public List<WhitelistResponse> getWhitelistedClients(String clientName, Integer appId, Integer environmentId) {
        List<Whitelist> whitelistedClients = whitelistRepository.findAllByClientNameAndEnvironmentAndApp(clientName, appId, environmentId);
        return whitelistedClients.stream()
                .map(whitelist -> {
                    WhitelistResponse whitelistResponse = new WhitelistResponse();
                    whitelistResponse.setId(whitelist.getId());
                    whitelistResponse.setClientName(whitelist.getClientName());
                    whitelistResponse.setClientIp(whitelist.getClientIp());
                    whitelistResponse.setAppName(whitelist.getApp().getName());
                    whitelistResponse.setEnvironmentName(whitelist.getEnvironment().getName());
                    return whitelistResponse;
                })
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = false)
    public WhitelistResponse whitelist(WhitelistRequest whitelistRequest) {
        try{
            this.logger.info("Whitelisting client: {} with IP: {} to app {} - {}", whitelistRequest.getClientName(),
                whitelistRequest.getClientIp(), whitelistRequest.getAppId(), whitelistRequest.getEnvironmentId());

            App app = this.appRepository.findById(whitelistRequest.getAppId())
                    .orElseThrow(() -> new WhitelistingException("The app your are trying to whitelist the client with is not found"));
            Environment environment = this.environmentRepository.findById(whitelistRequest.getEnvironmentId())
                    .orElseThrow(() -> new WhitelistingException("The environment you are trying to whitelist the client with is not found"));

            Optional<Whitelist> existingClient = this.whitelistRepository.findOne(
                    whitelistRequest.getClientName(),
                    whitelistRequest.getClientIp(),
                    whitelistRequest.getAppId(),
                    whitelistRequest.getEnvironmentId());
            if (existingClient.isPresent()) {
                throw new WhitelistingException("Client " + whitelistRequest.getClientName()
                        + " - " + whitelistRequest.getClientIp() + " record already exist for the specified app and environment");
            }

            Whitelist whitelist = new Whitelist();
            whitelist.setClientName(whitelistRequest.getClientName());
            whitelist.setClientIp(whitelistRequest.getClientIp());
            whitelist.setApp(app);
            whitelist.setEnvironment(environment);
            whitelistRepository.save(whitelist);

            WhitelistResponse whitelistResponse = new WhitelistResponse();
            whitelistResponse.setId(whitelist.getId());
            whitelistResponse.setClientName(whitelist.getClientName());
            whitelistResponse.setClientIp(whitelist.getClientIp());
            whitelistResponse.setAppName(app.getName());
            whitelistResponse.setEnvironmentName(environment.getName());
            return whitelistResponse;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Transactional(readOnly = false)
    public void deleteWhitelist(int id) {
        try {
            Whitelist whitelist = this.whitelistRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Whitelist not found"));
            this.whitelistRepository.delete(whitelist);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
