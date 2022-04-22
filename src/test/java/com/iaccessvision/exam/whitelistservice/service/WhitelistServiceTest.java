package com.iaccessvision.exam.whitelistservice.service;

import com.iaccessvision.exam.whitelistservice.dto.WhitelistResponse;
import com.iaccessvision.exam.whitelistservice.model.App;
import com.iaccessvision.exam.whitelistservice.model.Environment;
import com.iaccessvision.exam.whitelistservice.model.Whitelist;
import com.iaccessvision.exam.whitelistservice.repository.AppRepository;
import com.iaccessvision.exam.whitelistservice.repository.EnvironmentRepository;
import com.iaccessvision.exam.whitelistservice.repository.WhitelistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WhitelistServiceTest {

    @Mock
    private WhitelistRepository whitelistRepository;
    @Mock
    private AppRepository appRepository;
    @Mock
    EnvironmentRepository  environmentRepository;
    @InjectMocks
    private WhitelistService whitelistService;

    @Test
    void whenGetWhitelistedClientIps_thenReturnListOfClientIps() {
        //Given
        App app = new App(1, "app1");
        Environment environment1 = new Environment(1, "DEV");
        Environment environment2 = new Environment(2, "STAGING");
        Environment environment3 = new Environment(3, "PROD");

        List<String> whiteListedIps = List.of("10.10.10.1", "10.10.10.2", "10.10.10.3");

        List<String> expectedWhitelistResponses = List.of(
               "10.10.10.3", "10.10.10.2", "10.10.10.1"
        );

        Mockito.when(this.whitelistRepository.findAllClientIpsByClientNameAndEnvironmentAndApp(Mockito.isNull(), Mockito.any(Integer.class), Mockito.isNull()))
                .thenReturn(whiteListedIps);

        //When
        List<String> whitelistedClientIps = this.whitelistService.getWhitelistedClientIps(null, 1, null);

        //Then
        Assertions.assertThat(whitelistedClientIps).asList()
                .isNotEmpty()
                .hasSize(3)
                .containsAll(expectedWhitelistResponses);
    }

    @Test
    void getWhitelistedClients() {
        //Given
        App app = new App(1, "app1");
        Environment environment1 = new Environment(1, "DEV");
        Environment environment2 = new Environment(2, "STAGING");
        Environment environment3 = new Environment(3, "PROD");

//        List<Whitelist> whitelists = List.of(
//                new Whitelist(1, "client1", "10.10.10.1", app, environment1),
//                new Whitelist(2, "client2", "10.10.10.2", app, environment2),
//                new Whitelist(3, "client3", "10.10.10.3", app, environment3)
//
//        );

    }

    @Test
    void whitelist() {
    }

    @Test
    void deleteWhitelist() {
    }
}