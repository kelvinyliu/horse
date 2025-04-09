package control;

import model.*;
import view.BettingView;
import view.HorseSetupPanelView;
import view.RaceView;

import java.util.ArrayList;
import java.util.List;

public class RaceController {
    private final Race model;
    private final RaceView view;
    private final HorseSetupModel horseModel;
    private final RaceHistoryModel historyModel;
    private final HorseStatsController statsController;
    private final BettingModel bettingModel;
    private final BettingController bettingController;

    private boolean currentlyRacing = false;

    public RaceController(Race model, RaceView view, HorseSetupModel horseModel) {
        this.model = model;
        this.view = view;
        this.horseModel = horseModel;
        this.historyModel = new RaceHistoryModel();
        this.statsController = new HorseStatsController(historyModel);
        this.bettingModel = new BettingModel();
        BettingView bettingView = new BettingView();

        this.bettingController = new BettingController(
                bettingModel,
                bettingView,
                horseModel.horses,
                model.getWeatherCondition()
        );

        new RaceConfigController(
                model,
                view,
                horseModel,
                bettingModel,
                bettingController
        );

        // Button hooks
        view.getStartRaceButton().addActionListener(e -> new Thread(this::startRace).start());

        view.getResetHorsesButton().addActionListener(e -> {
            for (Horse h : horseModel.horses) {
                h.goBackToStart();
            }
            view.getRaceCanvas().resetLanes(horseModel);
            view.getLogPanel().log("Horses reset to starting positions.");
        });

        view.getOpenBettingButton().addActionListener(e -> bettingController.show());

        view.getViewStatsButton().addActionListener(e -> statsController.openStatsWindow());
    }

    private double getWeatherConfidenceModifier(WeatherCondition weather) {
        return switch (weather) {
            case MUDDY -> -0.03;
            case ICY -> -0.05;
            case CLEAR -> 0.0;
        };
    }

    private double getSaddleConfidenceModifier(Saddle saddle) {
        return switch (saddle) {
            case SADDLE -> 0.05;
            case NONE -> 0.0;
        };
    }

    private double getHorseshoeConfidenceModifier(Horseshoe horseshoe) {
        return switch (horseshoe) {
            case STEEL_HORSESHOES -> 0.03;
            case RUBBER_SHOES -> 0.01;
            case NONE -> 0.0;
        };
    }


    private void startRace() {
        if (currentlyRacing) return;

        // get horse config from view
        ArrayList<HorseSetupPanelView.HorseData> dataList = view.getSetupWindow().getHorseSetupPanel().getAllHorseData();
        if (dataList.size() < 2) {
            view.getLogPanel().log("You need at least 2 horses to race!");
            return;
        }

        horseDataToHorses(dataList);

        double weatherConfModifier = getWeatherConfidenceModifier(model.getWeatherCondition());
        for (Horse h : horseModel.horses) {
            double confidence = h.getConfidence();
            confidence += weatherConfModifier;
            confidence += getSaddleConfidenceModifier(h.getSaddle());
            confidence += getHorseshoeConfidenceModifier(h.getHorseshoe());

            h.setConfidence(confidence);
        }

        view.getRaceCanvas().setupTrack(model.getRaceLength(), horseModel);

        // begin race loop
        currentlyRacing = true;
        view.getResetHorsesButton().setEnabled(false);
        for (Horse h : horseModel.horses) h.goBackToStart();

        boolean finished = false;

        long raceStart = System.currentTimeMillis();
        while (!finished) {
            for (Horse h : horseModel.horses) moveHorse(h);

            for (int i = 0; i < horseModel.getHorseCount(); i++) {
                Horse h = horseModel.horses.get(i);
                String label = h.getSymbol() + (h.hasFallen() ? " ❌" : "");
                view.getRaceCanvas().updateLane(i, h.getDistanceTravelled(), label);
            }

            boolean allFallen = horseModel.horses.stream().allMatch(Horse::hasFallen);
            if (allFallen) {
                view.getLogPanel().log("All horses have fallen. No winner.");
                view.getResetHorsesButton().setEnabled(true);
                currentlyRacing = false;
                break;
            }

            finished = horseModel.horses.stream().anyMatch(h -> h.getDistanceTravelled() >= model.getRaceLength());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long raceEnd = System.currentTimeMillis();
        double totalTimeSeconds = (raceEnd - raceStart) / 1000.0;

        Horse wonHorse = determineWinningHorse();

        if (wonHorse != null) {
            double winnings = bettingModel.calculateWinnings(wonHorse.getName());
            view.getLogPanel().log("Total winnings paid out: $" + String.format("%.2f", winnings));
        }
        bettingModel.resetBets();
        List<HorseSetupPanelView.HorseData> allHorseData = view.getSetupWindow().getHorseSetupPanel().getAllHorseData();
        for (int i = 0; i < horseModel.horses.size(); i++) {
            Horse h = horseModel.horses.get(i);
            // get the hash
            String hash = allHorseData.get(i).getHash();

            historyModel.addResult(hash, new RaceResult(
                    ((double) model.getRaceLength() / (double) Math.max(h.getDistanceTravelled(), 1)) * totalTimeSeconds,
                    h == wonHorse,
                    model.getRaceLength(),
                    h.getDistanceTravelled(),
                    h.getConfidence(),
                    model.getTrackShape(),
                    model.getWeatherCondition()
            ));
        }

        view.getResetHorsesButton().setEnabled(true);
        currentlyRacing = false;
    }

    private Horse determineWinningHorse() {
        // Determine winners
        ArrayList<Horse> winners = new ArrayList<>();
        for (Horse h : horseModel.horses) {
            if (h.getDistanceTravelled() >= model.getRaceLength()) {
                winners.add(h);
            }
        }
        if (winners.isEmpty()) {
            return null;
        }
        Horse winner;
        if (winners.size() == 1) {
            view.getLogPanel().log("Horse " + winners.getFirst().getName() + " has won!");
            winner = winners.getFirst();
        } else {
            Horse chosen = winners.get((int) (Math.random() * winners.size()));
            winner = chosen;
            view.getLogPanel().log("Close call! determined winner: " + chosen.getName());
        }
        return winner;
    }

    private void horseDataToHorses(ArrayList<HorseSetupPanelView.HorseData> dataList) {
        // Convert to Horse objects and store
        horseModel.horses.clear();
        for (HorseSetupPanelView.HorseData data : dataList) {
            Horse newHorse = new Horse(
                    data.symbol,
                    data.name,
                    data.confidence,
                    data.breed,
                    data.coatColour,
                    data.saddle,
                    data.horseshoe
            );
            horseModel.addHorse(newHorse);
        }
    }

    private void moveHorse(Horse h) {
        if (!h.hasFallen()) {
            if (Math.random() < h.getConfidence()) h.moveForward();
            if (Math.random() < 0.1 * h.getConfidence() * h.getConfidence()) h.fall();
        }
    }
}
