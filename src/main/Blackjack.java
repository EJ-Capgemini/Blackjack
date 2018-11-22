package main;

import java.util.*;

public class Blackjack {
    private final Deck deck;
    private final Dealer dealer;
    private final List<User> users;
    private Response requiredResponse = Response.FIRST_TRY;

    private int curTurn = 0;
    private int curGame = 1;

    public Blackjack(List<Player> players) {
        this.deck = new Deck();
        this.dealer = new Dealer();
        this.users = new ArrayList<>();
        for (Player player : players) {
            this.users.add(player);
        }
        this.users.add(dealer);
    }

    public void playGame(){
        if (users.size() >= 2) {
            System.out.println("### GAME(ID " + curGame + ") CREATED ###");
            deck.shuffleDeck();

            for (User user : users) {
                user.addCard(deck.drawCard());
                user.addCard(deck.drawCard());
            }
        } else {
            System.out.println("ERROR: INSUFFICIENT NUMBER OF PLAYERS TO START GAME! MINIMUM OF 1 REQUIRED. FOUND " + users.size());
            return;
        }
        System.out.println("### GAME STARTED ###");
        do {
            nextAction();
        }
        while (curTurn < users.size());

        finishGame();
    }

    public void reset(){
        deck.reset();
        curTurn = 0;
        for (User user : users) {
            user.cards.clear();
        }
    }

    public void nextAction() {
        User curUser = null;
        if (curTurn < users.size()){
            curUser = users.get(curTurn);

            //Een dealer mag niet doorgaan als hij al 17 of hoger heeft. Een player stop automatisch bij 21+
            if (curUser.getClass().toString().equals("class main.Dealer")){
                if (!((Dealer)curUser).allowedToDraw()){
                    curTurn++;
                    return;
                }
            } else if (curUser.getClass().toString().equals("class main.Player")){
                if (!((Player)curUser).allowedToDraw()){
                    curTurn++;
                    requiredResponse = Response.FIRST_TRY; //niet noodzakelijk omdat spel eindigt.
                    return;
                }
            }
        } else {
            System.out.println("Alle spelers zijn aan de beurt geweest, maar het spel is niet gestopt..");
            throw new IndexOutOfBoundsException();
        }

        switch(requiredResponse) {
            case FIRST_TRY:
                System.out.println("It's " + curUser.getName() + "'s turn.");
                curUser.printCards();
                System.out.println("Draw a card? yes/no");
                break;
            case DRAW_AGAIN:
                System.out.println("Draw another card? yes/no");
                break;
            case BAD_INPUT:
                System.out.println("Sorry, you can only answer with yes or no. Please try again.");
                break;
        }

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        //Voor de ervaring hoofdletters en spaties corrigeren.
        switch(input.toLowerCase().replaceAll("\\s","")){
            case "yes":
            case "y":
                curUser.addCard(deck.drawCard());
                requiredResponse = Response.DRAW_AGAIN;
                break;
            case "no":
            case "n":
                System.out.println(curUser.getName() + " finished his/her turn.");
                curTurn++;
                requiredResponse = Response.FIRST_TRY;
                break;
            default:
                requiredResponse = Response.BAD_INPUT;
                break;
        }
    }

    public void finishGame(){
        System.out.println("### GAME FINISHED ###");

        List<User> bustedUsers = new ArrayList<>(); //achteraf verwijderen om ConcurrentModificationException te vermijden!
        List<User> remainingUsers = new ArrayList<>(users);
        for (User user : users) {
            if (user.calculateScore() > 21){
                bustedUsers.add(user);
            }
        }
        remainingUsers.removeAll(bustedUsers);

        //Lijst sorten op hoogste score eerst.
        remainingUsers.sort(this::compare);


//        Omdat er potentieel meerdere winnaars kunnen zijn eerst checken op hoogste score.
//        En daarna kijken wie deze score heeft.
        int highestScore = remainingUsers.get(0).calculateScore();
        String winnerNames = "";
        int winnerCount = 0;

        for (User user : remainingUsers) {
            if (user.calculateScore() == highestScore){
                if (winnerCount > 0){
                    winnerNames += ", ";
                }
                winnerNames += user.getName();
                winnerCount++;
            }
        }

        String output = winnerCount > 1 ? "Winners" : "Winner";
        output += ": " + winnerNames + " with a score of " + highestScore;

        System.out.println(output);

        playAgain();
    }

    public void playAgain(){
        System.out.println("Do you want to play again? Type yes to continue. Anything else to exit.");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        //Voor de ervaring hoofdletters en spaties corrigeren.
        switch(input.toLowerCase().replaceAll("\\s","")){
                case "yes":
                case "y":
                    System.out.println("\n");
                    curGame++;
                    reset();
                    playGame();
                    break;
                default:
                    System.out.println("Thank you for playing. Have a nice day. Bye.");
                    break;
        }
    }

    /*
        Twee main.User objecten vergelijken op hoogste score.
     */
    public int compare(User u1, User u2) {
        return Integer.compare(u2.calculateScore(), u1.calculateScore());
    }
}
