import org.junit.Test;
import static org.junit.Assert.*;

public class BagTest {
    /**
     * Testing the Bag Constructor with an bag that does not exist
     */
    @Test (expected = IllegalArgumentException.class)
    public void testBagDoesNotExist() {
        //Creating a Random Bag
        Bag randomBag = new Bag("Random", "sampleBag.csv", 2);
    }

    /**
     * Testing the Bag Constructor with a Bag that contains anything other than positive numbers for the pebbles
     */
    @Test (expected = IllegalArgumentException.class)
    public void testInvalidBagValues() {
        // Creating randomBagValues.csv and randomBagValues.txt files
        Bag testRandomBagCSV = new Bag("Random", "randomBagValues.csv", 2);
        Bag testRandomBagTXT = new Bag("Random", "randomBagValues.txt", 2);

    }

    /**
     * Testing the Bag Constructor for condition regarding less pebbles than eleven times the players.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testTooFewPebbles() {
        // Bag created with number fewer than 11 x Players
        Bag randomBag = new Bag("Random", "tooFewPebbles.csv", 5);
    }

    /**
     * Testing the Bag Constructor for an empty bag
     */
    @Test
    public void testEmptyBag() {
        //Creating an empty bag where the 'contents' of the file / bag pebbles are equal to 0.
        Bag emptyBag = new Bag("empty");
        // Check if the bag is empty  
        assertEquals(emptyBag.bagPebbles.size(), 0);
    }

    /**
     * Testing the selectPebble method for an empty bag to check if it throws the error stating that there are zero pebbles left in the black bag
     */
    @Test
    public void testSelectPebble() {
        //Creating an empty bag where the 'contents' of the file / bag pebbles are equal to 0.
        Bag emptyBag = new Bag("empty");
        //Check if the value is equal to 1
        assertEquals(emptyBag.selectPebble(), 1);
    }


    /**
     * Testing the checkBagExchangeNeeded Function '
     * Check to see if the Bags pair using the sizes of an empty and full bag respectively
     */
    @Test
    public void testCheckBagExchangeNeeded() {
        //Creating an empty black bag
        Bag blackBag = new Bag("X");
        //Creating a white bag that is full
        Bag whiteBag = new Bag("Y", "bagExchange.csv", 2);
        
        int whiteBagSize = whiteBag.bagPebbles.size();
        //Exchange the Bags 
        Bag.checkBagExchangeNeeded(blackBag, whiteBag);
        //Check that the size of contents are equal 
        assertEquals(blackBag.bagPebbles.size(), whiteBagSize);
    }

}
