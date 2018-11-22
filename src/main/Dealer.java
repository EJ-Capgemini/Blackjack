package main;

public class Dealer extends User {
    public Dealer() {
        super("Dealer");
    }

    /*
        Volgens de regels mag een dealer niet drawen als hij al 17 of hoger heeft. Hierbij wordt de aas uiteraard als 1 gerekend.
     */
    public boolean allowedToDraw(){
        int sum = 0;

        for (Card card : cards) {
            sum += card.getRank().getValue();
        }

        return sum < 17;
    }
}