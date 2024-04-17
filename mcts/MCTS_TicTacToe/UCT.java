package edu.neu.coe.info6205.mcts.MCTS_TicTacToe;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UCT {

    public static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return (nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }

    static Node findBestNodeWithUCT(Node node) {
        int parentVisit = node.getState().getVisitCount();
        return Collections.max(
                node.getChildArray(),
                Comparator.comparing(c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount())));
    }

    // add function here to print UCT values
    public static void printUctValues(Node node){
        int parentVisit = node.getState().getVisitCount();
        List<Node> childArray = node.getChildArray();

        for(Node child : childArray){
            double uctValue =  uctValue(parentVisit, child.getState().getWinScore(), child.getState().getVisitCount());
            System.out.println("UCT value for child node ("+ child.toString() +"): " + uctValue);
        }

    }
}