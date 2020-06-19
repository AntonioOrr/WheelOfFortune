/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package woffortune;
import java.util.ArrayList;
/**
 * Class that defines a player for a game with monetary winnings and 
 * a limited number of choices
 * @author Antonio Orr
 */
public class Player {
    private int winnings = 0; // amount of money won
    private String name; // player's name
    private int numGuesses = 0; // number of times they've tried to solve puzzle
    private String prizeWinnings = ""; //string for single earned prize
    private ArrayList<String> prizes = new ArrayList(); //list of available prizes
    private ArrayList<String> prizeList = new ArrayList<String>(); //prizes player has earned
    /**
     * Constructor
     * @param name String that is the player's name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Getter
     * @return int amount of money won so far 
     */
    public int getWinnings() {
        return winnings;
    }

    /**
     * Adds amount to the player's winnings
     * @param amount int money to add
     */
    public void incrementScore(int amount) {
        if (amount < 0) return;
        this.winnings += amount;
    }
    //getter, returns string of prize
    public String getPrize(){
        return prizeWinnings;
    }
    //method that returns string of prizes separated by commas
    public String getPrizeList(){
        String list = String.join(", ", prizeList);
        return list;
    }

    //Adds prize to player's prizeList after changing prize name
    public void incrementPrize(int index){
        this.prizeWinnings = prizes.get(index);
        prizeList.add(prizeWinnings);
    }
    /**
     * Getter 
     * @return String player's name 
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return int the number of guesses used up 
     */
    public int getNumGuesses() {
        return numGuesses;
    }

    /** 
     * Add 1 to the number of guesses used up
     */
    public void incrementNumGuesses() {
        this.numGuesses++;
    }
    
    /**
     * Resets for a new game (for number of guesses but not winnings)
     *
     */
    public void reset() {
        this.numGuesses = 0;
    }
    
    public void bankrupt() {
        this.winnings = 0;
        //clears list of player's prizes
        prizeList.clear();
        this.prizeWinnings = "";
    }
    //list of available prizes
    public void PrizeAdding(){
      prizes.add("A pair of shoes");
      prizes.add("Car");
      prizes.add("Concert tickets");
      prizes.add("Dirtbike");
      prizes.add("Resort tickets");
  }
    
}
