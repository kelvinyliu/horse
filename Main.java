import control.BettingController;
import control.RaceController;
import model.BettingModel;
import model.HorseSetupModel;
import model.Race;
import model.WeatherCondition;
import view.BettingView;
import view.RaceView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Race raceModel = new Race(20);
            HorseSetupModel horseModel = new HorseSetupModel();
            RaceView view = new RaceView();

            BettingModel bettingModel = new BettingModel();
            BettingView bettingView = new BettingView();
            new BettingController(
                    bettingModel,
                    bettingView,
                    horseModel.horses,
                    raceModel.getWeatherCondition() != null ? raceModel.getWeatherCondition() : WeatherCondition.CLEAR
            );

            new RaceController(raceModel, view, horseModel);

            view.setVisible(true);
        });
    }
}
