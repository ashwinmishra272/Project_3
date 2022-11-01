package com.sscfacilitylocation.algorithms.metaheuristic.tabusearch;

import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.ImprovementGraph;
import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.Node;
import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.SolutionImprovement;
import com.sscfacilitylocation.common.Solution;
import com.sscfacilitylocation.entity.Customer;

import java.util.List;

public class CustomerTabuSearch extends BaseTabuSearch {

    public CustomerTabuSearch() {
        super();
    }

    public SolutionImprovement getSolutionImprovement(Solution currentSolution, boolean aspiration) {
        ImprovementGraph improvementGraph;

        if (aspiration) improvementGraph = new ImprovementGraph(currentSolution);
        else improvementGraph = new ImprovementGraph(currentSolution, TabuType.CUSTOMER, this.tabuList);

        List<Node> cycle = improvementGraph.getBestExchangeCycle();

        if (cycle != null) return new SolutionImprovement(cycle);

        return null;
    }

    public void updateTabuList(SolutionImprovement solutionImprovement) {
        for (Customer customer : solutionImprovement.getInvolvedCustomers()) {
            if (!this.tabuList.contains(customer)) {
                this.tabuList.add(customer);
            }
        }
        while (this.tabuList.size() > this.tabuListLength) {
            this.tabuList.poll();
        }
    }

    @Override
    public TabuType getTabuType() {
        return TabuType.CUSTOMER;
    }
}
