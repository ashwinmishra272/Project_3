package com.sscfacilitylocation.entity;

import java.util.*;

public class Facility implements Cloneable {

    private int id;
    private float fixedCost;
    private HashMap<Integer, Float> customerCosts;
    private HashSet<Customer> servedCustomers;
    private float capacity;
    private float residualCapacity;

    public Facility(int id, float fixedCost, float capacity) {
        this.id = id;
        this.fixedCost = fixedCost;
        this.capacity = capacity;
        this.residualCapacity = capacity;
        customerCosts = new HashMap<>();
        servedCustomers = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public float getFixedCost() {
        return fixedCost;
    }

    public HashSet<Customer> getServedCustomers() {
        return servedCustomers;
    }

    public HashMap<Integer, Float> getCustomerCosts() {
        return customerCosts;
    }

    public void setCustomerCosts(HashMap<Integer, Float> customerCosts) {
        this.customerCosts = customerCosts;
    }

    public float getCapacity() {
        return capacity;
    }

    public void addCustomers(Collection<Customer> newCustomers) {
        newCustomers.forEach(customer -> {
            if (servedCustomers.add(customer)) residualCapacity -= customer.getDemand();
        });
    }

    public void removeCustomers(Collection<Customer> oldCustomers) {
        oldCustomers.forEach(customer -> {
            if (servedCustomers.remove(customer)) residualCapacity += customer.getDemand();
        });
    }

    public void addCustomer(Customer newCustomer) {
        if (servedCustomers.add(newCustomer)) residualCapacity -= newCustomer.getDemand();
    }

    public void removeCustomer(Customer oldCustomer) {
        if (servedCustomers.remove(oldCustomer)) residualCapacity += oldCustomer.getDemand();
    }

    public float getResidualCapacity() {
        return residualCapacity;
    }

    public float getWholeServingCost() {
        return fixedCost + customerCosts.values().stream().reduce(0.0F, Float::sum);
    }

    public Customer getWorstCustomer() {
        return getWorstCustomerIn(servedCustomers);
    }

    public Customer getWorstCustomer(Queue<Customer> tabuList) {
        Set<Customer> possibleCustomers = new HashSet<>(servedCustomers);
        possibleCustomers.removeAll(tabuList);
        return getWorstCustomerIn(possibleCustomers);
    }

    private Customer getWorstCustomerIn(Set<Customer> possibleCustomers) {
        Customer worstCustomer = null;
        float worstCost = -1;

        for (Customer c : possibleCustomers) {
            float customerCost = customerCosts.get(c.getId());

            if (customerCost > worstCost) {
                worstCost = customerCost;
                worstCustomer = c;
            }
            else if (customerCost == worstCost && c.getDemand() < worstCustomer.getDemand()) {
                worstCustomer = c;
            }
        }

        return worstCustomer;
    }

    @Override
    public Object clone() {
        try {
            Facility facility = (Facility) super.clone();
            facility.id = id;
            facility.fixedCost = fixedCost;
            facility.customerCosts = new HashMap<>();
            this.customerCosts.forEach((id, cost) -> {
                facility.customerCosts.put(id, cost);
            });
            facility.servedCustomers = new HashSet<>();
            this.servedCustomers.forEach(customer -> {
                facility.servedCustomers.add((Customer) customer.clone());
            });
            facility.capacity = capacity;
            facility.residualCapacity = residualCapacity;

            return facility;
        } catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Facility)) return false;
        Facility facility = (Facility) o;
        return id == facility.id &&
                Float.compare(facility.fixedCost, fixedCost) == 0 &&
                Float.compare(facility.capacity, capacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fixedCost, capacity);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}