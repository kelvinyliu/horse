// File: model/BettingModel.java
package model;

import java.util.*;

public class BettingModel {
    private final Map<String, Double> odds = new HashMap<>();
    private final List<Bet> betHistory = new ArrayList<>();
    private final Map<String, Double> betAmounts = new HashMap<>();

    public void calculateOdds(List<Horse> horses, WeatherCondition weather) {
        odds.clear();

        double weatherModifier = weather == WeatherCondition.CLEAR ? 0.0 : Math.random();

        for (Horse h : horses) {
            double modifier = 1.0 + Math.random() + weatherModifier; // randomness
            odds.put(h.getName(), Math.round(modifier * 100.0) / 100.0);
        }
    }


    public void placeBet(String horseName, double amount) {
        double currentOdds = odds.getOrDefault(horseName, 1.0);
        betHistory.add(new Bet(horseName, amount, currentOdds));
        betAmounts.put(horseName, betAmounts.getOrDefault(horseName, 0.0) + amount);
    }

    public Map<String, Double> getOdds() {
        return odds;
    }

    public List<Bet> getBetHistory() {
        return betHistory;
    }

    public double calculateWinnings(String winningHorse) {
        return betHistory.stream()
                .filter(b -> b.horseName.equals(winningHorse))
                .mapToDouble(b -> b.amount * b.odds)
                .sum();
    }

    public void resetBets() {
        betHistory.clear();
        betAmounts.clear();
    }
}
