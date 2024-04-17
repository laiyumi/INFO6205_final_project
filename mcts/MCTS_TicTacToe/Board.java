package edu.neu.coe.info6205.mcts.MCTS_TicTacToe;


import java.util.ArrayList;
import java.util.List;

public class Board {
    int[][] boardValues;
    int totalMoves;

    public static final int WINNING_COUNT = 4;   // Connect Four

    public static final int DEFAULT_BOARD_SIZE = 6;

    public static final int IN_PROGRESS = -1;
    public static final int DRAW = 0;
    public static final int P1 = 1;
    public static final int P2 = 2;


    public Board(int row, int col) {
        boardValues = new int[row][col];
    }

    public Board(int[][] boardValues) {
        this.boardValues = boardValues;
    }

    public Board(int[][] boardValues, int totalMoves) {
        this.boardValues = boardValues;
        this.totalMoves = totalMoves;
    }

    public Board(Board board) {
        int rowLength = board.getBoardValues().length;
        int columnLength = board.getBoardValues()[0].length;
        this.boardValues = new int[rowLength][columnLength];
        int[][] boardValues = board.getBoardValues();
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                this.boardValues[i][j] = boardValues[i][j];
            }
        }
    }

    // checking each row from the bottom to the top in the given column p.getX())
    public void performMove(int player, Position p) {
        for(int i = DEFAULT_BOARD_SIZE-1; i >= 0; i--) {
            if(boardValues[i][p.getY()] == 0) {
                this.totalMoves++;
                boardValues[i][p.getY()] = player;
                break;
            }
        }
    }


    public int checkStatus() {
        // Check horizontal lines
        for (int i = 0; i < DEFAULT_BOARD_SIZE; i++)
            for (int j = 0; j < boardValues[0].length - 3; j++)
                if (isConsecutiveFour(boardValues, i, j, 0, 1)) return boardValues[i][j];

        // Check vertical lines
        for (int i = 0; i < boardValues[0].length; i++)
            for (int j = 0; j < DEFAULT_BOARD_SIZE-3; j++)
                if (isConsecutiveFour(boardValues, j, i, 1, 0)) return boardValues[j][i];

        // Check major diagonal (lower part)
        for (int i = 0; i < DEFAULT_BOARD_SIZE-3; i++)
            for (int j = 0; j < boardValues[0].length - 3; j++)
                if (isConsecutiveFour(boardValues, i, j, 1, 1)) return boardValues[i][j];

        // Check major diagonal (upper part)
        for (int i = 3; i < DEFAULT_BOARD_SIZE; i++)
            for (int j = 0; j <= boardValues[0].length - 4; j++)
                if (isConsecutiveFour(boardValues, i, j, -1, 1)) return boardValues[i][j];

        // If no consecutive four has been found, we check if there is still some place on the board
        if (getEmptyPositions().size() > 0){
            return IN_PROGRESS;
        } else {
            return DRAW;
        }
    }


    private boolean isConsecutiveFour(int[][] values, int r, int c, int dr, int dc) {
        boolean consecutiveFour = true;
        int value = values[r][c];  // Cell value

        for (int i = 0; i < WINNING_COUNT; i++)
            if (values[r + dr * i][c + dc * i] != value) {
                consecutiveFour = false;
                break;
            }
        if(value == 0)    // Empty cells cannot be winning points
            return false;

        return consecutiveFour;
    }

    public int[][] getBoardValues() {
        return boardValues;
    }

    public void setBoardValues(int[][] boardValues) {
        this.boardValues = boardValues;
    }

    public void printBoard() {
        int rowSize = this.boardValues.length;      // number of rows
        int columnSize = this.boardValues[0].length;  // number of columns

        // Print column numbers
        for (int column = 0; column < columnSize; column++) {
            System.out.print(column + " ");
        }

        System.out.println("\n--------------");

        // Print board values
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                System.out.print(boardValues[i][j] + " ");
            }
            System.out.println();
        }
    }


    // get the valid positions
    public List<Position> getEmptyPositions() {
        int rowSize = this.boardValues.length;      // number of rows
        int columnSize = this.boardValues[0].length;  // number of columns
        List<Position> emptyPositions = new ArrayList<>();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (boardValues[i][j] == 0) {
                    emptyPositions.add(new Position(i, j));
                }
            }
        }
        return emptyPositions;
    }



    public void printStatus() {
        switch (this.checkStatus()) {
            case P1:
                System.out.println("Player 1 wins");
                break;
            case P2:
                System.out.println("Player 2 wins");
                break;
            case DRAW:
                System.out.println("Game Draw");
                break;
            case IN_PROGRESS:
                System.out.println("Game In Progress");
                break;
        }
    }
}