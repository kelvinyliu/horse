package control;

import model.RaceHistoryModel;
import view.HorseStatsView;

public class HorseStatsController {
    private final RaceHistoryModel historyModel;
    private HorseStatsView statsView;

    public HorseStatsController(RaceHistoryModel historyModel) {
        this.historyModel = historyModel;
    }

    public void openStatsWindow() {
        if (statsView == null) {
            statsView = new HorseStatsView(historyModel);
        }
        statsView.setVisible(true);
    }
}
