import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Board!");
        System.out.println("Later you could choose game Tic-Tac-Toe or Order-and-Chaos to play.");
        System.out.println("Player \"O\" will start first by default. A board looks like below. " +
                "You may use the coordinate of the board to demonstrate where you'd like to place" +
                " your \"O\" or \"X\". For example, if you enter 2,3 for \"O\", then the " +
                "board should be like below. You can also enter \"end\" for your next move if you" +
                " want to exit the game. Now pick your side and enjoy it!");
        System.out.println("""
                input: 3,2 (format: {int,int}, no space)
                Sample:
                ------> y
                |   +---+---+---+
                v   |   |   |   |  1
                x   +---+---+---+
                    |   |   |   |  2
                    +---+---+---+
                    |   | O |   |  3
                    +---+---+---+
                      1   2   3
                """);
        System.out.println("Which game would you like to play? Enter \"T\" for Tic-Tac-Toe or " +
                "\"O\" for Order-and-Chaos.");
        Scanner scGame = new Scanner(System.in);
        String type = scGame.nextLine();
        switch (type) {
            case "T" -> {
                System.out.println("What size of the board do you like? Please enter an integer " +
                        "and then a nxn board will be created.");
                Scanner scSize = new Scanner(System.in);
                int size = scSize.nextInt();
                TTTGame tttGame = new TTTGame();
                tttGame.player1 = new Player();
                tttGame.player2 = new Player();
                tttGame.initPlayer(tttGame.player1, "O", 1);
                tttGame.initPlayer(tttGame.player2, "X", 2);
                tttGame.tttBoard = new TTTBoard(size);
                System.out.println("Note: In Tic-Tac-Toe, one should make n pieces in a line to " +
                        "win, where n is the size of the board.");
                tttGame.startGame();
            }
            case "O" -> {
                System.out.println("A new board creating...");
                OACGame oacGame = new OACGame();
                oacGame.oacBoard = new OACBoard();
                oacGame.player1 = new Player();
                oacGame.player2 = new Player();
                oacGame.initPlayer(oacGame.player1, "O", 1);
                oacGame.initPlayer(oacGame.player2, "X", 2);
                System.out.println("Note: In Order-and-Chaos, player Order, aka player one/\"O\"," +
                        " goes first.");
                oacGame.startGame();
            }
        }
    }
}
