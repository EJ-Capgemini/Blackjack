package main;

public enum Rank {
    ACE("aas", 1),
    KING("koning", 10),
    QUEEN("koningin", 10),
    JACK("boer", 10),
    TEN("tien", 10),
    NINE("negen", 9),
    EIGHT("acht", 8),
    SEVEN("zeven", 7),
    SIX("zes", 6),
    FIVE("vijf", 5),
    FOUR("vier", 4),
    THREE("drie", 3),
    TWO("twee", 2);

    private final String name;
    private final int value;

    Rank(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
