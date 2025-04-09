package view;

import model.Breed;
import model.CoatColour;
import model.Horseshoe;
import model.Saddle;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class HorseSetupPanelView extends JPanel {
    private final ArrayList<HorseFormRow> horseRows = new ArrayList<>();

    public HorseSetupPanelView(int laneCount) {
        setLayout(new GridLayout(0, 1, 10, 5));
        setBorder(BorderFactory.createTitledBorder("Configure Horses"));

        for (int i = 0; i < laneCount; i++) {
            HorseFormRow row = new HorseFormRow(i + 1);
            horseRows.add(row);
            add(row);
        }

    }

    public ArrayList<HorseData> getAllHorseData() {
        ArrayList<HorseData> horses = new ArrayList<>();
        for (HorseFormRow row : horseRows) {
            horses.add(row.getHorseData());
        }
        return horses;
    }

    // bundle horse data into object for easier storage until usage.
    public static class HorseData {
        public String name;
        public char symbol;
        public double confidence;
        public Breed breed;
        public CoatColour coatColour;
        public Saddle saddle;
        public Horseshoe horseshoe;

        public String getHash() {
            String horseString = name + symbol + String.format("%.2f", confidence)
                    + breed + coatColour + saddle + horseshoe;
            return String.valueOf(horseString.hashCode());
        }

    }

    // row of configuration for each horse.
    private static class HorseFormRow extends JPanel {
        private final JTextField nameField;
        private final JTextField symbolField;
        private final JSpinner confidenceSpinner;
        private final JLabel horseHash;

        private final JComboBox<Breed> breedDropdown = new JComboBox<>(Breed.values());
        private final JComboBox<CoatColour> coatDropdown = new JComboBox<>(CoatColour.values());
        private final JComboBox<Saddle> saddleDropdown = new JComboBox<>(Saddle.values());
        private final JComboBox<Horseshoe> horseshoeDropdown = new JComboBox<>(Horseshoe.values());



        public HorseFormRow(int index) {
            setLayout(new FlowLayout(FlowLayout.LEFT));

            nameField = new JTextField("Horse" + index, 10);
            symbolField = new JTextField(String.valueOf((char) ('A' + index - 1)), 2);
            confidenceSpinner = new JSpinner(new SpinnerNumberModel(0.75, 0.1, 1.0, 0.05));
            confidenceSpinner.setPreferredSize(new Dimension(100, 20));
            horseHash = new JLabel();

            nameField.getDocument().addDocumentListener(new TextChangeListener(this::updateHash));
            symbolField.getDocument().addDocumentListener(new TextChangeListener(this::updateHash));
            confidenceSpinner.addChangeListener(e -> updateHash());

            breedDropdown.addActionListener(e -> updateHash());
            coatDropdown.addActionListener(e -> updateHash());
            saddleDropdown.addActionListener(e -> updateHash());
            horseshoeDropdown.addActionListener(e -> updateHash());

            add(new JLabel("Lane " + index + ":"));
            add(new JLabel("Name:"));
            add(nameField);
            add(new JLabel("Symbol:"));
            add(symbolField);
            add(new JLabel("Confidence:"));
            add(confidenceSpinner);

            add(new JLabel("Breed:"));
            add(breedDropdown);
            add(new JLabel("Coat colour:"));
            add(coatDropdown);
            add(new JLabel("Saddle:"));
            add(saddleDropdown);
            add(new JLabel("Horseshoe:"));
            add(horseshoeDropdown);

            add(horseHash);
            updateHash();
        }

        // create hash for a specific horse configuration, this is used in horse statistics.
        private void updateHash() {
            String name = nameField.getText().trim();
            String symbol = symbolField.getText().trim();
            double confidence = (Double) confidenceSpinner.getValue();
            String formattedConfidence = String.format("%.2f", confidence);

            String breed = breedDropdown.getSelectedItem().toString();
            String coat = coatDropdown.getSelectedItem().toString();
            String saddle = saddleDropdown.getSelectedItem().toString();
            String horseshoe = horseshoeDropdown.getSelectedItem().toString();

            String hashSource = name + symbol + formattedConfidence + breed + coat + saddle + horseshoe;
            int hash = hashSource.hashCode();

            horseHash.setText(String.valueOf(hash));
        }

        public HorseData getHorseData() {
            HorseData data = new HorseData();
            data.name = nameField.getText().trim();
            String symbolText = symbolField.getText().trim();
            data.symbol = symbolText.isEmpty() ? '?' : symbolText.charAt(0);
            data.confidence = (Double) confidenceSpinner.getValue();
            data.breed = (Breed) breedDropdown.getSelectedItem();
            data.coatColour = (CoatColour) coatDropdown.getSelectedItem();
            data.saddle = (Saddle) saddleDropdown.getSelectedItem();
            data.horseshoe = (Horseshoe) horseshoeDropdown.getSelectedItem();

            return data;
        }

        public void setHorseData(HorseData data) {
            nameField.setText(data.name);
            symbolField.setText(String.valueOf(data.symbol));
            confidenceSpinner.setValue(data.confidence);
            breedDropdown.setSelectedItem(data.breed);
            coatDropdown.setSelectedItem(data.coatColour);
            saddleDropdown.setSelectedItem(data.saddle);
            horseshoeDropdown.setSelectedItem(data.horseshoe);

            updateHash();
        }
    }
    public void setHorseDataAt(int index, HorseData data) {
        if (index < horseRows.size()) {
            horseRows.get(index).setHorseData(data);
        }
    }

}

class TextChangeListener implements DocumentListener {

    private final Runnable textChangeMethod;
    public TextChangeListener(Runnable runnable) {
        this.textChangeMethod = runnable;

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        textChangeMethod.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        textChangeMethod.run();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        textChangeMethod.run();
    }
}