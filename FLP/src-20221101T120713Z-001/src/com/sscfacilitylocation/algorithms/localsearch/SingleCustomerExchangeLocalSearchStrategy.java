package com.sscfacilitylocation.algorithms.localsearch;

import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.ImprovementGraph;
import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.Node;
import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.SolutionImprovement;
import com.sscfacilitylocation.common.Solution;
import com.sscfacilitylocation.utility.Console;

import java.util.List;

public class SingleCustomerExchangeLocalSearchStrategy implements LocalSearchStrategy {

    @Override
    public Solution run(Solution initialSolution) {
        Solution bestSolution = initialSolution;
        int k = 1;
        Console.println("\nITERATION " + k);
        SolutionImprovement solutionImprovement = getSolutionImprovement(bestSolution);

        while (solutionImprovement != null) {
            bestSolution.applyImprovement(solutionImprovement);
            Console.println("");
            Console.println(bestSolution);
            k += 1;
            Console.println("\nITERATION " + k);
            solutionImprovement = getSolutionImprovement(bestSolution);
        }

        return bestSolution;
    }

    private SolutionImprovement getSolutionImprovement(Solution currentSolution) {
        ImprovementGraph improvementGraph = new ImprovementGraph(currentSolution);
        List<Node> cycle = improvementGraph.getBestImprovingExchangeCycle();

        if (cycle != null) return new SolutionImprovement(cycle);

        return null;
    }
}
