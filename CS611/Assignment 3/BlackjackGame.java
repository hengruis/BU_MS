// cooperate with Jiazheng Xiong
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame extends CardGame{
    private BlackjackPlayer player1 = new BlackjackPlayer();
    private Dealer computer = new Dealer();
    private Deck BJDeck = new Deck();
    private List<Hand> splitHands = new ArrayList<>();
    private Hand curHand;
    private boolean firstPlay = true;
    private double balance = 0.0;

    public void initial(){
        Scanner sc = new Scanner(System.in);
        if (firstPlay) {
            System.out.println("Welcome to Blackjack. You will play against a computer dealer.");
            //init player
            System.out.println("Player please enter your name: ");
            String name = sc.nextLine();
            player1.setPlayerName(name);
            System.out.println("How much money do you have: ");
            double money = sc.nextDouble();
            player1.setMoney(money);
            firstPlay = false;
        }
        balance = player1.money;
        System.out.println("Enter your bet: ");
        double bet = sc.nextDouble();
        while (bet > player1.money) {
            System.out.println("You do not have enough money! Re-enter your bet: ");
            bet = sc.nextDouble();
        }
        curHand = new Hand();
        curHand.setBet(bet);
        balance = player1.money - curHand.bet;
        computer.dealerHand.handCards = new ArrayList<>();

        BJDeck.initialDeck();
        BJDeck.shuffleDeck();
    }

    // main logics
    public void play(boolean scratch){
        initial();
        if (scratch) {
            System.out.println("Dealer distributes cards.");
            dealFirstCards();
        }
        Scanner sc = new Scanner(System.in);
        while (true) {
            // natural blackjack
            if (curHand.isWin(21)) gameOver("win");
            System.out.println("What's your next action?");
            System.out.println("1: hit");
            System.out.println("2: stand");
            System.out.println("3: double up");
            if (curHand.handCards.size() == 2 &&
                    curHand.handCards.get(0).getRank() == curHand.handCards.get(1).getRank() &&
                    balance >= curHand.bet) {
                System.out.println("4: split");
            }
            int act = sc.nextInt();
            switch (act) {
                // hit
                case 1 -> playerHit();
                // stand
                case 2 -> playerStand();
                // double up
                case 3 -> {
                    curHand.bet *= 2;
                    balance = player1.money - curHand.bet;
                    playerHit();
                    playerStand();
                }
                // split
                case 4 -> playerSplit();
            }
        }
    }

    public void dealFirstCards() {
        curHand.handCards.add(BJDeck.takeCard(true));
        computer.dealerHand.handCards.add(BJDeck.takeCard(true));
        curHand.handCards.add(BJDeck.takeCard(true));
        computer.dealerHand.handCards.add(BJDeck.takeCard(false));
        curHand.viewHandCards("player");
        computer.dealerHand.viewHandCards("dealer");
    }

    public void playerHit() {
        curHand.hit(BJDeck.takeCard(true));
        curHand.calculateValue(21);
        curHand.viewHandCards("player");
        if (curHand.isBust(21)) gameOver("lose");
        if (curHand.isWin(21)) {
            if (computer.dealerHand.isWin(21)) gameOver("tie");
            else gameOver("win");
        }
    }

    public void playerStand() {
        curHand.calculateValue(21);
        System.out.println("The dealer's hidden card is " +
                computer.dealerHand.handCards.get(1));
        computer.dealerHand.calculateValue(21);
        while (computer.dealerHand.values < 17) {
            computer.dealerHand.handCards.add(BJDeck.takeCard(true));
            computer.dealerHand.calculateValue(21);
        }
        computer.dealerHand.viewHandCards("all");
        if (computer.dealerHand.isBust(21) ||
                curHand.values > computer.dealerHand.values) gameOver(
                "win");
        else if (curHand.values < computer.dealerHand.values) gameOver("lose");
        else gameOver("tie");
    }

    public void playerSplit() {
        Hand splitHand = new Hand();
        splitHand.handCards.add(curHand.handCards.remove(1));
        splitHand.viewHandCards("player");
        curHand.handCards.add(BJDeck.takeCard(true));
        curHand.viewHandCards("player");
        splitHand.handCards.add(BJDeck.takeCard(true));
        splitHand.setBet(curHand.bet);
        splitHands.add(splitHand);
    }

    public void gameOver(String situation) {
        switch (situation) {
            case "win" -> player1.money += curHand.bet;
            case "lose" -> {
                player1.money -= curHand.bet;
                if (player1.money <= 0.0) outOfMoney();
            }
            case "tie" -> {}
        }
        prompt(situation);
        decision();
    }

    // print info about result
    public void prompt(String situation) {
        switch (situation) {
            case "win" -> System.out.println("You win! What to do next?");
            case "lose" -> System.out.println("You lose! What to do next?");
            case "tie" -> System.out.println("Tie! What to do next?");
        }
    }

    // if player wants to continue or quit
    public void decision() {
        System.out.println("Your money left: " + player1.money);
        Scanner sc = new Scanner(System.in);
        System.out.println("1: continue");
        System.out.println("2: cash out");
        int ans = sc.nextInt();
        if (ans == 1) {
            if (splitHands.isEmpty()) play(true);
            else {
                curHand = splitHands.remove(0);
                play(false);
            }
        } else {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
    }

    public void outOfMoney() {
        System.out.println("Sorry, you can not play anymore because you have no money now.");
        System.exit(0);
    }
}
