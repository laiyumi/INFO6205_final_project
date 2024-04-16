package edu.neu.coe.info6205.mcts.MCTS_TicTacToe;

public class Main {
    public static final int NUM_GAMES = 20;

    public static void main(String[] args) {

        int p1Wins = 0;
        int p2Wins = 0;
        int draws = 0;

        for (int game = 0; game < NUM_GAMES; game++) {
            MCTS mcts = new MCTS();
            Board board = new Board();
            int player = Board.P1;
            int totalMoves = Board.DEFAULT_BOARD_SIZE * Board.DEFAULT_BOARD_SIZE;
            
            // use mcts to do the move
            for (int i = 0; i < totalMoves; i++) {
                board = mcts.findNextMove(board, player);
                System.out.println("move: " + i);
                board.printBoard();

                if (board.checkStatus() != Board.IN_PROGRESS) {
                    break;
                }
                // switch player in turn
                player = 3 - player;
            }
            int winStatus = board.checkStatus();
//            System.out.println("final board");
//            board.printBoard();
            System.out.println("-----");


            // check the win results
            if (winStatus == Board.P1) {
                p1Wins++;
            } else if (winStatus == Board.P2) {
                p2Wins++;
            } else {
                draws++;
            }
        }

        System.out.println("player 1 wins: " + p1Wins);
        System.out.println("player 2 wins: " + p2Wins);
        System.out.println("Draws: " + draws);
    }
}
