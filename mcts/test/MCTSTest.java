package edu.neu.coe.info6205.mcts.test;

import edu.neu.coe.info6205.mcts.MCTS_Connect4.Board;
import edu.neu.coe.info6205.mcts.MCTS_Connect4.MCTS;
import edu.neu.coe.info6205.mcts.MCTS_Connect4.Position;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MCTSTest {
    @Test
    public void testMCTSFindNextMoveOnEmptyBoard() {
        Board board = new Board(6, 7);
        MCTS mcts = new MCTS();
        Board result = mcts.findNextMove(board, Board.P1);
        assertTrue(result.getMoveCount() == 1); // Assuming there's a method to count moves
    }

    @Test
    public void testGameOutcomeWinRow() {
        Board board = new Board(6, 7);
        board.performMove(Board.P1, new Position(0, 0));
        board.performMove(Board.P1, new Position(1, 0));
        board.performMove(Board.P1, new Position(2, 0));
        board.performMove(Board.P1, new Position(3, 0)); // Simulate a winning condition

        assertEquals(Board.P1, board.checkStatus());
    }

    @Test
    public void testGameOutcomeWinColumn() {
        Board board = new Board(6, 7);
        board.performMove(Board.P1, new Position(0, 0));
        board.performMove(Board.P1, new Position(0, 1));
        board.performMove(Board.P1, new Position(0, 2));
        board.performMove(Board.P1, new Position(0, 3)); // Simulate a winning condition

        assertEquals(Board.P1, board.checkStatus());
    }

    @Test
    public void testGameOutcomeWinDiagonal() {
        Board board = new Board(6, 7);
        board.performMove(Board.P1, new Position(0, 0));
        board.performMove(Board.P1, new Position(1, 1));
        board.performMove(Board.P1, new Position(2, 2));
        board.performMove(Board.P1, new Position(3, 3)); // Simulate a winning condition

        assertEquals(Board.P1, board.checkStatus());
    }

    @Test
    public void testMoveTiming() {
        Board board = new Board(6, 7);
        MCTS mcts = new MCTS();
        long startTime = System.nanoTime();
        mcts.findNextMove(board, Board.P1);
        long endTime = System.nanoTime();
        assertTrue(TimeUnit.NANOSECONDS.toMillis(endTime - startTime) < 1000); // Ensure move is computed within 1000 ms
    }

    @Test
    public void testP1MCTSFindWinningMove() {
        // Initialize the board with a winning scenario for P1
        int[][] preSetBoard = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 2, 2, 2, 0, 0, 0},
                {2, 2, 1, 1, 1, 0, 0},
                {2, 1, 2, 2, 1, 1, 1}
        };
        Board board = new Board(preSetBoard);
        MCTS mcts = new MCTS();

        // MCTS to play as P1
        board = mcts.findNextMove(board, Board.P1);

        // After MCTS's move, check if P1 wins
        int winStatus = board.checkStatus();
        assertEquals(Board.P1, winStatus);
    }

    @Test
    public void testP2MCTSFindWinningMove() {
        // Initialize the board with a winning scenario for P2
        int[][] preSetBoard = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {1, 2, 2, 2, 0, 0, 0},
                {2, 2, 1, 1, 1, 0, 0},
                {2, 1, 2, 2, 1, 1, 1}
        };
        Board board = new Board(preSetBoard);
        MCTS mcts = new MCTS();

        // MCTS to play as P2
        board = mcts.findNextMove(board, Board.P2);

        // After MCTS's move, check if P2 wins
        int winStatus = board.checkStatus();
        assertEquals(Board.P2, winStatus);
    }

    @Test
    public void testWinningMoveWith100PercentWinRate() {
        // Set up the board in a nearly winning position for Player 1
        int[][] preSetBoard = {
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0},
                {2, 1, 0, 1, 1, 0, 0}
        };
        Board board = new Board(preSetBoard);
        MCTS mcts = new MCTS();


        double winRate = mcts.findNextMoveWinRate(board, Board.P1);

        assertEquals(100.0, winRate);
    }

}
