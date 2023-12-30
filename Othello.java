package オセロ;



import java.util.Scanner;

public class Othello {
    public static final int BLACK = 1;
    public static final int WHITE = -1;
    public static final int EMPTY = 0;
    public static final int BOARD_SIZE = 8;
    public static final int[][] DIRECTIONS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    public static final String[] COLUMN_NAMES = {"a", "b", "c", "d", "e", "f", "g", "h"};

    private int[][] board;
    private int currentPlayer;

    public Othello() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
        currentPlayer = BLACK;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            if (hasValidMove(currentPlayer)) {
                
            	System.out.println("プレイヤー " + (currentPlayer == BLACK ? "黒" : "白") + ", 次の手を入力してください（例：'d3'）:");
            	String move = scanner.nextLine();
            	if (move.length() != 2) {
            	    System.out.println("無効な入力です。もう一度入力してください。");
            	    continue;
            	}
            	int row = move.charAt(1) - '1';
            	int col = getColumnIndex(move.substring(0, 1));
            	if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && isValidMove(row, col, currentPlayer)) {
            	    makeMove(row, col, currentPlayer);
            	    currentPlayer *= -1;
            	} else {
            	    System.out.println("無効な手です。もう一度入力してください。");
            	}
            } else if (hasValidMove(currentPlayer * -1)) {
                System.out.println("プレイヤー " + (currentPlayer == BLACK ? "黒" : "白") + " の有効な手がありません。パスします。");
                currentPlayer *= -1;
            } else {
                break;
            }
        }
        scanner.close();
        printBoard();
        printResult();
    }

    private void printBoard() {
        System.out.print(" ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(" " + COLUMN_NAMES[i]);
        }
        System.out.println();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == BLACK) {
                    System.out.print(" 黒");
                } else if (board[i][j] == WHITE) {
                    System.out.print(" 白");
                } else {
                    System.out.print(" .");
                }
            }
            System.out.println();
        }
    }

    private boolean hasValidMove(int player) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isValidMove(i, j, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidMove(int row, int col, int player) {
        if (board[row][col] != EMPTY) {
            return false;
        }
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            boolean hasOpponentStone = false;
            while (newRow >= 0 && newRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE) {
                if (board[newRow][newCol] == player * -1) {
                    hasOpponentStone = true;
                } else if ((board[newRow][newCol] == player) && hasOpponentStone) {
                    return true;
                } else {
                    break;
                }
                newRow += direction[0];
                newCol += direction[1];
            }
        }
        return false;
    }

    private void makeMove(int row, int col, int player) {
        board[row][col] = player;
        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            boolean hasOpponentStone = false;
            while (newRow >= 0 && newRow < BOARD_SIZE && newCol >= 0 && newCol < BOARD_SIZE) {
                if (board[newRow][newCol] == player * -1) {
                    hasOpponentStone = true;
                } else if ((board[newRow][newCol] == player) && hasOpponentStone) {
                    int changeRow = row + direction[0];
                    int changeCol = col + direction[1];
                    while (changeRow != newRow || changeCol != newCol) {
                        board[changeRow][changeCol] = player;
                        changeRow += direction[0];
                        changeCol += direction[1];
                    }
                    break;
                } else {
                    break;
                }
                newRow += direction[0];
                newCol += direction[1];
            }
        }
    }

    private void printResult() {
        int blackCount = 0;
        int whiteCount = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == BLACK) {
                    blackCount++;
                } else if (board[i][j] == WHITE) {
                    whiteCount++;
                }
            }
        }
        System.out.println("黒: " + blackCount);
        System.out.println("白: " + whiteCount);
        if (blackCount > whiteCount) {
            System.out.println("黒の勝ち");
        } else if (whiteCount > blackCount) {
            System.out.println("白の勝ち");
        } else {
            System.out.println("引き分け");
        }
    }

    private int getColumnIndex(String columnName) {
        for (int i = 0; i < COLUMN_NAMES.length; i++) {
            if (COLUMN_NAMES[i].equals(columnName)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Othello othello = new Othello();
        othello.play();
    }
}
