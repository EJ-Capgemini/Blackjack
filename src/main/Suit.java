package main;

public enum Suit {
    CLUB ("klaveren"),
    HEART("harten"),
    DIAMOND("ruiten"),
    SPADE("schoppen");

    private String name;

    Suit(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
