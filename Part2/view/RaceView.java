package view;

import javax.swing.*;
import java.awt.*;

public class RaceView extends JFrame {
    private final RaceTrackView raceCanvas;
    private final LogPanelView logPanel;
    private final SetupWindowView setupWindow;
    private final JButton viewStatsButton;
    private final JButton openBettingButton;

    // 🆕 Race control panel
    private JButton startRaceButton;
    private JButton resetHorsesButton;

    public RaceView() {
        setTitle("Horse Race Simulator");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton openSetupButton = new JButton("Open Setup");
        viewStatsButton = new JButton("View Stats");
        openBettingButton = new JButton("Open Betting");
        logPanel = new LogPanelView();
        raceCanvas = new RaceTrackView();


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(viewStatsButton, BorderLayout.EAST);
        centerPanel.add(openSetupButton, BorderLayout.NORTH);
        centerPanel.add(openBettingButton, BorderLayout.WEST);
        centerPanel.add(raceCanvas, BorderLayout.CENTER);
        centerPanel.add(createRaceControlPanel(), BorderLayout.SOUTH);

        this.setupWindow = new SetupWindowView();

        openSetupButton.addActionListener(e -> setupWindow.setVisible(true));

        add(centerPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);
    }

    private JPanel createRaceControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        startRaceButton = new JButton("Start Race");
        resetHorsesButton = new JButton("Reset Horses");

        panel.setBorder(BorderFactory.createTitledBorder("Race Controls"));
        panel.add(startRaceButton);
        panel.add(resetHorsesButton);

        return panel;
    }

    public RaceTrackView getRaceCanvas() {
        return raceCanvas;
    }

    public LogPanelView getLogPanel() {
        return logPanel;
    }

    public JButton getStartRaceButton() {
        return startRaceButton;
    }

    public JButton getResetHorsesButton() {
        return resetHorsesButton;
    }

    public SetupWindowView getSetupWindow() {
        return setupWindow;
    }

    public JButton getViewStatsButton() {
        return viewStatsButton;
    }

    public JButton getOpenBettingButton() {
        return openBettingButton;
    }

}
