package com.iaccessvision.exam.whitelistservice.service;

import com.iaccessvision.exam.whitelistservice.dto.WhitelistRequest;
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
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
    @Captor
    private ArgumentCaptor<Whitelist> whitelistArgumentCaptor;

    @Test
    void getWhitelistedClientIps_ShouldReturnListOfClientIps() {
        List<String> whiteListedIps = List.of("10.10.10.1", "10.10.10.2", "10.10.10.3");
        List<String> expectedWhitelistResponses = List.of(
               "10.10.10.3", "10.10.10.2", "10.10.10.1"
        );
        Mockito.when(this.whitelistRepository.findAllClientIpsByClientNameAndEnvironmentAndApp(Mockito.isNull(), Mockito.any(Integer.class), Mockito.isNull()))
                .thenReturn(whiteListedIps);

        List<String> whitelistedClientIps = this.whitelistService.getWhitelistedClientIps(null, 1, null);
        Assertions.assertThat(whitelistedClientIps).asList()
                .isNotEmpty()
                .hasSize(3)
                .containsAll(expectedWhitelistResponses);
    }

    @Test
    void getWhitelistedClients_ShouldReturnListOfWhitelistResponses() {
        App app = new App(1, "app1");
        Environment environment1 = new Environment(1, "DEV");
        List<Whitelist> whitelists = List.of(
                new Whitelist(1, "client1", "10.10.10.1", app, environment1)

        );
        Mockito.when(this.whitelistRepository.findAllByClientNameAndEnvironmentAndApp("client1", null, null))
                .thenReturn(whitelists);
        List<WhitelistResponse> whiteListedResponses = List.of(
                new WhitelistResponse(1, "client1", "10.10.10.1", "app1", "DEV")
        );

        List<WhitelistResponse> whitelistResponses = this.whitelistService.getWhitelistedClients("client1", null, null);
        Assertions.assertThat(whitelistResponses).asList()
                .isNotEmpty()
                .hasSize(1)
                .containsAll(whiteListedResponses);
    }

    @Test
    void whitelist_ShouldWhitelistClient_WhenClientNotWhitelisted() {
        App app = new App(1, "app1");
        Environment environment1 = new Environment(1, "DEV");
        Whitelist whitelist = new Whitelist(1, "client1", "10.10.10.1", app, environment1);
        WhitelistResponse expectedWhitelistResponse = new WhitelistResponse(1, "client1", "10.10.10.1", "app1", "DEV");
        Mockito.when(this.appRepository.findById(1))
                        .thenReturn(Optional.of(app));
        Mockito.when(this.environmentRepository.findById(1))
                .thenReturn(Optional.of(environment1));
        Mockito.when(this.whitelistRepository.save(Mockito.any(Whitelist.class)))
                .thenReturn(whitelist);
        WhitelistRequest whitelistRequest = new WhitelistRequest("client1", "10.10.10.1", 1, 1);

        WhitelistResponse whitelistResponse = this.whitelistService.whitelist(whitelistRequest);
        Assertions.assertThat(whitelistResponse).isNotNull()
                .isEqualTo(expectedWhitelistResponse);
    }

    @Test
    void deleteWhitelist_ShouldDeleteWhitelist_WhenWhitelistedClientExist() {
        Whitelist whitelist = new Whitelist(1, "client1", "10.10.10.1", null, null);
        Mockito.when(this.whitelistRepository.findById(1))
                .thenReturn(Optional.of(whitelist));

        this.whitelistService.deleteWhitelist(1);
        Mockito.verify(this.whitelistRepository, Mockito.times(1)).delete(whitelistArgumentCaptor.capture());
        Assertions.assertThat(whitelistArgumentCaptor.getValue().getId())
                .isEqualTo(whitelist.getId());
    }
}