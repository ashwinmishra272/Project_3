package com.sscfacilitylocation.common;

import com.sscfacilitylocation.algorithms.greedy.GreedyStrategy;
import com.sscfacilitylocation.algorithms.localsearch.LocalSearchStrategy;
import com.sscfacilitylocation.algorithms.metaheuristic.tabusearch.TabuSearchStrategy;
import com.sscfacilitylocation.utility.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Problem {

    private int numOfFacilities;
    private int numOfCustomers;
    private float[] facilityCapacities;
    private float[] facilityFixedCosts;
    private float[] customerDemands;
    private float[][] facilityToCustomerCosts;
    private float optimumValue;
    private GreedyStrategy greedy;
    private LocalSearchStrategy localSearch;
    private TabuSearchStrategy tabuSearch;
    private Solution solution;

    public Problem(String instancePath, GreedyStrategy greedy, LocalSearchStrategy localSearch, TabuSearchStrategy tabuSearch) {
        this.greedy = greedy;
        this.localSearch = localSearch;
        this.tabuSearch = tabuSearch;

        try {
            BufferedReader inFile = new BufferedReader(new FileReader(instancePath));
            String line = inFile.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line);

            // Load the number of possible facilities and the number of customer
            numOfFacilities = Integer.parseInt(tokenizer.nextToken());
            numOfCustomers = Integer.parseInt(tokenizer.nextToken());

            // Load facility capacities and fixed costs
            facilityCapacities = new float[numOfFacilities];
            facilityFixedCosts = new float[numOfFacilities];
            for (int j = 0; j < numOfFacilities; j++) {
                line = inFile.readLine();
                tokenizer = new StringTokenizer(line);
                facilityCapacities[j] = Float.parseFloat(tokenizer.nextToken());
                facilityFixedCosts[j] = Float.parseFloat(tokenizer.nextToken());
            }

            // Load customer demands
            customerDemands = new float[numOfCustomers];
            line = inFile.readLine();
            tokenizer = new StringTokenizer(line);
            for (int i = 0; i < numOfCustomers; i++) {
                customerDemands[i] = Float.parseFloat(tokenizer.nextToken());
            }

            // Load the facility to customer costs
            facilityToCustomerCosts = new float[numOfFacilities][numOfCustomers];
            for (int j = 0; j < numOfFacilities; j++) {
                line = inFile.readLine();
                tokenizer = new StringTokenizer(line);
                for (int i = 0; i < numOfCustomers; i++) {
                    facilityToCustomerCosts[j][i] = Float.parseFloat(tokenizer.nextToken());
                }
            }

            // Load optimum value if present
            line = inFile.readLine();
            if (line != null) {
                tokenizer = new StringTokenizer(line);
                optimumValue = Float.parseFloat(tokenizer.nextToken());
            } else {
                optimumValue = 0;
            }

            inFile.close();
        } catch (IOException e) {
            Console.println("Cannot read the instance file: " + e.getMessage());
        }
    }

    public int getNumOfFacilities() {
        return numOfFacilities;
    }

    public int getNumOfCustomers() {
        return numOfCustomers;
    }

    public float[] getCustomerDemands() {
        return customerDemands;
    }

    public float[] getFacilityCapacities() {
        return facilityCapacities;
    }

    public float[] getFacilityFixedCosts() {
        return facilityFixedCosts;
    }

    public float[][] getFacilityToCustomerCosts() {
        return facilityToCustomerCosts;
    }

    public float getOptimumValue() {
        return optimumValue;
    }

    public Solution getSolution() {
        return solution;
    }

    public void solveWithGreedy() {
        solution = greedy.run(this);
    }

    public void performLocalSearch() throws RuntimeException {
        if (solution == null) {
            throw new RuntimeException("Cannot perform a Local Search without an initial solution");
        }

        solution = localSearch.run(solution);
        Console.println("\nNo more improvement feasible.");
    }

    public void performTabuSearch() throws RuntimeException {
        if (solution == null) {
            throw new RuntimeException("Cannot perform a Tabu Search without an initial solution");
        }

        switch (tabuSearch.getTabuType()) {
            case CUSTOMER: {
                solution = tabuSearch.run(solution, numOfCustomers / 3, 100);
                break;
            }
            case FACILITY: {
                solution = tabuSearch.run(solution, numOfFacilities / 4, 40);
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder();
        sb.append("Facilities: ").append(numOfFacilities)
                .append(" - Customers: ").append(numOfCustomers);

        sb.append("\n").append("Facility Capacities: ");
        for (int j = 0; j < numOfFacilities; j++) {
            sb.append(facilityCapacities[j]).append(" ");
        }

        sb.append("\n").append("Facility Fixed Costs: ");
        for (int j = 0; j < numOfFacilities; j++) {
            sb.append(facilityFixedCosts[j]).append(" ");
        }

        sb.append("\n").append("Customer Demands: ");
        for (int i = 0; i < numOfCustomers; i++) {
            sb.append(customerDemands[i]).append(" ");
        }
        sb.append("\n").append("Facility to Customer Costs: ").append("\n");
        for (int j = 0; j < numOfFacilities; j++) {
            sb.append("\n").append("Facility ").append(j).append(":").append("\n");
            for (int i = 0; i < numOfCustomers; i++) {
                sb.append(i).append(") ").append(facilityToCustomerCosts[j][i]).append("\n");
            }
        }
        return sb.toString();
    }

}
