package com.kelsey.NumberTreeWS.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="factories")
public class Factory {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    String name;

    @Column
    int rangeLow;

    @Column
    int rangeHigh;

    @ManyToOne
    @JsonBackReference
    RootNode rootNode;

    @OneToMany(mappedBy = "factory")
    @JsonManagedReference
    List<ChildNode> childNodes;

    public Factory() {
    }

    public Factory(RootNode rootNode, String name, int rangeLow, int rangeHigh) {
        this.rootNode = rootNode;
        this.name = name;
        this.rangeLow = rangeLow;
        this.rangeHigh = rangeHigh;
    }

    public RootNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildNode> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<ChildNode> childNodes) {
        this.childNodes = childNodes;
    }

    public int getRangeLow() {
        return rangeLow;
    }

    public void setRangeLow(int rangeLow) {
        this.rangeLow = rangeLow;
    }

    public int getRangeHigh() {
        return rangeHigh;
    }

    public void setRangeHigh(int rangeHigh) {
        this.rangeHigh = rangeHigh;
    }
}

