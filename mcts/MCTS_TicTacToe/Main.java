package edu.neu.coe.info6205.mcts.MCTS_TicTacToe;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final int NUM_GAMES = 400;

    public static void main(String[] args) {
        // switch between human plaer mode and robot players mode
        boolean human_player = false;

        int p1Wins = 0;
        int p2Wins = 0;
        int draws = 0;

        long totalDurationP1 = 0L;
        long totalDurationP2 = 0L;

        for (int game = 0; game < NUM_GAMES; game++) {

            MCTS mcts = new MCTS();
            int row = 6;
            int col = 7;
            Board board = new Board(6,7); // Connect 4 is usually played 6 rows by 7 columns
            int player = Board.P1;
            int totalMoves = row * col;
            long durationP1 = 0L;
            long durationP2 = 0L;
            //Map<Integer, Double> columnWinRates = mcts.findNextMoveWinRate(board, Board.P1);

            // use mcts to do the move

            for (int i = 0; i < totalMoves; i++) {

                long startTime = System.nanoTime();

                if (player == Board.P1 || (!human_player && player == Board.P2)) {
                    board = mcts.findNextMove(board, player);
                } else if(human_player && player == Board.P2){
                    while (true) {
                        System.out.println("Enter column number (0-6): ");
                        Scanner scanner = new Scanner(System.in);
                        int num = scanner.nextInt();
                        Position p = new Position(0, num);
                        try {
                            board.performMove(player, p);
                            break;
                        } catch (Exception e) {
                            System.out.println("Invalid move, try again");
                        }
                    }
                    board.printBoard();
                }

                long endTime = System.nanoTime();
                long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

                if (player == Board.P1) {
                    durationP1 += duration;
                } else {
                    durationP2 += duration;
                }

//                System.out.println("\n" + "Board after move: " + i);
//                System.out.println("0 1 2 3 4 5 6");
//                board.printBoard();
//                System.out.println("Time taken for this move: " + duration + " ms");

                if (board.checkStatus() != Board.IN_PROGRESS) {
                    break;
                }

                player = 3 - player;
            }
            totalDurationP1 += durationP1;
            totalDurationP2 += durationP2;

            int winStatus = board.checkStatus();

            // check the win results
            if (winStatus == Board.P1) {
                p1Wins++;
                System.out.println(human_player ? "Robot wins! " : "Player 1 wins! ");
            } else if (winStatus == Board.P2) {
                p2Wins++;
                System.out.println("p2 wins! ");

            } else {
                draws++;
            }
            System.out.println("final board");
            board.printBoard();
            System.out.println("-------------");

        }

        double averageDurationP1 = (totalDurationP1 * 1.0) / NUM_GAMES;
        double averageDurationP2 = (totalDurationP2 * 1.0) / NUM_GAMES;

        System.out.println("MCTS simulation: 100" + ", run: " + NUM_GAMES);

        System.out.println("Average time taken per game by Player 1: " + averageDurationP1 + " ms");
        System.out.println("Average time taken per game by Player 2: " + averageDurationP2 + " ms");
        System.out.println("Average time taken per game: " + (averageDurationP2 + averageDurationP1)/2 + " ms");

        System.out.println("p1 wins: " + p1Wins);
        System.out.println("p2 wins: " + p2Wins);
        System.out.println("Draws: " + draws);
    }
}
