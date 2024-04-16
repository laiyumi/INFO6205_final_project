package edu.neu.coe.info6205.mcts.MCTS_TicTacToe;

public class Main {
    public static final int NUM_GAMES = 5;

    public static void main(String[] args) {

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
            
            // use mcts to do the move
            for (int i = 0; i < totalMoves; i++) {

                board = mcts.findNextMove(board, player);
//                System.out.println("move: " + i);
//                board.printBoard();

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
            System.out.println("-----");

        }

        System.out.println("p1 wins: " + p1Wins);
        System.out.println("p2 wins: " + p2Wins);
        System.out.println("Draws: " + draws);
    }
}
