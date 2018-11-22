package main;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //testusers
        Player player1 = new Player("Erwin");
        List<Player> players = new ArrayList<>();
        players.add(player1);

        //create and start the game
        Blackjack blackjack = new Blackjack(players);
        blackjack.playGame();
    }
}
