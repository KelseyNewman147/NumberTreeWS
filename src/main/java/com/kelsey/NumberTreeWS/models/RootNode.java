package com.kelsey.NumberTreeWS.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "root_node")
public class RootNode {
    @Id
    int id;

    @OneToMany(mappedBy = "rootNode")
    @JsonManagedReference
    List<Factory> factories;

    public RootNode() {
    }

    public RootNode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public void setFactories(List<Factory> factories) {
        this.factories = factories;
    }
}

