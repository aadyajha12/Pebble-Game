/**
 * Class which handles the functionalities of Bag
 * @version 17/11/21
 */

//Imports required for the class to run
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.FileReader;
import java.io.IOException;


/**
 * Bag Class
 */
public class Bag {
    // Attributes of Bag Class including Name and the Contents of the Bag called BagPebbles
    private final String name;
    public List<Integer> bagPebbles = Collections.synchronizedList(new ArrayList<>());

    /**
     * Constructor for Bag class
     * @param name - Name (E.g. A,B,C or X,Y,Z)
     */

    public Bag(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the Bag
     */
    public String getName() {
        return this.name;
    }

    /**
     * Constructor for Bag class with the attributes
     * Collects inputs from the Bag Class
     * Checks that the number of pebbles and players are suitable given specification
     * Reads the contents from the File to add into the bag
     *
     * @param name - The name of the bag
     * @param bagLocation - The location of the bag
     * @param noOfPlayers - The number of players
     * @throws IllegalArgumentException
     */

    public Bag(String name, String bagLocation, int noOfPlayers) throws IllegalArgumentException {
        this.name = name;
        boolean correctBag = true;
        String[] readBagPebbles = {};

        //Checking for the files to be read from
        try {
            BufferedReader readBag = new BufferedReader(new FileReader(bagLocation));
            readBagPebbles = readBag.readLine().split(",");
        } catch (IOException e) {
            System.out.println("Some of the files cannot be found.");
            correctBag = false;
        }

        //If the Bag is correct and can be read from then
        if (correctBag) {
            // The program should ensure that each black bag contains at least 11 times as many pebbles as players
            if (readBagPebbles.length >= (noOfPlayers * 11)) {

                //Checking for unusual cases in the bag 
                for (String bagPebbleContents : readBagPebbles) {
                    try {
                        int pebbleValue = Integer.parseInt(bagPebbleContents);
                        if (pebbleValue < 0) {
                            System.out.println("There is an error in " + bagLocation + "." + "\n" + "Negative numbers have been detected");
                            correctBag = false;
                            break;
                        }
                        //Adding the pebble into the Bag
                        this.bagPebbles.add(pebbleValue);

                    } catch (NumberFormatException e) {
                        System.out.println("There is an error in " + bagLocation + "." + "\n" + "An invalid format has been found");
                        correctBag = false;
                        break;
                    }
                }
            } else {
                correctBag = false;
                System.out.println("There is an error in " + bagLocation + ".");
            }
        }

        if (!correctBag) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Allows the program to select a pebble
     */
    // Picks a Random Pebble from the Bag File
    synchronized int selectPebble() {
        int totalPebbles = this.bagPebbles.size();
        //If the pebbles are equal to or less than 0
        //Pebbles must have a strictly positive weight – therefore the program should detect and warn the user if they are trying to use files where this is not the case.
        if (totalPebbles <= 0) {
            //If a player attempts to draw from an empty black bag, the player should attempt to select another bag until they select a bag with pebbles.
            System.out.println("Wait!! Hold on...there are zero pebbles left in the black bag");
            return 1;
        } else {
            //The process of drawing pebbles from a black bag should be uniformly at random – that is it should be equally probable to draw any of the pebbles in the bag.
            Random random = new Random();
            int j = random.nextInt(totalPebbles);
            return this.bagPebbles.remove(j);
        }
    }
    /**
     * This synchronized method checks to see if any exchanged is required
     * A change will occur when there are no pebbles left in the black bag
     * It's pair white bag will be the chosen one to exchange the bags
     * @param blackBag - the black bag which would have no pebbles left
     * @param whiteBag - the white bag who is paired with the empty black bag
     */
    public synchronized static void checkBagExchangeNeeded(Bag blackBag, Bag whiteBag){
        //If the blackBag is empty
        if (blackBag.bagPebbles.isEmpty()){
            //Add all pebbles from the whiteBag into blackBag
            blackBag.bagPebbles.addAll(whiteBag.bagPebbles);
            //Clear the whiteBag pebbles because the contents have moved into the blackBag
            //Once a black bag is empty, all the pebbles from a white bag should be emptied into it
            whiteBag.bagPebbles.clear();

        }

    }
}

