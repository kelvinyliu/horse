package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceHistoryModel {
    private final Map<String, List<RaceResult>> history = new HashMap<>();

    public void addResult(String horseHash, RaceResult result) {
        history.computeIfAbsent(horseHash, k -> new ArrayList<>()).add(result);
    }

    public List<RaceResult> getHistoryForHorse(String horseHash) {
        return history.getOrDefault(horseHash, new ArrayList<>());
    }

    public Map<String, List<RaceResult>> getAllHistory() {
        return history;
    }
}
