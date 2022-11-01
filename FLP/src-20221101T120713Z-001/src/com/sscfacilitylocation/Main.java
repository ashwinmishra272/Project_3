package com.sscfacilitylocation;

import com.sscfacilitylocation.algorithms.greedy.LowerCostFacilityAndCustomerGreedyStrategy;
import com.sscfacilitylocation.algorithms.localsearch.SingleCustomerExchangeLocalSearchStrategy;
import com.sscfacilitylocation.algorithms.metaheuristic.tabusearch.CustomerTabuSearch;
import com.sscfacilitylocation.common.Problem;
import com.sscfacilitylocation.common.Solution;
import com.sscfacilitylocation.utility.Console;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final String INSTANCE_PATH = "problem_instances/OR-Library_Instances/cap63";
        Problem problem = new Problem(
                INSTANCE_PATH,
                new LowerCostFacilityAndCustomerGreedyStrategy(),
                new SingleCustomerExchangeLocalSearchStrategy(),
                new CustomerTabuSearch()
                );
        Console.println(problem);

        Scanner userIn = new Scanner(System.in);
        System.out.print("\nPress ENTER to apply the greedy algorithm...");
        userIn.nextLine();
        Console.println("Greedy solution: ");
        problem.solveWithGreedy();

        Solution solution = problem.getSolution();

        if (solution != null) {
            Console.println(solution);

            System.out.print("\nPress ENTER to start the local search...");
            userIn.nextLine();
            Console.println("\nComputing Local Search: ");
            problem.performLocalSearch();
            Console.println(problem.getSolution());

            System.out.print("\nPress ENTER to start the tabu search...");
            userIn.nextLine();
            Console.println("\nComputing Tabu Search: ");
            problem.performTabuSearch();
            Console.println(problem.getSolution());
        } else {
            Console.println("Problem is unsatisfiable.");
        }
    }
}