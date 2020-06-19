/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package woffortune;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.InputMismatchException;
/**
 * WofFortuneGame class
 * Contains all logistics to run the game
 * @author Antonio Orr
 */
public class WofFortuneGame {

    private boolean puzzleSolved = false;

    private Wheel wheel;
    //private Player player1;
    private String phrase = "Once upon a time";
    //private Letter[] letter_array = new Letter[16];
    private ArrayList<Letter> letterArray = new ArrayList<Letter>();
    private ArrayList<String> phrases = new ArrayList<String>();
    private ArrayList<Player> players = new ArrayList();

    /**
     * Constructor
     * @param wheel Wheel 
     * @throws InterruptedException 
     */
    public WofFortuneGame(Wheel wheel) throws InterruptedException {
        // get the wheel
        this.wheel = wheel;
        //for some reason I had to catch Exception whenever setUpGame() was called (i dont know why)
        try { // do all the initialization for the game
        setUpGame();
        } catch (Exception ii){
            System.out.println("An error has occurred");
        }
        

    }
    
    /**
     * Plays the game
     * @throws InterruptedException 
     */
    public void playGame() throws InterruptedException {
        // while the puzzle isn't solved, keep going
        while (!puzzleSolved){
            // let the current player play
            for (int i = 0; i < players.size(); i++){
            
                playTurn(players.get(i));
                //if puzzle is solved, round ends
                if (puzzleSolved)
                    break;
            } 
        }
        //boolean reset to false in case of next round
        puzzleSolved = false;
        //clears letterArray
        letterArray.clear();
        //chooses a random phrase
        Random rand1 = new Random();
        phrase = phrases.get(rand1.nextInt(10));
        for (int i = 0; i < phrase.length(); i++) {
         
            letterArray.add(i, new Letter(phrase.charAt(i)));
            
        }
    }
    
    /**
     * Sets up all necessary information to run the game
     */
    private void setUpGame() throws Exception{
        PhraseAdding();
        //create boolean variable
        boolean valid = true;
        int people = 0;
        //do-while loop repeats body until there is no InputMismatchError
        do {
        try {
        valid = true;
        Scanner sc = new Scanner(System.in);
        System.out.println("How many people are going to play?");
        people = sc.nextInt();
        
        } catch (InputMismatchException oe){
                System.out.println("Error: InputMismatchException. Please only"
                    + " insert the NUMBER of players next time.");
                valid = false;
                }
          catch (Exception ie){
              System.out.println("An error has occurred during input process...");
              valid = false;
          }
        } while(!valid);
        //create array of strings for players
        String[] names = new String[people];
        //itterates through each player name, and adds to players ArrayList
        for (int i = 0; i < names.length; i++){
        
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the name of player " + (i + 1) + ":");
            names[i] = sc.next();
            players.add(new Player(names[i]));
           
        }
        // create a single player 
        //player1 = new Player("Player1");
        
        // print out the rules
        System.out.println("RULES!");
        System.out.println("Each player gets to spin the wheel, to get a number value");
        System.out.println("Each player then gets to guess a letter. If that letter is in the phrase, ");
        System.out.println(" the player will get the amount from the wheel for each occurence of the letter");
        System.out.println("If you have found a letter, you will also get a chance to guess at the phrase");
        System.out.println("Each player only has three guesses, once you have used up your three guesses, ");
        System.out.println("you can still guess letters, but no longer solve the puzzle.");
        System.out.println();
        try {
        System.out.println("Would you like to create a phrase? (yes / no)");
        Scanner sp = new Scanner(System.in);
        String want = sp.next();
        while(!(want.equalsIgnoreCase("yes")) && !(want.equalsIgnoreCase("no"))){
            System.out.println("Enter either yes or no");
            want = sp.next();
        }
        if (want.equalsIgnoreCase("yes")) {
            //allow user to enter phrase
            Scanner st = new Scanner(System.in);
            System.out.println("Enter a phrase: ");
            String ePhrase = st.nextLine();
            //replace default phrase with user-entered phrase
            phrase = ePhrase;
        }
        else {
            //generate random number from 0 to 9
            Random rand = new Random();
            int randChoice = rand.nextInt(10);
            //replaces default phrase with random phrase in phrases array
            phrase = phrases.get(randChoice);
            //System.out.println("random phrase index selected: " + randChoice);
        }
        } catch (Exception ae){
            System.out.println("An error has occurred during input process...");
        }
        
        // for each character in the phrase, create a letter and add to letters array
        for (int i = 0; i < phrase.length(); i++) {
            //letter_array[i] = new Letter(phrase.charAt(i));
            letterArray.add(i, new Letter(phrase.charAt(i)));
            
        }
        
        // setup done
    }
    
