package com.iaccessvision.exam.whitelistservice.model;

import javax.persistence.*;

@Entity
public class App {

    @Id
    @SequenceGenerator(name = "app_id_sequence", sequenceName = "app_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_id_sequence")
    private Integer id;
    @Column(name = "app_name")
    private String name;

    public App() {}

    public App(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "App{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
