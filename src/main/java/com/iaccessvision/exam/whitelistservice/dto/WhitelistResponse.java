package com.iaccessvision.exam.whitelistservice.dto;

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
