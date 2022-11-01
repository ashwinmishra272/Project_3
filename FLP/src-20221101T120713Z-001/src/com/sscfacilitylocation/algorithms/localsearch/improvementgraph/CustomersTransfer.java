package com.sscfacilitylocation.algorithms.localsearch.improvementgraph;

import com.sscfacilitylocation.entity.Customer;
import com.sscfacilitylocation.entity.Facility;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomersTransfer {

    private Set<Customer> movingCustomers;
    private Facility fromFacility;
    private Facility toFacility;

    public CustomersTransfer(Collection<Customer> movingCustomers, Facility fromFacility, Facility toFacility) {
        this.movingCustomers = (HashSet<Customer>) movingCustomers;
        this.fromFacility = fromFacility;
        this.toFacility = toFacility;
    }

    public CustomersTransfer(Customer movingCustomer, Facility fromFacility, Facility toFacility) {
        movingCustomers = new HashSet<>();
        movingCustomers.add(movingCustomer);
        this.fromFacility = fromFacility;
        this.toFacility = toFacility;
    }

    public Set<Customer> getMovingCustomers() {
        return movingCustomers;
    }

    public Facility getFromFacility() {
        return fromFacility;
    }

    public Facility getToFacility() {
        return toFacility;
    }

}
