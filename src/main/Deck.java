package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        createDeck();
    }

    private void createDeck(){
        for (Suit suits : Suit.values()) {
            for (Rank rank : Rank.values()){
                cards.add(new Card(suits, rank));
            }
        }
    }

    public void reset(){
        cards.clear();
        createDeck();
    }

    public void shuffleDeck(){
        Collections.shuffle(cards);
    }

    /*
        DEBUG
     */
    public void printDeck(){
        for (Card card : cards) {
            System.out.println(card.getSuit() + " " + card.getRank());
        }
    }

    public Card drawCard(){
        Card card = cards.get(0);
        cards.remove(0);

        return card;
    }
}
