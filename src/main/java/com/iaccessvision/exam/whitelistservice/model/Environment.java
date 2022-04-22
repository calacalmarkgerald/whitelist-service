package com.iaccessvision.exam.whitelistservice.model;

import javax.persistence.*;

@Entity
public class Environment {

    @Id
    @SequenceGenerator(name = "environment_id_sequence", sequenceName = "environment_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "environment_id_sequence")
    private Integer id;
    @Column(name = "environment_name")
    private String name;

    public Environment() {}

    public Environment(Integer id, String name) {
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
        return "Environment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
