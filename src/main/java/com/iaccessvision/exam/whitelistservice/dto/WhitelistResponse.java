package com.iaccessvision.exam.whitelistservice.dto;

import java.util.Objects;

public class WhitelistResponse {
    private Integer id;
    private String clientName;
    private String clientIp;
    private String appName;
    private String environmentName;

    public WhitelistResponse() {}

    public WhitelistResponse(Integer id, String clientName, String clientIp, String appName, String environmentName) {
        this.id = id;
        this.clientName = clientName;
        this.clientIp = clientIp;
        this.appName = appName;
        this.environmentName = environmentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhitelistResponse that = (WhitelistResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(clientName, that.clientName) && Objects.equals(clientIp, that.clientIp) && Objects.equals(appName, that.appName) && Objects.equals(environmentName, that.environmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName, clientIp, appName, environmentName);
    }

    @Override
    public String toString() {
        return "WhitelistResponse{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", appName='" + appName + '\'' +
                ", environmentName='" + environmentName + '\'' +
                '}';
    }
}
