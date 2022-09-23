import java.util.Scanner;


public abstract class Game {
    Player player1;
    Player player2;
    int stalemate = 0;

    public void initPlayer(Player p, String name, int num) {
        p.setName(name);
        p.setNo(num);
    }

    public abstract void endGame();

    public abstract void startGame();
}


class TTTGame extends Game {
    TTTBoard tttBoard;

    public TTTGame() {
        super();
    }

    public void placePiece(String side) {
        int x, y;
        // record the coordinate
        if (side.equals("O")) {
            this.player1.enterMove();
            x = this.player1.curMove[0];
            y = this.player1.curMove[1];
        }
        else {
            this.player2.enterMove();
            x = this.player2.curMove[0];
            y = this.player2.curMove[1];
        }
        // check the availability
        if (x <= 0 || x > this.tttBoard.size || y <= 0 || y > this.tttBoard.size) {
            System.out.println("That's an invalid place! Please try again.");
            placePiece(side);
        }
        else if (!this.tttBoard.board[x-1][y-1].equals(" ")) {
            System.out.println("Sorry, there is already a piece on that place.");
            placePiece(side);
        }
        else {
            this.tttBoard.board[x-1][y-1] = side;
        }
    }

    @Override
    public void endGame() {
        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        switch (ans) {
            case "y", "yes", "Y", "Yes" -> startGame();
            case "n", "no", "N", "No" -> {
                System.out.println("Do you want to play the other kind of board game?");
                Scanner scType = new Scanner(System.in);
                String answer = scType.nextLine();
                switch (answer) {
                    case "y", "yes", "Y", "Yes" -> {
                        OACGame oacGame = new OACGame();
                        oacGame.oacBoard = new OACBoard();
                        oacGame.player1 = this.player1;
                        oacGame.player2 = this.player2;
                        oacGame.startGame();
                    }
                    case "n", "no", "N", "No" -> {
                        System.out.println("Thanks for playing! Your scores are listed below. See" +
                                " you next time!");
                        System.out.printf("""
                                        Player %s wins: %d times
                                        Player %s wins: %d times
                                        Stalemate: %d
                                        """,
                                this.player1.name, this.player1.winSum, this.player1.name,
                                this.player2.winSum, this.stalemate);
                    }
                    default -> {
                        System.out.println("Sorry, your answer can not be identified. Please " +
                                "enter a more common word again:");
                        endGame();
                    }
                }
            }
            default -> {
                System.out.println("Sorry, your answer can not be identified. Please enter a more" +
                        " common word again:");
                endGame();
            }
        }
    }

    @Override
    public void startGame() {
        this.tttBoard.initBoard();
        this.tttBoard.displayBoard();
        String side = "O";
        // store the number of moves
        int step = 0;
        while (true) {
            // check if someone wins after 5 moves, since no one can win before that
            if (step >= this.tttBoard.size - 1) {
                if (this.tttBoard.isWinner("O")) {
                    this.player1.winSum++;
                    System.out.printf("Player %s has won!\n", this.player1.name);
                    break;
                }
                if (this.tttBoard.isWinner("X")) {
                    this.player2.winSum++;
                    System.out.printf("Player %s has won!\n", this.player2.name);
                    break;
                }
            }
            // it comes to stalemate if the board filled
            if (step >= this.tttBoard.size * this.tttBoard.size && this.tttBoard.isFilled()) {
                this.stalemate++;
                System.out.println("!! Now the game ends up in stalemate. !!");
                break;
            }
            placePiece(side);
            this.tttBoard.displayBoard();
            step++;
            // it's another player's turn now
            side = side.equals("O") ? "X" : "O";
        }
        System.out.println("Game over. Would you like to play another round?");
        endGame();
    }
}


class OACGame extends Game {
    OACBoard oacBoard;

    public OACGame() {
        super();
    }

    public void placePiece(Player p) {
        System.out.println("Which piece do you want to place? Please Choose O or X. If your input" +
                " is not \"O\", then it will be automatically assigned \"X\".");
        Scanner scPiece = new Scanner(System.in);
        String piece = scPiece.nextLine();
        if (!piece.equals("O")) piece = "X";
        p.enterMove();
        int x = p.curMove[0];
        int y = p.curMove[1];
        if (x < 0 || x > this.oacBoard.size || y < 0 || y > this.oacBoard.size) {
            System.out.println("That's a invalid place! Please try again.");
            placePiece(p);
        }
        else if (!this.oacBoard.board[x-1][y-1].equals(" ")) {
            System.out.println("Sorry, there is already a piece on that place.");
            placePiece(p);
        }
        else {
            this.oacBoard.board[x-1][y-1] = piece;
        }
    }

    @Override
    public void endGame() {
        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        switch (ans) {
            case "y", "yes", "Y", "Yes" -> startGame();
            case "n", "no", "N", "No" -> {
                System.out.println("Do you want to play the other kind of board game?");
                Scanner scType = new Scanner(System.in);
                String answer = scType.nextLine();
                switch (answer) {
                    case "y", "yes", "Y", "Yes" -> {
                        System.out.println("What size of the board do you like? Please enter an " +
                                "integer and then a nxn board will be created.");
                        Scanner scSize = new Scanner(System.in);
                        int size = scSize.nextInt();
                        TTTGame tttGame = new TTTGame();
                        tttGame.player1 = this.player1;
                        tttGame.player2 = this.player2;
                        tttGame.tttBoard = new TTTBoard(size);
                        tttGame.startGame();
                    }
                    case "n", "no", "N", "No" -> {
                        System.out.println("Thanks for playing! Your scores are listed below. See" +
                                " you next time!");
                        System.out.printf("""
                                        Player %s wins: %d times
                                        Player %s wins: %d times
                                        """,
                                this.player1.name, this.player1.winSum, this.player1.name,
                                this.player2.winSum);
                    }
                    default -> {
                        System.out.println("Sorry, your answer can not be identified. Please " +
                                "enter a more common word again:");
                        endGame();
                    }
                }
            }
            default -> {
                System.out.println("Sorry, your answer can not be identified. Please enter a more" +
                        " common word again:");
                endGame();
            }
        }
    }

    @Override
    public void startGame() {
        this.oacBoard.initBoard();
        this.oacBoard.displayBoard();
        Player p = this.player1;
        int step = 0;
        while (true) {
            if (step >= 8 &&
                    (this.oacBoard.isWinner("O") || this.oacBoard.isWinner("X"))) {
                this.player1.winSum++;
                System.out.printf("Player %s has won!\n", this.player1.name);
                break;
            }
            if (step >= 35 && this.oacBoard.isFilled()) {
                this.player2.winSum++;
                System.out.printf("Player %s has won!\n", this.player2.name);
                break;
            }
            placePiece(p);
            this.oacBoard.displayBoard();
            step++;
            p = p.equals(this.player1) ? this.player2 : this.player1;
        }
        System.out.println("Game over. Would you like to play another round?");
        endGame();
    }
}
