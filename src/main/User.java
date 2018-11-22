package main;

import java.util.ArrayList;
import java.util.List;

public class User {
    final List<Card> cards;
    private final String name;

    public User(String name) {
        this.cards = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCard(Card card) {
        cards.add(card);
        System.out.println(this.name + " drew a " + card.getSuit().getName() + " " + card.getRank().getName());
    }

    public int calculateScore(){
        int sum = 0;
        int aces = 0;

        //Eerst alle waardes optellen van alles behalve aas.
        for (Card card : cards) {
            if (!card.getRank().equals(Rank.ACE)) {
                sum += card.getRank().getValue();
            } else {
                aces++;
            }
        }

//        Toelichting berekening: Een aas is 1 of 11. Hoeveel je er ook hebt, je wilt er maar maximaal 1 als 11.
//        Oftewel.. Elke aas als 1 tellen. Kan er 10 bij opgeteld worden? Dan 1 aas als 11 door er 10 bij op te tellen.
        sum = ((sum + 10 + aces) > 21) ? ((sum += aces)) : ((sum += 10 + aces));

        return sum;
    }

    public void printCards(){
        String output = name + " is currenly holding the following cards: ";

        int i = 0; //bijhouden of het om ht laatste element gaat.

        for (Card card : cards) {
            output += card.getSuit().getName() + " " + card.getRank().getName();

            if(i++ == cards.size() - 1){
                output += ".";
            } else {
                output += ", ";
            }
        }

        System.out.println(output);
    }
}
