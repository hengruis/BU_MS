// cooperate with Jiazheng Xiong
import java.util.*;

public abstract class CardGame extends Game {
    protected List<Player> Cardplayers = new ArrayList<>();
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        ChooseGame(start());
    }

    static int start() {
        System.out.println("Welcome to the Game of Blackjack and Trianta Ena!");
        System.out.println("Choose the game you want to play:");
        System.out.println("0:  Exit");
        System.out.println("1:  Blackjack");
        System.out.println("2:  Trianta Ena");
        return in.nextInt();
    }

    /**
     * choose game
     */
    static void ChooseGame(int n) {
        if (n == 0) {
            System.out.println("Game will exit.");
        } else if (n == 1) {
            new BlackjackGame().play(true);
        } else if (n == 2) {
            new TEGame().play();
        } else {
            System.out.println("Invalid Input. Game will exit.");
        }
    }
}
