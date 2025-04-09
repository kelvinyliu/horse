public class Main {

    public static void main(String[] args) {

        Horse horse1 = new Horse('A', "AAA", 0.9);
        Horse horse2 = new Horse('B', "BBB", 0.7);
        Horse horse3 = new Horse('C', "CCC", 0.5);

        Race race = new Race(20);

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        race.startRace();
    }
}
