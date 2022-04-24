package com.iaccessvision.exam.whitelistservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iaccessvision.exam.whitelistservice.dto.WhitelistRequest;
import com.iaccessvision.exam.whitelistservice.dto.WhitelistResponse;
import com.iaccessvision.exam.whitelistservice.exception.EntityNotFoundException;
import com.iaccessvision.exam.whitelistservice.service.WhitelistService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(WhitelistController.class)
class WhitelistControllerTest {

    @MockBean
    private WhitelistService whitelistService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should return status 200 with list of client ips When making GET request to endpoint - /api/v1/whitelist/ip")
    @WithMockUser(value = "admin")
    void getWhitelistedClientIps_ShouldReturn200WithListOfClientIps() throws Exception {
        List<String> whiteListedIps = List.of("10.10.10.1", "10.10.10.2", "10.10.10.3");
        Mockito.when(this.whitelistService.getWhitelistedClientIps(
                Mockito.any(String.class), Mockito.any(Integer.class), Mockito.isNull()
                )).thenReturn(whiteListedIps);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/whitelist/ip?clientName=client1&appId=1&environmentId=")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(whiteListedIps)));
    }

    @Test
    @DisplayName("Should return status 401 When making GET request to endpoint - /api/v1/whitelist/ip If User is Unauthorized")
    void getWhitelistedClientIps_shouldReturn401_WhenUnauthorized() throws Exception {
        Mockito.when(this.whitelistService.getWhitelistedClientIps(
                Mockito.any(String.class), Mockito.any(Integer.class), Mockito.isNull()
        )).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/whitelist/ip")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    @Test
    @DisplayName("Should return status 200 with list of whitelisted clients When making GET request to endpoint - api/v1/whitelist")
    @WithMockUser(value = "admin")
    void getWhitelistedClients_shouldReturn200WithListOfWhitelists() throws Exception{
        List<WhitelistResponse> whiteListedResponses = List.of(
                new WhitelistResponse(1, "client1", "10.10.10.1", "app1", "STAGE"),
                new WhitelistResponse(2, "client2", "10.10.10.2", "app1", "DEV"),
                new WhitelistResponse(3, "client3", "10.10.10.3", "app2", "PROD")
        );
        Mockito.when(this.whitelistService.getWhitelistedClients(
                Mockito.any(String.class), Mockito.any(Integer.class), Mockito.isNull()
        )).thenReturn(whiteListedResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/whitelist?clientName=client1&appId=1&environmentId=")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(whiteListedResponses.get(0).getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientName", Matchers.is(whiteListedResponses.get(0).getClientName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].clientIp", Matchers.is(whiteListedResponses.get(0).getClientIp())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].appName", Matchers.is(whiteListedResponses.get(0).getAppName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].environmentName", Matchers.is(whiteListedResponses.get(0).getEnvironmentName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(whiteListedResponses.get(1).getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientName", Matchers.is(whiteListedResponses.get(1).getClientName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].clientIp", Matchers.is(whiteListedResponses.get(1).getClientIp())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].appName", Matchers.is(whiteListedResponses.get(1).getAppName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].environmentName", Matchers.is(whiteListedResponses.get(1).getEnvironmentName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.is(whiteListedResponses.get(2).getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].clientName", Matchers.is(whiteListedResponses.get(2).getClientName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].clientIp", Matchers.is(whiteListedResponses.get(2).getClientIp())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].appName", Matchers.is(whiteListedResponses.get(2).getAppName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].environmentName", Matchers.is(whiteListedResponses.get(2).getEnvironmentName())))
        ;
    }

    @Test
    @DisplayName("Should return status 200 and whitelist the client When making POST request to endpoint - /api/v1/whitelist")
    @WithMockUser(value = "admin")
    void whitelist_shouldWhitelistClient() throws Exception {
        WhitelistRequest whitelistRequest = new WhitelistRequest("client1", "10.10.10.1", 1, 2);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(whitelistRequest);
        WhitelistResponse whitelistResponse = new WhitelistResponse(1, "client1", "10.10.10.1", "app1", "STAGE");
        String responseJson = mapper.writeValueAsString(whitelistResponse);

        Mockito.when(this.whitelistService.whitelist(Mockito.any(WhitelistRequest.class)))
                        .thenReturn(whitelistResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/whitelist")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(responseJson));
    }

    @Test
    @DisplayName("Should return status 200 and delete the whitelisted client When making DELETE request to endpoint - /api/v1/whitelist/${id}")
    @WithMockUser(value = "admin")
    void deleteWhitelist_shouldDeleteWhitelistedClient() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/whitelist/" + 1)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should return status 404 When making DELETE request to endpoint - /api/v1/whitelist/${id} If Whitelisted Client do not exist")
    @WithMockUser(value = "admin")
    void deleteWhitelist_shouldThrowError_WhenWhitelistedClientNotExist() throws Exception {

        Mockito.doThrow(new EntityNotFoundException("Whitelist not found")).when(this.whitelistService).deleteWhitelist(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/whitelist/" + 1)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}