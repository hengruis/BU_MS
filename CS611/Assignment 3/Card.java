// cooperate with Jiazheng Xiong
public class Card {
    private int rank; // Ace, 2-10, J, Q, K
    private String suit;

    public Card(int rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**
     *
     * @return string value of card's rank
     */
    public String toString() {
        String faceStr;
        String contents; // combination of suit and face of a card
        faceStr = switch(this.rank) {
            case 1 -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> "" + this.rank;
        };

        contents = suit + " - " + faceStr;

        return "|" + contents + "|";
    }
}
