package com.iaccessvision.exam.whitelistservice.controller;

import com.iaccessvision.exam.whitelistservice.dto.WhitelistRequest;
import com.iaccessvision.exam.whitelistservice.dto.WhitelistResponse;
import com.iaccessvision.exam.whitelistservice.exception.WhitelistingException;
import com.iaccessvision.exam.whitelistservice.service.WhitelistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/whitelist")
public class WhitelistController {
    private final WhitelistService whitelistService;

    public WhitelistController(WhitelistService whitelistService) {
        this.whitelistService = whitelistService;
    }

    @GetMapping(path= "ip", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public ResponseEntity<List<String>> getWhitelistedClientIps(@RequestParam(value = "clientName", required = false) String clientName,
                                                                         @RequestParam(value = "appId", required = false) Integer appId,
                                                                         @RequestParam(value = "environmentId", required = false) Integer environmentId) {
        List<String> whitelistedClientIps = this.whitelistService.getWhitelistedClientIps(clientName, appId, environmentId);
        return ResponseEntity.ok(whitelistedClientIps);
    }

    @GetMapping(consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public ResponseEntity<List<WhitelistResponse>> getWhitelistedClients(@RequestParam(value = "clientName", required = false) String clientName,
                                                                         @RequestParam(value = "appId", required = false) Integer appId,
                                                                         @RequestParam(value = "environmentId", required = false) Integer environmentId) {
        List<WhitelistResponse> whitelistedClients = this.whitelistService.getWhitelistedClients(clientName, appId, environmentId);
        return ResponseEntity.ok(whitelistedClients);
    }

    @PostMapping(consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public ResponseEntity<WhitelistResponse> whitelist(@RequestBody @Valid WhitelistRequest whitelistRequest) {
        WhitelistResponse whitelistResponse = this.whitelistService.whitelist(whitelistRequest);
        return ResponseEntity.ok(whitelistResponse);
    }

    @DeleteMapping(path = "/{id}", consumes = { "application/json", "application/xml" }, produces = { "application/json", "application/xml" })
    public ResponseEntity<Void> deleteWhitelist(@PathVariable("id") int id) {
        this.whitelistService.deleteWhitelist(id);
        return ResponseEntity.ok(null);
    }
}
