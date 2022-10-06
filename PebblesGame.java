/**
 * This is the Pebble Game class which contains the main method for the game to be ran
 * as mentioned in the specification for this program
 * @author 700047391 700074574
 * @version 17/11/2021
 */

// Imports required for the program to work
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class PebblesGame {
    // Attributes for the PebbleGame class
    private static final Bag[] blackBags = new Bag[3];
    private static final Bag[] whiteBags = new Bag[3];

    //Constructs the Number of Players
    private final int noOfPlayers;

    //Boolean to determine if the Game has ended and been won by a player
    boolean endOfGame = false;



    //Starts the Threads of the Game
    public void startThreads() {
        //The players should act as concurrent threads, with the threading commencing before drawing their initial pebbles
        for (int i = 0; i < this.noOfPlayers; i ++) {
            Runnable runningPlayer = new Player(i);

            Thread game = new Thread(runningPlayer);
            game.setName("Player " + i);
            game.start();
        }
        System.out.println("Starting the threads of game");
    }

    /**
     * Method used to calculate if any player has won the game or not by checking the sum of the pebbles
     * and ensuring that the amount of pebbles is equal to 10
     * @param pebbleHand - the list of pebbles a player has
     * @param playerID - the player's number
     * @return true if the player has won, false if they have not
     */
    public synchronized boolean checkWinningHand(ArrayList<Integer> pebbleHand, String playerID){
        if (this.endOfGame) {
            return true;
        } else {
            //Initial Value is 0
            int pebbleHandValue = 0;
            //Iterates through until the value is 100
            for (Integer sum : pebbleHand) {
                pebbleHandValue += sum;
            }

            if (pebbleHandValue == 100){
                this.endOfGame = true;
                System.out.println("Looks like we have a winner to this game!!!. " + playerID + " has won!! Congratulations!!!");
                return true;
            } else {
                return false;
            }
        }
    }
    /**
     * Constructor for the PebblesGame Class
     * Puts the black and white bags and puts them in 2 arrays
     *
     * @param noOfPlayers - the number of players to play the game
     * @param locationBag - a string array of bagLocations
     */
    public PebblesGame(int noOfPlayers, String[] locationBag)  {
        //If Players are less than 1 and less than 3 files have been entered, throw error
        if (noOfPlayers < 1 || locationBag.length != 3) {
            throw new IllegalArgumentException();
        } else {
            //Get the number of players and pair the black and white bags
            this.noOfPlayers = noOfPlayers;
            //The bag a pebble is discarded to, must be the paired white bag of the black bag that the last
            // pebble draw was from. Specifically, if the last pebble was drawn from X, the next discard
            //should be to A, if the last pebble drawn was from Y, the next discard should be to B, and
            //if the last pebble drawn was from Z, the next discard should be to C.
            String[][] pairing = {{"A", "X"}, {"B", "Y"}, {"C", "Z"}};
            for (int i = 0; i < 3; i++) {
                blackBags[i] = new Bag(pairing[i][1], locationBag[i], noOfPlayers);
                whiteBags[i] = new Bag(pairing[i][0]);
            }
        }
    }

    /**
     * The main method of the program which starts with the introductory message
     * followed by initialization of the game where the game would contain running with multiple threads
     * and get the winner of the game
     * @param args - arguments mentioned in the method
     */
    public static void main(String[] args) {
        //User Inputs
        Scanner intro = new Scanner(System.in);
        System.out.println("Welcome to the Pebble Game!! \n" +
                "You will be asked for to enter the number of players.\n " +
                "And then for the location of the three files in turn containing comma separated integer values for the pebble weights.\n" +
                "The integer values must be strictly positive \n" +
                "The game will then be simulated, and output written to files in this directory.");

        //checkInputs makes sure all the inputs are correct and there is no error
        boolean checkInputs = false;


        while (!checkInputs) {
            System.out.println("Please enter the number of players: ");
            String totalPlayers = intro.nextLine();

            int noOfPlayers = 0;
            // Checks if the user has inputted E to exit the game
            if (totalPlayers.equals("E") || totalPlayers.equals("e")) {
                break;
            } else {
                try {
                    //Convert to an integer value
                    noOfPlayers = Integer.parseInt(totalPlayers);
                } catch (NumberFormatException e) {
                    System.out.println("Error");
                }
            }
            if (noOfPlayers > 0) {
                boolean fileCollected = true;
                String[] bagLocations = new String[3];
                for (int i = 0; i < 3; i++) {
                    System.out.println("Please enter a location of bag number " + i + " to load: ");
                    String bagLocation = intro.nextLine();

                    if (bagLocation.length() > 0) {
                        if (bagLocation.equals("E")|| bagLocation.equals("e")) {
                            //fileCollected represents the files inputted by user and if they have all passed the requirements
                            fileCollected = false;
                            checkInputs = true;
                            break;
                            //Check for txt or csv file
                        } else if (bagLocation.contains(".csv") || bagLocation.contains(".txt")) {
                            bagLocations[i] = bagLocation;
                        } else {
                            fileCollected = false;
                            break;
                        }
                    } else {
                        fileCollected = false;
                        break;
                    }
                }

                if (fileCollected) {
                    try {
                        PebblesGame game = new PebblesGame(noOfPlayers, bagLocations);
                        game.startThreads();
                        checkInputs = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Please enter the location of the file as the bag has an invalid directory.");
                    }
                } else {
                    System.out.println("This is an invalid file name. Please enter location of bag number 0 to load again.");
                }
            }
        }
        //Close the scanner of user inputs
        intro.close();
    }
    /**
     * A nested Player class which implements the Runnable class
     * It contains details about the Player's number, the total weight of the pebbles each player has
     * and the list of the pebbles they have
     */

    //Players should be implemented as nested classes within a PebbleGame application.
    class Player implements Runnable {
        //PlayerID or number
        private final int playerID;
        private String playerName;
        private int lastBag;
        //Array List of pebbleHand
        private ArrayList<Integer> pebbleHand = new ArrayList<>();
        private Random random = new Random();
        //Boolean for whether to create output files or not
        private boolean gameInfo;

        /**
         * Create new log file for the player
         */
        private String create_player_file() {
            String path = "Player" + playerID + "_output.txt";
            File player_file = new File(path);
            try {
                if (player_file.exists()) {
                    player_file.delete();
                }

                player_file.createNewFile();
                return path;
                // If unsuccessful throw error
            } catch (IOException e) {
                System.out.println("Error: Could not create " + path);
                return null;
            }
        }

        /**
         * A constructor
         * @param playerID - number of the Player
         */
        Player(int playerID) {
            this.playerID = playerID;
            //Creating the Output Files
            create_player_file();
        }

        /**
         * The method removes a random pebble from the selected bag and discards it
         * Updates the player's log of the latest changes
         * And shows the remaining pebbles left with that player
         */
        private void discard() {
            // Randomly selects a pebble from 0 - 9 (considered as 10 pebbles)
            int n = random.nextInt(9);
            Bag selectedRandomBag = whiteBags[this.lastBag];
            // Gets the pebble removed from the player
            int pebbleRemoved = this.pebbleHand.remove(n);
            // Pebble gets added to the bag
            selectedRandomBag.bagPebbles.add(pebbleRemoved);
            System.out.println("Player " + this.playerID + " has discarded  " + pebbleRemoved + " to bag " + selectedRandomBag.getName() + "\n" + "Player " + this.playerID + " hand is " + pebbleHand);
            log("Player " + playerID + " has discarded a " + pebbleRemoved + " to bag " + selectedRandomBag.getName());

        }

        /**
         * Log creates the output.txt file for each player
         * @param text takes the new line of text in each player file
         */
        private void log(String text) {
            if (gameInfo) System.out.println(text);
            Writer outputText;
            try {
                outputText = new BufferedWriter(new FileWriter("player" + playerID + "_output.txt", true));
                outputText.append(text).append("\n");
                outputText.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * A method where the player would take pebbles and have them added to a random bag selected
         * by the random object and check if there is the black bag is empty or not which would require all
         * the pebbles in the white bag to be transferred to the black bag
         */
        private void playerTakesTenPebbles() {
            boolean pebbleTaken = false;
            while (!pebbleTaken) {
                Random pebblePick = new Random();
                // The random object selects a random bag among the 3 bags
                int j = pebblePick.nextInt(3);

                Bag selectedRandomBag = blackBags[j];
                int pebbleSelected = selectedRandomBag.selectPebble();

                if (pebbleSelected == 1) {
                    pebbleTaken = false;
                } else {
                    this.pebbleHand.add(pebbleSelected);
                    pebbleTaken = true;
                    //Updates to let the program know this would be considered the latest bag being picked
                    //Add pebble if successful
                    this.lastBag = j;
                    //Check to see if there is an exchanged needed for the pebbles
                    Bag.checkBagExchangeNeeded(blackBags[j], whiteBags[j]);

                    System.out.println("Player " + this.playerID + " has drawn a " + pebbleSelected + " from bag " + selectedRandomBag.getName() + "\n" + "Player " + this.playerID + " hand is " + pebbleHand);
                    log("Player " + playerID + " has drawn a " + pebbleSelected + " from bag " + selectedRandomBag.getName() + "\n" + "Player " + playerID + " hand is " + pebbleHand);
                }
            }
        }


        /**
         * This is the method where the player would be actively taking part and playing the game
         * Until a winner is not declared, the game would continue by constantly looping by discarding
         * and picking new pebble
         */
        @Override
        public void run() {
            // At the beginning of the game, a player would randomly pick 10 pebbles
            for (int i = 0; i < 10; i++) {
                // This method would place the ten pebbles in random bags
                this.playerTakesTenPebbles();
            }
            // Check if the player has won or not
            while (!checkWinningHand(this.pebbleHand, this.playerName)) {
                // Discards 1 pebble
                this.discard();
                // Allows the player to pick another pebble
                this.playerTakesTenPebbles();
            }
        }


    }
}

