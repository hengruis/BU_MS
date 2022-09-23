import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        System.out.println("Welcome to the game TicTacToe!");
        System.out.println("In this game, 2 players will take turns to place their pieces in the " +
                "3x3 board.The symbol \"O\" represents one player, while the symbol \"X\" " +
                "represents the other one. If \"O\"s or \"X\"s are in one line horizontally, " +
                "vertically or diagonally, the corresponding player will win the game. Try to " +
                "connect your pieces and prevent your opponent at the same time!");
        System.out.println("Player \"O\" will start first by default. A board looks like below. " +
                "You may use the numbers on the board to demonstrate where you'd like to place " +
                "your \"O\" or \"X\". For example, if you enter 5, your piece will be placed at " +
                "the center of the board. You can also enter 0 for your next move if you want to " +
                "exit the game. Now pick your side and enjoy it!");
        System.out.println("""
                Sample:
                +---+---+---+
                | 1 | 2 | 3 |
                +---+---+---+
                | 4 | 5 | 6 |
                +---+---+---+
                | 7 | 8 | 9 |
                +---+---+---+
                """);
        game.startGame();
    }
}

class TicTacToe {
    private final String[] board;
    private final int[] winsSum;

    public TicTacToe() {
        /*
         constructor
         */
        this.board = new String[]{
                " ", " ", " ",
                " ", " ", " ",
                " ", " ", " "
        };
        // winsSum[0] player O, winsSum[1] player X, winsSum[2] stalemate
        this.winsSum = new int[]{0, 0, 0};
    }

    public void initBoard() {
        /*
         initiate the board
         */
        Arrays.fill(this.board, " ");
    }

    public int setBoard(String side, int pos) {
        /*
         side: "O" or "X"
         pos: the corresponding place of the board
         if piece can be placed, then do so, otherwise return error status
         */
        // code 0 stands for inappropriate place
        if (!this.board[pos-1].equals(" ")) return 0;
        else {
            this.board[pos-1] = side;
            // code 1 stands for a successful insertion
            return 1;
        }
    }

    public void displayBoard() {
        /*
         display the board
         */
        System.out.printf("""
                        +---+---+---+
                        | %s | %s | %s |
                        +---+---+---+
                        | %s | %s | %s |
                        +---+---+---+
                        | %s | %s | %s |
                        +---+---+---+
                        """,
                this.board[0], this.board[1], this.board[2],
                this.board[3], this.board[4], this.board[5],
                this.board[6], this.board[7], this.board[8]);
    }

    public void enterMove(String side) {
        /*
         side: "O" or "X"
         implement a player's move
         */
        System.out.printf("Player %s enter your move: ", side);
        Scanner scannerO = new Scanner(System.in);
        try {
            int pos = scannerO.nextInt();
            // exit if enter 0
            if (pos == 0) {
                System.out.println("What a pity! See you next time!");
                System.exit(0);
            }
            // invalid input
            if (pos < 0 || pos > 9) {
                System.out.println("That's a invalid place! Please try again.");
                enterMove(side);
            }
            else if (setBoard(side, pos) == 0) {
                System.out.println("Oops! Seems that place is no longer available now. Please " +
                        "enter another place:");
                enterMove(side);
            }
        } catch (Exception e) {
            // if input is not an integer
            System.out.println("An integer is expected! Please try again:");
            enterMove(side);
        }
    }

    public boolean isWinner(String side) {
        /*
         side: "O" or "X"
         check the board and decide if a player wins
         */
        if (
                // if in a vertical line
                (this.board[0].equals(side) && this.board[3].equals(side) &&
                 this.board[6].equals(side) ||
                 this.board[1].equals(side) && this.board[4].equals(side) &&
                 this.board[7].equals(side) ||
                 this.board[2].equals(side) && this.board[5].equals(side) &&
                 this.board[8].equals(side)) ||
                // if in a horizontal line
                (this.board[0].equals(side) && this.board[1].equals(side) &&
                 this.board[2].equals(side) ||
                 this.board[3].equals(side) && this.board[4].equals(side) &&
                 this.board[5].equals(side) ||
                 this.board[6].equals(side) && this.board[7].equals(side) &&
                 this.board[8].equals(side)) ||
                 // if in a diagonal line
                (this.board[0].equals(side) && this.board[4].equals(side) &&
                 this.board[8].equals(side) ||
                 this.board[2].equals(side) && this.board[4].equals(side) &&
                 this.board[6].equals(side))) {
            switch (side) {
                case "O" -> this.winsSum[0]++;
                case "X" -> this.winsSum[1]++;
            }
            System.out.printf("Congrats! Player %s has won the game!\n", side);
            return true;
        }
        return false;
    }

    public boolean isBoardFilled() {
        /*
         check if the board is completely filled up
         */
        for (String side:
             this.board) {
            if (side.equals(" ")) return false;
        }
        return true;
    }

    public void endGame() {
        /*
         end the game
         */
        // collect players' answers
        Scanner scanner = new Scanner(System.in);
        String ans = scanner.nextLine();
        switch (ans) {
            case "y", "yes", "Y", "Yes" -> startGame();
            case "n", "no", "N", "No" -> {
                System.out.println("Thanks for playing TicTacToe! Your scores are listed below. " +
                        "See you next time!");
                System.out.printf("""
                                Player O wins: %d times
                                Player X wins: %d times
                                Stalemate: %d
                                """,
                        this.winsSum[0], this.winsSum[1], this.winsSum[2]);
            }
            default -> {
                System.out.println("Sorry, your answer can not be identified. Please enter a more" +
                        " common word again:");
                endGame();
            }
        }
    }

    public void startGame() {
        /*
         start the game
         */
        System.out.println("Now new board creating...");
        initBoard();
        displayBoard();
        // local variable "side" controls the move each turn, begins with "O"
        String side = "O";
        // store the number of moves
        int step = 0;
        while (true) {
            // check if someone wins after 5 moves, since no one can win before that
            if (step >= 5 && (isWinner("O") || isWinner("X"))) break;
            // it comes to stalemate if the board filled
            if (step >= 9 && isBoardFilled()) {
                this.winsSum[2]++;
                System.out.println("!! Now the game ends up in stalemate. !!");
                break;
            }
            enterMove(side);
            displayBoard();
            step++;
            // it's another player's turn now
            side = side.equals("O") ? "X" : "O";
        }
        System.out.println("Game over. Would you like to play for another round?");
        endGame();
    }
}
