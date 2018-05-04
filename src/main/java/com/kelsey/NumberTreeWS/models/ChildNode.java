package com.kelsey.NumberTreeWS.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "child_nodes")
public class ChildNode {
    @Id
    @GeneratedValue
    @Column(name = "child_id")
    int id;

    @Column
    int number;

    @ManyToOne
    @JsonBackReference
    Factory factory;

    public ChildNode() {
    }

    public ChildNode( int number, Factory factory) {
        this.number = number;
        this.factory = factory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }
}

