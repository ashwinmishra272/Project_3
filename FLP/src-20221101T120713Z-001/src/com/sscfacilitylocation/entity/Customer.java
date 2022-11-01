package com.sscfacilitylocation.entity;


import java.util.Objects;

public class Customer implements Cloneable {

    private int id;
    private float demand;

    public Customer (int id, float demand) {
        this.id = id;
        this.demand = demand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDemand() {
        return demand;
    }

    public void setDemand(float demand) {
        this.demand = demand;
    }

    @Override
    public Object clone() {
        try {
            Customer customer = (Customer) super.clone();
            customer.id = id;
            customer.demand = demand;
            return customer;
        } catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                Float.compare(customer.demand, demand) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, demand);
    }

    public String toString() {
        return String.valueOf(id);
    }
}
