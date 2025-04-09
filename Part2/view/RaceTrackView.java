package view;

import model.Horse;
import model.HorseSetupModel;
import model.Horseshoe;
import model.Saddle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RaceTrackView extends JPanel {
    private final ArrayList<JLabel> horses = new ArrayList<>();
    private int trackLength = 20;
    private final int laneHeight = 40;

    public RaceTrackView() {
        setLayout(null); // Manual layout
        setPreferredSize(new Dimension(900, 400));
        setBorder(BorderFactory.createTitledBorder("Race Track"));
        setBackground(Color.WHITE);
    }

    public void setupTrack(int trackLength, HorseSetupModel horseModel) {
        this.trackLength = trackLength;
        horses.clear();
        removeAll();

        int laneIndex = 0;
        for (Horse h : horseModel.horses) {

            JLabel horseLabel = new JLabel(horseDisplay(h));
            horseLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
            horseLabel.setSize(100, 50);
            horseLabel.setLocation(10, laneIndex * laneHeight + 20);
            add(horseLabel);
            horses.add(horseLabel);
            laneIndex++;
        }

        revalidate();
        repaint();
    }

    public void updateLane(int index, int progress, String label) {
        if (index >= horses.size()) return;

        JLabel horse = horses.get(index);

        int trackPixelWidth = getWidth() - 60; // Leave some margin
        int x = (int) ((double) progress / trackLength * trackPixelWidth);
        int y = index * laneHeight + 20;

        horse.setLocation(x, y);
        horse.setText(label);
        horse.repaint();
    }

    public void resetLanes(HorseSetupModel horseModel) {
        for (int i = 0; i < horses.size(); i++) {
            JLabel horse = horses.get(i);
            horse.setLocation(10, i * laneHeight + 20);

            // Update label in case symbol changed
            if (i < horseModel.horses.size()) {
                Horse h = horseModel.horses.get(i);

                String visual = horseDisplay(h);
                horse.setText(visual);
            } else {
                horse.setText("-");
            }
        }
    }

    private String horseDisplay(Horse h) {
        String visual = String.valueOf(h.getSymbol());
        if (h.getSaddle() != null && h.getSaddle() != Saddle.NONE) {
            visual += "\uD83D\uDCBA";
        }
        if (h.getHorseshoe() != null && h.getHorseshoe() != Horseshoe.NONE) {
            visual += "Ʊ";
        }
        return visual;
    }
}
