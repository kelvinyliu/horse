// File: model/Bet.java
package model;

public class Bet {
    public final String horseName;
    public final double amount;
    public final double odds;

    public Bet(String horseName, double amount, double odds) {
        this.horseName = horseName;
        this.amount = amount;
        this.odds = odds;
    }
}
