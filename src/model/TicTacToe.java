package model;
import java.util.Scanner;

public class TicTacToe {
    private static final int BOARD_SIZE = 3;
    private static final char PLAYER = 'X';
    private static final char COMPUTER = 'O';
    private static final char EMPTY = ' ';
    private static final int MAX_MOVES = BOARD_SIZE * BOARD_SIZE;

    private char[][] board;
    private boolean gameEnded;
    private char winner;

    public TicTacToe() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        resetBoard();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        int row, column;

        System.out.println("Welcome to the Tic-Tac-Toe game!");

        while (!gameEnded) {
            displayBoard();

            if (winner == EMPTY) {
                if (isPlayerTurn()) {
                    System.out.println("Your turn (player).");
                    System.out.print("Enter the row (1-" + BOARD_SIZE + "): ");
                    row = scanner.nextInt() - 1;
                    System.out.print("Enter the column (1-" + BOARD_SIZE + "): ");
                    column = scanner.nextInt() - 1;

                    if (validateMove(row, column)) {
                        markMove(row, column, PLAYER);
                        switchTurn();
                    } else {
                        System.out.println("Invalid move. Try again.");
                    }
                } else {
                    System.out.println("Computer's turn.");
                    playComputer();
                    switchTurn();
                }
            }
        }

        displayBoard();
        if (winner == PLAYER) {
            System.out.println("Congratulations! You win!");
        } else if (winner == COMPUTER) {
            System.out.println("Sorry, you lost. The computer wins.");
        } else {
            System.out.println("It's a tie! The game ended with no winner.");
        }
    }

    private void resetBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }

        gameEnded = false;
        winner = EMPTY;
    }

    private void displayBoard() {
        System.out.println("-------------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
            System.out.println("-------------");
        }
    }

    private boolean isPlayerTurn() {
        int numMoves = countMoves();
        return numMoves % 2 == 0;
    }

    private void switchTurn() {
        int numMoves = countMoves();

        if (numMoves >= 5) {
            if (hasWinner()) {
                gameEnded = true;
                winner = isPlayerTurn() ? PLAYER : COMPUTER;
            } else if (numMoves == MAX_MOVES) {
                gameEnded = true;
                winner = EMPTY; // Tie
            }
        }
    }

    private boolean hasWinner() {
        // Check rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[0][j] != EMPTY && board[0][j] == board[1][j] && board[0][j] == board[2][j]) {
            	return true;
            }
            }
        // Check diagonals
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return true;
        }
        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return true;
        }

        return false;
    }

    private int countMoves() {
        int numMoves = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != EMPTY) {
                    numMoves++;
                }
            }
        }
        return numMoves;
    }

    private boolean validateMove(int row, int column) {
        if (row < 0 || row >= BOARD_SIZE || column < 0 || column >= BOARD_SIZE) {
            return false;
        }

        return board[row][column] == EMPTY;
    }

    private void markMove(int row, int column, char player) {
        board[row][column] = player;
    }

    private void playComputer() {
        int row, column;

        // Check if the player is about to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (validateMove(i, j)) {
                    board[i][j] = PLAYER;
                    if (hasWinner()) {
                        markMove(i, j, COMPUTER);
                        return;
                    }
                    board[i][j] = EMPTY;
                }
            }
        }

        // If the player is not about to win, select a random empty cell
        do {
            row = (int) (Math.random() * BOARD_SIZE);
            column = (int) (Math.random() * BOARD_SIZE);
        } while (!validateMove(row, column));
        markMove(row, column, COMPUTER);
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}

