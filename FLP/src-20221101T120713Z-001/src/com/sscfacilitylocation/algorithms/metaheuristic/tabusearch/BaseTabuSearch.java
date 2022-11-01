package com.sscfacilitylocation.algorithms.metaheuristic.tabusearch;

import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.SolutionImprovement;
import com.sscfacilitylocation.common.Solution;
import com.sscfacilitylocation.utility.Console;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public abstract class BaseTabuSearch implements TabuSearchStrategy {

    private Solution bestSolution;
    Queue tabuList;
    int tabuListLength;
    private int numOfIterationWithoutImprovement;
    private boolean aspiration;

    public BaseTabuSearch() {
        tabuList = new LinkedList<>();
        numOfIterationWithoutImprovement = 0;
        aspiration = false;
    }

    @Override
    public Solution run(Solution initialSolution, int tabuListLength, int stopWithoutImprovementAfter) {
        bestSolution = initialSolution;
        this.tabuListLength = tabuListLength;

        Solution tempSolution = (Solution) bestSolution.clone();
        Solution aspirationSolution;

        int k = 1;
        Console.println("\nITERATION " + k);
        Console.println("TabuList = " + Arrays.toString(tabuList.toArray()));
        Console.println("\nNow exploring neighborhood of");
        Console.println(tempSolution);
        SolutionImprovement solutionImprovement = getSolutionImprovement(tempSolution, aspiration);

        while (numOfIterationWithoutImprovement < stopWithoutImprovementAfter && solutionImprovement != null) {
            if (aspiration) {
                aspirationSolution = (Solution) tempSolution.clone();
                SolutionImprovement solutionImprovementWithAspiration = getSolutionImprovement(aspirationSolution, aspiration);
                Console.println("ASPIRATION");
                if (updateSituation(aspirationSolution, solutionImprovementWithAspiration)) { // Aspiration take me to a new best solution
                    Console.println("Best solution reached with aspiration.");
                    tempSolution = aspirationSolution;
                } else {
                    numOfIterationWithoutImprovement -= 1; // Decrease the counter increased by the unsuccessful aspiration
                    updateSituation(tempSolution, solutionImprovement);
                }
            } else {
                updateSituation(tempSolution, solutionImprovement);
            }

            k += 1;
            Console.println("\nITERATION " + k);
            Console.println("TabuList = " + Arrays.toString(tabuList.toArray()));
            Console.println("\nNow exploring neighborhood of");
            Console.println(tempSolution);
            solutionImprovement = getSolutionImprovement(tempSolution, false);
        }

        Console.println("\nStopping tabu search as it's not possible to update the best solution from " + numOfIterationWithoutImprovement + " iterations.");

        return bestSolution;
    }

    public boolean updateSituation(Solution tempSolution, SolutionImprovement solutionImprovement) {
        tempSolution.applyImprovement(solutionImprovement);

        updateTabuList(solutionImprovement);

        if (tempSolution.getCost() > bestSolution.getCost()) { // Found a worst solution
            aspiration = true;
            numOfIterationWithoutImprovement += 1;
        } else if (tempSolution.getCost() < bestSolution.getCost()) { // Update best solution
            bestSolution = (Solution) tempSolution.clone();
            numOfIterationWithoutImprovement = 0;
            aspiration = false;
            Console.println("\nNew best solution");
            Console.println(bestSolution);
            return true;
        }

        return false;
    }

    abstract SolutionImprovement getSolutionImprovement(Solution currentSolution, boolean aspiration);

    abstract void updateTabuList(SolutionImprovement solutionImprovement);
}
