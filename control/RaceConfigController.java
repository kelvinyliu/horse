package control;

import model.Horse;
import model.HorseSetupModel;
import model.Race;
import model.WeatherCondition;
import model.BettingModel;
import view.HorseSetupPanelView;
import view.RaceView;
import view.ConfigPanelView;

public class RaceConfigController {
    private final Race model;
    private final RaceView view;
    private final HorseSetupModel horseModel;
    private final BettingModel bettingModel;
    private final BettingController bettingController;

    public RaceConfigController(Race model, RaceView view, HorseSetupModel horseModel,
                                BettingModel bettingModel, BettingController bettingController) {
        this.model = model;
        this.view = view;
        this.horseModel = horseModel;
        this.bettingModel = bettingModel;
        this.bettingController = bettingController;

        ConfigPanelView config = view.getSetupWindow().getConfigPanel();
        config.getApplyConfigButton().addActionListener(e -> applyConfiguration());
    }

    private void applyConfiguration() {
        int lanes = view.getSetupWindow().getConfigPanel().getLaneCount();
        int trackLength = view.getSetupWindow().getConfigPanel().getTrackLength();
        String shape = view.getSetupWindow().getConfigPanel().getTrackShape();
        WeatherCondition weather = view.getSetupWindow().getConfigPanel().getWeatherCondition();

        model.configureRace(trackLength, lanes, shape, weather);
        view.getSetupWindow().rebuildHorseSetupPanel(lanes);

        horseModel.horses.clear();
        for (HorseSetupPanelView.HorseData data : view.getSetupWindow().getHorseSetupPanel().getAllHorseData()) {
            horseModel.addHorse(new Horse(
                    data.symbol,
                    data.name,
                    data.confidence,
                    data.breed,
                    data.coatColour,
                    data.saddle,
                    data.horseshoe
            ));
        }

        // refresh track view
        view.getRaceCanvas().setupTrack(trackLength, horseModel);

        // refresh betting
        bettingModel.resetBets();
        bettingModel.calculateOdds(horseModel.horses, weather);
        bettingController.updateHorseList(horseModel.horses);

        view.getLogPanel().log("Race configured: " + lanes + " lanes, " + trackLength + "m, " +
                shape + " track, " + weather + " weather.");
    }


}
