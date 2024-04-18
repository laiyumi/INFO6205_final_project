package edu.neu.coe.info6205.mcts.MCTS_TicTacToe;

import java.util.List;

public class MCTS {
    private static final int WIN_SCORE = 1;
    private int opponent;

    private static final int SIMULATION_COUNT = 100;  // higher simulation results in more draws

    // base on the current state, find the next move for the player
    public Board findNextMove(Board board, int playerNo) {

        playerNo = 3 - playerNo;
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.getState().setBoard(board);
        rootNode.getState().setPlayerNo(playerNo);

        for (int i = 0; i < SIMULATION_COUNT; i++) {
            // step 1 - Selection
            Node promisingNode = selectPromisingNode(rootNode);
            // step 2 - Expansion
            if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS)
                expandNode(promisingNode);

            // step 3 - Simulation
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildArray().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            // step 4 - Update
            backPropogation(nodeToExplore, playoutResult);
        }

        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getBoard();
    }

    // use UCT to select the child node
    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        // if current node is not leaf node
        while (node.getChildArray().size() != 0) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }

    // expand the given node to create children nodes from all possible states(positions)
    private void expandNode(Node node) {
        // get all possible states
        List<State> possibleStates = node.getState().getAllPossibleStates();
        // loop over each state to create a corresponding node
        for (State state : possibleStates) {
            Node newNode = new Node(state);
            newNode.setParent(node);
            // switch player
            newNode.getState().setPlayerNo(node.getState().getOpponent());
            // add child node to the parent node
            node.getChildArray().add(newNode);
        }
    }

    // update node scores
    private void backPropogation(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getState().increaseVisit();
            // update score for the current player
            if (tempNode.getState().getPlayerNo() == playerNo)
                tempNode.getState().addScore(WIN_SCORE);
            // start from this node, trace back to the root
            tempNode = tempNode.getParent();
        }
    }

    // conduct a random playout fot a given node
    private int simulateRandomPlayout(Node node) {
        // make a copy of the current node
        Node tempNode = new Node(node);
        State tempState = tempNode.getState();
        int boardStatus = tempState.getBoard().checkStatus();

        // opponent wins
        if (boardStatus == opponent) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }
        // run game until the game ends
        while (boardStatus == Board.IN_PROGRESS) {
            tempState.switchPlayer();
            tempState.randomPlay();
            boardStatus = tempState.getBoard().checkStatus();
        }

        return boardStatus;
    }
}