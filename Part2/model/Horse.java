package model;

public class Horse
{
    //Fields of class model.Horse
    private final String horseName;
    private char horseSymbol;
    private int distanceTravelled;
    private boolean horseFellDuringRace;
    private double horseConfidence;

    private Breed breed;
    private CoatColour coatColour;
    private Saddle saddle;
    private Horseshoe horseshoe;

    public Horse(char horseSymbol, String horseName, double horseConfidence, Breed breed, CoatColour coatColour, Saddle saddle, Horseshoe horseshoe) {
        this.horseName = horseName;
        this.horseSymbol = horseSymbol;
        this.setConfidence(horseConfidence);

        this.breed = breed;
        this.coatColour = coatColour;
        this.saddle = saddle;
        this.horseshoe = horseshoe;
        this.goBackToStart();
    }


    //Other methods of class model.Horse
    public void fall()
    {
        this.horseFellDuringRace = true;
    }

    public double getConfidence()
    {
        return this.horseConfidence;
    }

    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }

    public String getName()
    {
        return this.horseName;
    }

    public char getSymbol()
    {
        return this.horseSymbol;
    }

    public void goBackToStart()
    {
        this.distanceTravelled = 0;
        this.horseFellDuringRace = false;
    }

    public boolean hasFallen()
    {
        return this.horseFellDuringRace;
    }

    public void moveForward()
    {
        this.distanceTravelled++;
    }

    public void setConfidence(double newConfidence)
    {
        // Cannot set confidence more than 1.0, also cannot be 0.0
        if (newConfidence > 1.0) {
            newConfidence = 1.0;
        } else if (newConfidence <= 0.0) {
            newConfidence = 0.1;
        }
        this.horseConfidence = newConfidence;
    }

    public void setSymbol(char newSymbol)
    {
        this.horseSymbol = newSymbol;
    }

    public Breed getBreed() {
        return this.breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    public CoatColour getCoatColor() {
        return this.coatColour;
    }

    public void setCoatColor(CoatColour coatColour) {
        this.coatColour = coatColour;
    }

    public Saddle getSaddle() {
        return this.saddle;
    }

    public void setSaddle(Saddle saddle) {
        this.saddle = saddle;
    }

    public Horseshoe getHorseshoe() {
        return this.horseshoe;
    }

    public void setHorseshoe(Horseshoe horseshoe) {
        this.horseshoe = horseshoe;
    }

}
