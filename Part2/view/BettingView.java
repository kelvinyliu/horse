package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

import model.Bet;

public class BettingView extends JFrame {
    private final JComboBox<String> horseSelector;
    private final JTextField betAmountField;
    private final JTextArea oddsDisplay;
    private final JTextArea historyDisplay;
    private final JLabel potentialWinningsLabel;
    private final JLabel totalBetLabel;
    private final JButton placeBetButton;
    private final JButton resetBetsButton;
    private final JButton closeButton;

    public BettingView() {
        setTitle("Betting Menu");
        setSize(450, 600);
        setLayout(new BorderLayout(10, 10));

        horseSelector = new JComboBox<>();
        betAmountField = new JTextField(10);
        oddsDisplay = new JTextArea(6, 30);
        historyDisplay = new JTextArea(12, 30);
        potentialWinningsLabel = new JLabel("Potential Winnings: $0.00");
        totalBetLabel = new JLabel("Total Bet Placed: $0.00");
        placeBetButton = new JButton("Place Bet");
        resetBetsButton = new JButton("Reset Bets");
        closeButton = new JButton("Close");

        oddsDisplay.setEditable(false);
        historyDisplay.setEditable(false);

        JPanel betPanel = new JPanel(new GridLayout(4, 2, 10, 5));
        betPanel.setBorder(BorderFactory.createTitledBorder("Place a Bet"));
        betPanel.add(new JLabel("Select Horse:"));
        betPanel.add(horseSelector);
        betPanel.add(new JLabel("Amount:"));
        betPanel.add(betAmountField);
        betPanel.add(new JLabel(" "));
        betPanel.add(potentialWinningsLabel);
        betPanel.add(placeBetButton);
        betPanel.add(resetBetsButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(oddsDisplay), BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(historyDisplay), BorderLayout.CENTER);
        centerPanel.add(totalBetLabel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);

        add(betPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JComboBox<String> getHorseSelector() {
        return horseSelector;
    }

    public JTextField getBetAmountField() {
        return betAmountField;
    }

    public JTextArea getOddsDisplay() {
        return oddsDisplay;
    }

    public JTextArea getHistoryDisplay() {
        return historyDisplay;
    }

    public JButton getPlaceBetButton() {
        return placeBetButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getResetBetsButton() {
        return resetBetsButton;
    }

    public void updateOddsDisplay(Map<String, Double> odds) {
        oddsDisplay.setText("Current Odds:\n");
        odds.forEach((name, odd) -> oddsDisplay.append(name + ": " + odd + "\n"));
    }

    public void updateHistoryDisplay(List<Bet> bets) {
        historyDisplay.setText("Bet History:\n");
        double total = 0.0;
        for (Bet b : bets) {
            historyDisplay.append(b.horseName + " - $" + b.amount + " @ " + b.odds + "\n");
            total += b.amount;
        }
        totalBetLabel.setText("Total Bet Placed: $" + String.format("%.2f", total));
    }

    public void updatePotentialWinnings(Map<String, Double> odds) {
        String horse = (String) horseSelector.getSelectedItem();
        try {
            double amount = Double.parseDouble(betAmountField.getText());
            double oddsForHorse = odds.getOrDefault(horse, 1.0);
            double winnings = amount * oddsForHorse;
            potentialWinningsLabel.setText("Potential Winnings: $" + String.format("%.2f", winnings));
        } catch (NumberFormatException ex) {
            potentialWinningsLabel.setText("Potential Winnings: $0.00");
        }
    }

    public void clearForm() {
        betAmountField.setText("");
        potentialWinningsLabel.setText("Potential Winnings: $0.00");
    }
}
