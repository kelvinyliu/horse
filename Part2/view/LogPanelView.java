package view;

import javax.swing.*;
import java.awt.*;

public class LogPanelView extends JPanel {
    private final JTextArea logArea;

    public LogPanelView() {

        logArea = new JTextArea(10, 80);
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);
    }

    public void log(String msg) {
        logArea.append(msg + "\n");
    }

    public void clear() {
        logArea.setText("");
    }
}
