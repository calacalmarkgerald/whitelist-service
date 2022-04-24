package com.iaccessvision.exam.whitelistservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class WhitelistRequest {
    @NotBlank
    private String clientName;
    @NotBlank
    @Pattern(regexp = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",
    message = "must be a valid IPv4 address")
    private String clientIp;
    @NotNull
    @Positive
    private Integer appId;
    @NotNull
    @Positive
    private Integer environmentId;

    public WhitelistRequest() {}

    public WhitelistRequest(String clientName, String clientIp, Integer appId, Integer environmentId) {
        this.clientName = clientName;
        this.clientIp = clientIp;
        this.appId = appId;
        this.environmentId = environmentId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(Integer environmentId) {
        this.environmentId = environmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhitelistRequest that = (WhitelistRequest) o;
        return Objects.equals(clientName, that.clientName) && Objects.equals(clientIp, that.clientIp) && Objects.equals(appId, that.appId) && Objects.equals(environmentId, that.environmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientName, clientIp, appId, environmentId);
    }

    @Override
    public String toString() {
        return "WhitelistRequest{" +
                "clientName='" + clientName + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", appId=" + appId +
                ", environmentId=" + environmentId +
                '}';
    }
}
