// cooperate with Jiazheng Xiong
import java.util.*;

public class Deck {
    private List<Card> CardDeck = new ArrayList<>();
    public static String[] Suit={"Spade","Heart","Club","Diamond"};

    public void initialDeck(){
        for(int i=0;i<=3;i++) {
            for (int j = 0; j <= 12; j++) {
                CardDeck.add(new Card(j+1,Suit[i]));
            }
        }
    }

    public void shuffleDeck(){
        Collections.shuffle(CardDeck);
    }

    public List<Card> getCardDeck() {
        return this.CardDeck;
    }

    // get first card in the deck
    public Card takeCard(boolean show){
        Card card = this.CardDeck.remove(0);
        if (show) System.out.println("The new card is " + card);
        else System.out.println("The new card is ?");
        return card;
    }

    public Card TEtakeCard(){
        if(this.CardDeck.isEmpty()){
            System.out.println("Deck is empty. Deck is reshuffled");
            this.initialDeck();
            this.shuffleDeck();
        }
        return this.CardDeck.remove(0);
    }
}


