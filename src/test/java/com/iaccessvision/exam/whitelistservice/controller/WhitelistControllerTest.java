package com.iaccessvision.exam.whitelistservice.controller;

import com.iaccessvision.exam.whitelistservice.service.WhitelistService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(WhitelistController.class)
class WhitelistControllerTest {

    //TODO: Improve testing: https://www.youtube.com/watch?v=oLtXe1wgSC8 and https://github.com/rieckpil/blog-tutorials/blob/master/testing-spring-boot-applications-with-mockmvc/src/test/java/de/rieckpil/blog/user/UserControllerTest.java
    @MockBean
    private WhitelistService whitelistService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return 200 with list of client ips When making GET request to endpoint - /api/v1/whitelist/ip")
    @WithMockUser(value = "admin")
    void shouldReturn200WithListOfClientIps() throws Exception {
        List<String> whiteListedIps = List.of("10.10.10.1", "10.10.10.2", "10.10.10.3");
        Mockito.when(this.whitelistService.getWhitelistedClientIps(
                Mockito.any(String.class), Mockito.any(Integer.class), Mockito.isNull()
                )).thenReturn(whiteListedIps);

        List<String> expectedWhitelistResponses = List.of(
                "10.10.10.1", "10.10.10.2", "10.10.10.3"
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/whitelist/ip?clientName=client1&appId=1&environmentId=")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(expectedWhitelistResponses)));
    }

    @Test
    void getWhitelistedClients() {
    }

    @Test
    void whitelist() {
    }

    @Test
    void deleteWhitelist() {
    }
}