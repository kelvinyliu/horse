
/**
 * A class for horse for a racing simulator
 * 
 * @author Kelvin Liu 
 * @version 1
 */
public class Horse
{
    //Fields of class Horse
    private final String horseName;
    private char horseSymbol;
    private int distanceTravelled;
    private boolean horseFellDuringRace;
    private double horseConfidence;
    
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
       this.horseName = horseName;
       this.setSymbol(horseSymbol);
       this.setConfidence(horseConfidence);

       // Dynamic
//       this.distanceTravelled = 0;
//       this.horseFellDuringRace = false;
        this.goBackToStart();
    }

    //Other methods of class Horse, mainly getters and setters
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

}
