package com.sscfacilitylocation.algorithms.localsearch.improvementgraph;

import com.sscfacilitylocation.entity.Customer;
import com.sscfacilitylocation.entity.Facility;

import java.util.Objects;
import java.util.Queue;

public class Node {

    private String name;
    private Facility facility;
    private Customer leavingCustomer;
    private float distanceFromSource;
    private Node predecessor;
    private NodeType type;

    Node(Facility facility, NodeType type) {
        switch (type) {
            case REGULAR: {
                this.facility = facility;
                distanceFromSource = Float.MAX_VALUE;
                leavingCustomer = facility.getWorstCustomer();
                name = String.valueOf(facility.getId());
                break;
            }
            case DUMMY: {
                this.facility = facility;
                distanceFromSource = Float.MAX_VALUE;
                leavingCustomer = null;
                name = 'd' + String.valueOf(facility.getId());
                break;
            }
            case SOURCE: {
                this.facility = null;
                distanceFromSource = 0.0F;
                leavingCustomer = null;
                name = "s";
                break;
            }
        }
        predecessor = null;
        this.type = type;
    }

    Node() {
        type = NodeType.SOURCE;
        predecessor = null;
        this.facility = null;
        distanceFromSource = 0.0F;
        leavingCustomer = null;
        name = "s";
    }

    Node(Facility facility, NodeType type, Queue<Customer> tabuList) {
        switch (type) {
            case REGULAR: {
                this.facility = facility;
                distanceFromSource = Float.MAX_VALUE;
                leavingCustomer = facility.getWorstCustomer(tabuList);
                name = String.valueOf(facility.getId());
                break;
            }
            case DUMMY: {
                this.facility = facility;
                distanceFromSource = Float.MAX_VALUE;
                leavingCustomer = null;
                name = 'd' + String.valueOf(facility.getId());
                break;
            }
            case SOURCE: {
                this.facility = null;
                distanceFromSource = 0.0F;
                leavingCustomer = null;
                name = "s";
                break;
            }
        }
        predecessor = null;
        this.type = type;
    }

    String getName() {
        return name;
    }

    public Facility getFacility() {
        return facility;
    }

    public Customer getLeavingCustomer() {
        return leavingCustomer;
    }

    float getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(float distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public NodeType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return name.equals(node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
