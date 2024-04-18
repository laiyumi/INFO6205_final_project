package edu.neu.coe.info6205.mcts.MCTS_TicTacToe;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final int NUM_GAMES = 2;

    public static void main(String[] args) {

        boolean human_player = false;

        int p1Wins = 0;
        int p2Wins = 0;
        int draws = 0;

        for (int game = 0; game < NUM_GAMES; game++) {

            MCTS mcts = new MCTS();
            int row = 6;
            int col = 7;
            Board board = new Board(6,7); // Connect 4 is usually played 6 rows by 7 columns
            int player = Board.P1;
            int totalMoves = row * col;
            //Map<Integer, Double> columnWinRates = mcts.findNextMoveWinRate(board, Board.P1);

            // use mcts to do the move
            for (int i = 0; i < totalMoves; i++) {

                if (player == Board.P1) {
                    long startTime = System.nanoTime();
                    board = mcts.findNextMove(board, player);
                    long endTime = System.nanoTime();

                    long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                    //System.out.println("move: " + i);
                    System.out.println("\n" + "Board after move: " + i );
                    System.out.println("0 1 2 3 4 5 6");
                    board.printBoard();
                    System.out.println("Time taken for move: " + duration + " ms");
                    System.out.println();
                }
                else {
                    if (human_player) {
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
                        //System.out.println("------------------");
                        board.printBoard();
                    }else{
                        long startTime = System.nanoTime();
                        board = mcts.findNextMove(board, player);
                        long endTime = System.nanoTime();

                        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
                        //System.out.println("move: " + i);
                        System.out.println("\n" + "Board after move: " + i);
                        System.out.println("0 1 2 3 4 5 6");
                        board.printBoard();
                        System.out.println("Time taken for move: " + duration + " ms");
                    }
                }

                if (board.checkStatus() != Board.IN_PROGRESS) {
                    break;
                }
                // switch player in turn
                player = 3 - player;
            }

            int winStatus = board.checkStatus();




            // check the win results
            if (winStatus == Board.P1) {
                p1Wins++;
                System.out.println("p1 wins! ");

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

        System.out.println("p1 wins: " + p1Wins);
        System.out.println("p2 wins: " + p2Wins);
        System.out.println("Draws: " + draws);
    }
}
