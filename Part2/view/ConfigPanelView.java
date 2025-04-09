package view;

import model.WeatherCondition;

import javax.swing.*;
import java.awt.*;

public class ConfigPanelView extends JPanel {
    private final JSpinner laneCountSpinner;
    private final JSpinner trackLengthSpinner;
    private final JComboBox<String> trackShapeCombo;
    private final JComboBox<WeatherCondition> weatherCombo;

    private final JButton applyConfigButton;

    public ConfigPanelView() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        laneCountSpinner = new JSpinner(new SpinnerNumberModel(5, 2, 12, 1));
        trackLengthSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 100, 1));

        trackShapeCombo = new JComboBox<>(new String[] {
                "Line", "Oval", "Figure Eight", "Custom"
        });

        weatherCombo = new JComboBox<>(WeatherCondition.values());

        applyConfigButton = new JButton("Apply Config");

        add(new JLabel("Lanes:"));
        add(laneCountSpinner);
        add(new JLabel("Track Length:"));
        add(trackLengthSpinner);
        add(new JLabel("Track Shape:"));
        add(trackShapeCombo);
        add(new JLabel("Weather:"));
        add(weatherCombo);
        add(applyConfigButton);
    }

    public int getLaneCount() {
        return (Integer) laneCountSpinner.getValue();
    }

    public int getTrackLength() {
        return (Integer) trackLengthSpinner.getValue();
    }

    public String getTrackShape() {
        return (String) trackShapeCombo.getSelectedItem();
    }

    public WeatherCondition getWeatherCondition() {
        return (WeatherCondition) weatherCombo.getSelectedItem();
    }

    public JButton getApplyConfigButton() {
        return applyConfigButton;
    }
}
