package com.sscfacilitylocation.algorithms.greedy;

import com.sscfacilitylocation.common.Problem;
import com.sscfacilitylocation.common.Solution;
import com.sscfacilitylocation.entity.Customer;
import com.sscfacilitylocation.entity.Facility;
import com.sscfacilitylocation.utility.Console;

import java.util.*;

public class LowerCostFacilityAndCustomerGreedyStrategy implements GreedyStrategy {

    private Queue<Facility> sortedFacilitiesQueue;
    private Queue<Customer> sortedCustomerQueue;

    @Override
    public Solution run(Problem inputProblem) {
        Solution solution = new Solution(inputProblem);

        HashSet<Customer> toBeAssignedCustomers = new HashSet<>();
        for (int i=0; i < inputProblem.getNumOfCustomers(); i++) {
            toBeAssignedCustomers.add(new Customer(i, inputProblem.getCustomerDemands()[i]));
        }
        sortFacilities(solution.getClosedFacilities().values());

        while (!sortedFacilitiesQueue.isEmpty() && !toBeAssignedCustomers.isEmpty()) {
            Facility bestFacility = sortedFacilitiesQueue.poll();

            if (facilityInd(bestFacility, toBeAssignedCustomers)) {
                Console.println("Opening facility " + bestFacility.getId() + " with service cost for all customers: " + bestFacility.getWholeServingCost());
                solution.openFacility(bestFacility);
                sortCustomers(bestFacility, toBeAssignedCustomers);

                while (!sortedCustomerQueue.isEmpty()) {
                    Customer bestCustomer = sortedCustomerQueue.poll();

                    if (customerInd(bestFacility, bestCustomer)) {
                        solution.addCustomerToFacility(bestCustomer, bestFacility);
                        Console.println("Assigning customer " + bestCustomer.getId() + " to facility " + bestFacility.getId() + " - capacity left: " + bestFacility.getResidualCapacity());
                        toBeAssignedCustomers.remove(bestCustomer);
                    }
                }
                Console.println("No more customers assignable to facility " + bestFacility.getId() + "\n");
            }
        }

        if (!toBeAssignedCustomers.isEmpty()) {
            solution = null;
        }

        return solution;
    }

    private void sortFacilities(Collection<Facility> possibleFacilities) {
        sortedFacilitiesQueue = new PriorityQueue<>(
                possibleFacilities.size(),
                (a, b) -> Float.compare(a.getWholeServingCost(), b.getWholeServingCost())
        );

        sortedFacilitiesQueue.addAll(possibleFacilities);
    }

    private void sortCustomers(Facility facility, Collection<Customer> possibleCustomers) {
        HashMap<Integer, Float> customerCosts = facility.getCustomerCosts();

        sortedCustomerQueue = new PriorityQueue<>(
                possibleCustomers.size(),
                (a, b) -> Float.compare(customerCosts.get(a.getId()), customerCosts.get(b.getId()))
        );

        sortedCustomerQueue.addAll(possibleCustomers);
    }

    private boolean facilityInd(Facility newFacility, Collection<Customer> toBeAssignedCustomers) {
        float minimumDemand = Float.MAX_VALUE;

        for (Customer customer : toBeAssignedCustomers) {
            float demand = customer.getDemand();
            if (demand < minimumDemand) minimumDemand = demand;
        }

        return newFacility.getCapacity() >= minimumDemand;
    }

    private boolean customerInd(Facility currentFacility, Customer newCustomer) {
        return currentFacility.getResidualCapacity() >= newCustomer.getDemand();
    }
}
