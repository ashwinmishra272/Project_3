package com.sscfacilitylocation.algorithms.metaheuristic.tabusearch;

import com.sscfacilitylocation.common.Solution;

public interface TabuSearchStrategy {
    Solution run(Solution initialSolution, int tabuListLength, int stopWithoutImprovementAfter);
    TabuType getTabuType();
}
