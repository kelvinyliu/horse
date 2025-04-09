package view;

import model.RaceHistoryModel;
import model.RaceResult;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorseStatsView extends JFrame {
    private final JComboBox<String> horseSelector;
    private final JTextArea statsArea;

    public HorseStatsView(RaceHistoryModel historyModel) {
        setTitle("Horse Stats Viewer");
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        horseSelector = new JComboBox<>();
        statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Monospaced", Font.PLAIN, 24));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select Horse (by hash):"));
        topPanel.add(horseSelector);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(statsArea), BorderLayout.CENTER);

        horseSelector.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                // Load horse hashes into dropdown
                horseSelector.removeAllItems();
                for (String hash : historyModel.getAllHistory().keySet()) {
                    horseSelector.addItem(hash);
                }
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        // listens for horse selection change
        horseSelector.addActionListener(e -> {
            String selectedHash = (String) horseSelector.getSelectedItem();
            if (selectedHash != null) {
                List<RaceResult> results = historyModel.getHistoryForHorse(selectedHash);
                statsArea.setText(formatStats(results));
            }
        });

        // get first by default if available
        if (horseSelector.getItemCount() > 0) {
            horseSelector.setSelectedIndex(0);
        }
    }

    private String formatStats(List<RaceResult> results) {
        if (results == null || results.isEmpty()) {
            return "No data for this horse.";
        }

        int races = results.size();
        int wins = 0;
        double totalTime = 0;
        int totalDistance = 0;

        for (RaceResult r : results) {
            if (r.won) wins++;
            totalTime += r.timeSeconds;
            totalDistance += r.finalDistance;
        }

        double avgTime = totalTime / races;
        double avgDistance = (double) totalDistance / races;
        double winRate = (100.0 * wins) / races;

        return String.format("""
                Races Run:       %d
                Wins:            %d
                Win Rate:        %.1f %%
                Average Time:    %.2f s
                Avg Distance:    %.1f m
                
                Track Conditions:
                """,
                races, wins, winRate, avgTime, avgDistance
        ) + formatTrackBreakdown(results);
    }

    private String formatTrackBreakdown(List<RaceResult> results) {
        Map<String, Integer> raceCounts = new HashMap<>();
        Map<String, Integer> winCounts = new HashMap<>();
        Map<String, Double> bestTimes = new HashMap<>();

        for (RaceResult r : results) {
            String key = r.trackShape + " - " + r.weather + " (" + r.laneDistance + "m)";

            // Count races
            raceCounts.put(key, raceCounts.getOrDefault(key, 0) + 1);

            // Count wins
            if (r.won) {
                winCounts.put(key, winCounts.getOrDefault(key, 0) + 1);
            }

            // Track best time
            if (!bestTimes.containsKey(key) || r.timeSeconds < bestTimes.get(key)) {
                bestTimes.put(key, r.timeSeconds);
            }
        }

        StringBuilder sb = new StringBuilder();

        for (String key : raceCounts.keySet()) {
            int total = raceCounts.get(key);
            int wins = winCounts.getOrDefault(key, 0);
            double winRate = total > 0 ? (100.0 * wins / total) : 0;
            double best = bestTimes.get(key);

            sb.append("  ")
                    .append(key)
                    .append(" : ")
                    .append(total).append(" runs, ")
                    .append(wins).append(" wins, Win%: ")
                    .append((Math.round(winRate * 10.0) / 10.0))
                    .append(", Best: ")
                    .append((Math.round(best * 100.0) / 100.0))
                    .append("s\n");
        }

        return sb.toString();
    }
}
