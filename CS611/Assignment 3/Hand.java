// cooperate with Jiazheng Xiong
import java.util.*;

public class Hand {
    protected List<Card> handCards = new ArrayList<>();
    protected int values = 0;
    protected double bet = 0.0;
    protected boolean isStand = false;

    public int getHandValues(){
        return this.values;
    }

    public double getBet(){
        return this.bet;
    }

    public void setBet(double n){
        this.bet = n;
    }

    public void addBet(double n){
        this.bet+=n;
    }

    public void stand(){
        this.isStand = true;
    }

    public void hit(Card card) {
        this.handCards.add(card);
    }

    /**
     *
     * @param n  for BJ n=21; TE n=31
     * return value of hand
     */
    public void calculateValue(int n){
        boolean Ace = false;
        int cvalue = 0;
        int crank;
        Iterator<Card> iter = handCards.iterator();
        Card temCard;
        while (iter.hasNext()) {
            temCard = iter.next();
            crank = temCard.getRank();
            if (crank == 1) {
                Ace = true;
                cvalue += 11;
            } else if (crank > 10) {
                cvalue += 10;
            } else {
                cvalue += crank;
            }
        }
        if (cvalue > n && Ace) {
            cvalue = cvalue - 10;
        }
        Ace = false;
        this.values = cvalue;
    }

    public boolean isBust(int n){
        return values>n;
    };

    public boolean isWin(int n) {
        return values == n;
    }

    // show cards in the hand
    public void viewHandCards(String who) {
        System.out.printf("%s' cards are: ", who);
        if (who.equals("player") || who.equals("all")) {
            for (Card c :
                    handCards) {
                System.out.print(c);
            }
            System.out.print("\n");
        }
        if (who.equals("dealer")) {
            System.out.print(handCards.get(0));
            System.out.print("\n");
        }
    }
}
