//Imports Required for the Program to Run
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PebblesGameTest {

    //Models the Game
    public PebblesGame pebbleGame;

    /**
     * Creating a new game with the bag file
     * Test Bag Files have been created
     * Used for other tests as the pre-conditions to run
     */
    @Before
    public void setGame() {
        String[] bagLocations = new String[]{"pebbleGameInitialise.csv","pebbleGameInitialise.csv", "pebbleGameInitialise.csv"};
        this.pebbleGame = new PebblesGame(2, bagLocations);
    }

    /**
     * Testing the files inputted by the user for the game to check if there are any that do not exist / are incorrect
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFiles() {
        //Creating the Files
        String[] bagLocations = new String[]{"incorrectFile.txt", "pebbleGameInitialise.csv", "incorrectFile.csv"};
        new PebblesGame(2, bagLocations);
    }

    /**
     * Testing if an incorrect number of players is inputted by the user
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectPlayers() {
        //Creating the Files
        String[] bagLocations = new String[]{"pebbleGameInitialise.csv", "pebbleGameInitialise.csv", "pebbleGameInitialise.csv"};
        new PebblesGame(-4, bagLocations);
    }

    /**
     * Testing the Start Threads to check if the correct number of threads are generated
     */
    @Test
    public void testStartThreads() {
        this.pebbleGame.startThreads();
        int threads = 0;
        for (Thread gameThread : Thread.getAllStackTraces().keySet()) {
            if (gameThread.getName().contains("Player")) {
                threads++;
            }
        }
        // Check if the number of threads is equal to 2 as set by the number of players
        assertEquals(2, threads);
    }

    /**
     * Testing the playerTakesTenPebbles Method by checking size of inputted file and user picking a pebble
     * Checks if it is equal to 1
     */
    @Test
    public void testPlayerTakesTenPebbles() {
        Runnable runningPlayer = this.pebbleGame.new Player(0);

        try {
            //Getting the Method to run
            Method constructor;
            constructor = runningPlayer.getClass().getDeclaredMethod("playerTakesTenPebbles", null);
            constructor.setAccessible(true);

            constructor.invoke(runningPlayer, null);

            //Get the Pebble Hand Attribute
            Field attribute;
            attribute = runningPlayer.getClass().getDeclaredField("pebbleHand");
            attribute.setAccessible(true);

            ArrayList<Integer> checkResult = (ArrayList<Integer>) attribute.get(runningPlayer);

            //Checking if the size is equal to 1
            assertEquals(checkResult.size(), 1);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    /**
     * Testing the checkWinningHand Method to see if the player wins with an arbitrary set of values
     * Inputted values into the array do not equal 100, so the user should not win
     */
    @Test
    public void testIfWinner() {
        //Values from this sum do not sum to 100
        // The values from this sum to 50
        ArrayList<Integer> hand = new ArrayList<>(Arrays.asList(2, 3, 7, 9, 6, 6, 8,9));
        //Check to see it equals 100
        boolean checkWinner = this.pebbleGame.checkWinningHand(hand, "Player 2");
        //Check to see if false
        assertFalse(checkWinner);
    }

    /**
     * Testing the checkWinningHand Method to see if the player wins with the correct value of 100
     * Inputted values into the array equal 100, so the user should win
     */
    @Test
    public void testWinnerIsTrue() {
        //Values from this sum are equal to 100
        ArrayList<Integer> hand = new ArrayList<>(Arrays.asList(5,6,7,8,10,11,12,9,11,1,10,10));
        boolean checkWinner = this.pebbleGame.checkWinningHand(hand, "Player 2");
        //Check to see if true
        assertTrue(checkWinner);
    }

    /**
     * Testing the checkWinningHand Method to see if the player that does not get the first winning hand of 100 does not win
     */
    @Test
    public void testIf100AfterWinner() {
        //Values from this sum are equal to 100
        ArrayList<Integer> hand = new ArrayList<>(Arrays.asList(18,6,7,9,2,3,11,12,14,9,9));
        //Game has already been won
        this.pebbleGame.endOfGame = true;
        boolean checkWinner = this.pebbleGame.checkWinningHand(hand, "Player 2");
        //Check to see if true
        assertTrue(checkWinner);
    }


}
