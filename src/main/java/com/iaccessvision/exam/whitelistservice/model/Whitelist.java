package com.iaccessvision.exam.whitelistservice.model;

import javax.persistence.*;

@Entity
public class Whitelist {
    @Id
    @SequenceGenerator(name = "whitelist_id_sequence", sequenceName = "whitelist_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "whitelist_id_sequence")
    private Integer id;

    @Column(name = "client_name")
    private String clientName;
    @Column(name = "client_ip")
    private String clientIp;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", referencedColumnName = "id")
    private App app;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_id", referencedColumnName = "id")
    private Environment environment;

    public Whitelist() {}

    public Whitelist(Integer id, String clientName, String clientIp, App app, Environment environment) {
        this.id = id;
        this.clientName = clientName;
        this.clientIp = clientIp;
        this.app = app;
        this.environment = environment;
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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "Whitelist{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", app=" + app +
                ", environment=" + environment +
                '}';
    }
}