    /**
     * One player's turn in the game
     * Spin wheel, pick a letter, choose to solve puzzle if letter found
     * @param player
     * @throws InterruptedException 
     */
    private void playTurn(Player player) throws InterruptedException {
        int money = 0;
        Scanner sc = new Scanner(System.in);
        
        System.out.println(player.getName() + ", you have $" + player.getWinnings());
        System.out.println("Spin the wheel! <press enter>");
        sc.nextLine();
        System.out.println("<SPINNING>");
        Thread.sleep(200);
        //condition for interruption
        if (Thread.interrupted())
            throw new InterruptedException("Error: Interrupted Exception");
        Wheel.WedgeType type = wheel.spin();
        System.out.print("The wheel landed on: ");
        switch (type) {
            case MONEY:
                money = wheel.getAmount();
                System.out.println("$" + money);
                break;
                
            case LOSE_TURN:
                System.out.println("LOSE A TURN");
                System.out.println("So sorry, you lose a turn.");
                return; // doesn't get to guess letter
                
                
            case BANKRUPT:
                System.out.println("BANKRUPT");
                player.bankrupt();
                return; // doesn't get to guess letter*/
                
            //if wheel chose PRIZE    
            case PRIZE:
                //creates ArrayList of available prizes
                player.PrizeAdding();
                System.out.println("PRIZE");
                System.out.println("You get to take the mystery prize if you guess a letter right!");
                break;
                
            default:
                
        }
        System.out.println("");
        System.out.println("Here is the puzzle:");
        showPuzzle();
        System.out.println();
        System.out.println(player.getName() + ", please guess a letter.");
        //String guess = sc.next();
        
        char letter = sc.next().charAt(0);
        if (!Character.isAlphabetic(letter)) {
            System.out.println("Sorry, but only alphabetic characters are allowed. You lose your turn.");
        } else {
            // search for letter to see if it is in
            int numFound = 0;
            for (Letter l : letterArray) {
                if ((l.getLetter() == letter) || (l.getLetter() == Character.toUpperCase(letter))) {
                    l.setFound();
                    numFound += 1;
                }
            }
            if (numFound == 0) {
                System.out.println("Sorry, but there are no " + letter + "'s.");
            } else {
                if (numFound == 1) {
                    System.out.println("Congrats! There is 1 letter " + letter + ":");
                } else {
                    System.out.println("Congrats! There are " + numFound + " letter " + letter + "'s:");
                }
                System.out.println();
                showPuzzle();
                System.out.println();
                //separated money and prize choices
                switch (type){
                    case MONEY:
                        player.incrementScore(numFound*money);
                        System.out.println("You earned $" + (numFound*money) + ", and you now have: $" + player.getWinnings());
                    break;
                    case PRIZE:
                        Random rand = new Random();
                        int randPrize = rand.nextInt(5);
                        player.incrementPrize(randPrize);
                        System.out.println("You got the prize: " + player.getPrize());
                    break;
                    
                    default:
                }

                System.out.println("Would you like to try to solve the puzzle? (Y/N)");
                letter = sc.next().charAt(0);
                System.out.println();
                if ((letter == 'Y') || (letter == 'y')) {
                    solvePuzzleAttempt(player);
                }
            }
            
            
        }
        
    }
    
    /**
     * Logic for when user tries to solve the puzzle
     * @param player 
     */
    private void solvePuzzleAttempt(Player player) {
        
        if (player.getNumGuesses() >= 3) {
            System.out.println("Sorry, but you have used up all your guesses.");
            return;
        }
        
        player.incrementNumGuesses();
        System.out.println("What is your solution?");
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        String guess = sc.next();
        if (guess.compareToIgnoreCase(phrase) == 0) {
            System.out.println("Congratulations! You guessed it!");
            puzzleSolved = true;
            // Round is over. Write message with final stats
            // TODO
            System.out.println("Final Stats:");
            //displays each player's final stats for round
            for (int i = 0; i < players.size(); i++){
                //distincts the winner from other players
                if (player.getName() == players.get(i).getName()){
                    System.out.println(players.get(i).getName().concat("(WINNER)") + ": $" + players.get(i).getWinnings()
                                            + ", " + players.get(i).getPrizeList());
                }
                else {
                   System.out.println(players.get(i).getName() + ": $" + players.get(i).getWinnings()
                   + ", " + players.get(i).getPrizeList()); 
                }
                
            }
        }
        else {
            System.out.println("Sorry, but that is not correct.");
        }
    }
    
    /**
     * Display the puzzle on the console
     */
    private void showPuzzle() {
        System.out.print("\t\t");
        for (Letter l : letterArray) {
            if (l.isSpace()) {
                System.out.print("   ");
            } else {
                if (l.isFound()) {
                    System.out.print(Character.toUpperCase(l.getLetter()) + " ");
                } else {
                    System.out.print(" _ ");
                }
            }
        }
        System.out.println();
        
    }
    
    /**
     * For a new game reset player's number of guesses to 0
     */
    public void reset() {
        for (int i = 0; i < players.size(); i++){
            players.get(i).reset();
        }
        //player1.reset();
    }
  public void PhraseAdding(){
      //ten phrases to be added to phrases array when called
      phrases.add("I like dogs");
      phrases.add("Please wash your hands");
      phrases.add("Dont talk to strangers");
      phrases.add("Spaghetti and meatballs");
      phrases.add("Turn up for what");
      phrases.add("Nice to be alive");
      phrases.add("A path to wickedness");
      phrases.add("Cant think of phrases");
      phrases.add("Lions and tigers and bears");
      phrases.add("Boy who cried wolf");
  }
 
}
