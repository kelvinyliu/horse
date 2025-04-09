package control;

import model.*;
import view.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class BettingController {
    private final BettingModel model;
    private final BettingView view;

    public BettingController(BettingModel model, BettingView view, List<Horse> horses, WeatherCondition weather) {
        this.model = model;
        this.view = view;

        model.calculateOdds(horses, weather);
        updateHorseList(horses);

        setupListeners();
    }

    private void setupListeners() {
        view.getPlaceBetButton().addActionListener(e -> {
            String horse = (String) view.getHorseSelector().getSelectedItem();

            if (horse == null || horse.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please select a horse before placing a bet.");
                return;
            }

            try {
                double amount = Double.parseDouble(view.getBetAmountField().getText());
                model.placeBet(horse, amount);

                view.updateOddsDisplay(model.getOdds());
                view.updateHistoryDisplay(model.getBetHistory());
                view.clearForm();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Enter a valid number for the bet amount.");
            }
        });


        // Live potential winnings update
        view.getBetAmountField().getDocument().addDocumentListener(new SimpleDocumentListener(() -> view.updatePotentialWinnings(model.getOdds())));

        // reset all bets
        view.getResetBetsButton().addActionListener(e -> {
            model.resetBets();
            view.updateOddsDisplay(model.getOdds());
            view.updateHistoryDisplay(model.getBetHistory());
            view.clearForm();
        });

        view.getCloseButton().addActionListener(e -> view.setVisible(false));
    }

    public void show() {
        view.setVisible(true);
    }

    public void updateHorseList(List<Horse> horses) {
        view.getHorseSelector().removeAllItems();
        for (Horse h : horses) {
            view.getHorseSelector().addItem(h.getName());
        }

        view.updateOddsDisplay(model.getOdds());
        view.updateHistoryDisplay(model.getBetHistory());
    }


    private static class SimpleDocumentListener implements DocumentListener {
        private final Runnable callback;

        public SimpleDocumentListener(Runnable callback) {
            this.callback = callback;
        }

        public void insertUpdate(DocumentEvent e) {
            callback.run();
        }

        public void removeUpdate(DocumentEvent e) {
            callback.run();
        }

        public void changedUpdate(DocumentEvent e) {
            callback.run();
        }
    }
}
