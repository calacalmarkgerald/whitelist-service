package com.iaccessvision.exam.whitelistservice.repository;

import com.iaccessvision.exam.whitelistservice.model.Whitelist;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class WhitelistRepositoryTest {

    @Autowired
    private WhitelistRepository whitelistRepository;

    @Test
    @Sql("classpath:sql/test-data.sql")
    void givenWhitelistedClient1Exist_whenFindOne_thenReturnClient1() {
        Optional<Whitelist> client1 = this.whitelistRepository.findOne("client1", "10.10.10.1",
                1, 1);
        Assertions.assertThat(client1).hasValueSatisfying(whitelist -> {
            Assertions.assertThat(whitelist.getId()).isEqualTo(1);
            Assertions.assertThat(whitelist.getClientIp()).isEqualTo("10.10.10.1");
            Assertions.assertThat(whitelist.getClientName()).isEqualTo("client1");
            Assertions.assertThat(whitelist.getApp().getId()).isEqualTo(1);
            Assertions.assertThat(whitelist.getEnvironment().getId()).isEqualTo(1);
        });
    }

    @Test
    @Sql("classpath:sql/test-data.sql")
    void givenWhitelistedClients_whenFindAllByClientNameAndEnvironmentAndApp_thenReturnListOfWhitelistedClients() {
        List<Whitelist> whitelistListedClients = this.whitelistRepository
                .findAllByClientNameAndEnvironmentAndApp(null, 1, null);

        Assertions.assertThat(whitelistListedClients).asList().isNotEmpty().hasSize(2);
    }

    @Test
    @Sql("classpath:sql/test-data.sql")
    void givenWhitelistedClients_whenFindAllClientIpsByClientNameAndEnvironmentAndApp_thenReturnListOfWhitelistedClients() {
        List<String> whitelistListedClientIps = this.whitelistRepository
                .findAllClientIpsByClientNameAndEnvironmentAndApp(null, null, 3);

        Assertions.assertThat(whitelistListedClientIps).asList().isNotEmpty().hasSize(3);
    }
}