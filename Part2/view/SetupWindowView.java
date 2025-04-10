
package view;

import javax.swing.*;
import java.awt.*;

public class SetupWindowView extends JFrame {
    private final ConfigPanelView configPanel;
    private HorseSetupPanelView horseSetupPanel;

    public SetupWindowView() {
        super("Race Setup");

        configPanel = new ConfigPanelView();
        horseSetupPanel = new HorseSetupPanelView(0);

        setLayout(new BorderLayout());
        add(configPanel, BorderLayout.NORTH);
        add(horseSetupPanel, BorderLayout.CENTER);

        setSize(1300, 800);
        setLocationRelativeTo(null);
    }

    public void rebuildHorseSetupPanel(int laneCount) {
        var oldData = horseSetupPanel.getAllHorseData();
        remove(horseSetupPanel);

        // check if lanes changed
        if (oldData.size() != laneCount) {
            horseSetupPanel = new HorseSetupPanelView(laneCount);
        } else {
            horseSetupPanel = new HorseSetupPanelView(laneCount);
            for (int i = 0; i < laneCount; i++) {
                horseSetupPanel.setHorseDataAt(i, oldData.get(i));
            }
        }

        add(horseSetupPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    public ConfigPanelView getConfigPanel() {
        return configPanel;
    }

    public HorseSetupPanelView getHorseSetupPanel() {
        return horseSetupPanel;
    }

}