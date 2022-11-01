package com.sscfacilitylocation.algorithms.metaheuristic.tabusearch;

import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.ImprovementGraph;
import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.Node;
import com.sscfacilitylocation.algorithms.localsearch.improvementgraph.SolutionImprovement;
import com.sscfacilitylocation.common.Solution;
import com.sscfacilitylocation.entity.Facility;

import java.util.List;

public class FacilityTabuSearch extends BaseTabuSearch {

    public FacilityTabuSearch() {
        super();
    }

    @Override
    public SolutionImprovement getSolutionImprovement(Solution currentSolution, boolean aspiration) {
        ImprovementGraph improvementGraph;

        if (aspiration) improvementGraph = new ImprovementGraph(currentSolution);
        else improvementGraph = new ImprovementGraph(currentSolution, TabuType.FACILITY, this.tabuList);

        List<Node> cycle = improvementGraph.getBestExchangeCycle();

        if (cycle != null) return new SolutionImprovement(cycle);

        return null;
    }

    @Override
    public void updateTabuList(SolutionImprovement solutionImprovement) {
        for (Facility facility : solutionImprovement.getInvolvedFacilities()) {
            if (!this.tabuList.contains(facility)) {
                this.tabuList.add(facility);
            }
        }
        while (this.tabuList.size() > this.tabuListLength) {
            this.tabuList.poll();
        }
    }

    @Override
    public TabuType getTabuType() {
        return TabuType.FACILITY;
    }
}
