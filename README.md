# Pebble Game 

Welcome to the Pebble Game program. There are 4 java files for the Bag, PebblesGame (which contains the Player class as an nested class), and the test classes BagTest and PebblesGameTest. There are also multiple different csv and text files used for testing which have been included. 

In order to view the files in the jar file, you must open a terminal in the folder where the jar file is kept and enter:
jar -tvf pebbles.jar

This will contain the bytecode (.class), example files and source files (.java).

To run the Game you can run the PebblesGame.java file using the jar file. 

Open a terminal in the folder where the jar file is kept and enter:
java -jar pebbles.jar

The PebblesGame.java file should run and create output files of the tests. 

Running the Tests
Tests can be run using a standard Java IDE such as IntelliJ IDEA and running the test TestSuite.

Running the Tests in IntelliJ Idea: 
1. Create a project in IntelliJ and include the Project SDK 
2. Inside the project there is a src folder with the files a) PebblesGame.java b) Bag.java 
3. There is also a test folder with the files a) BagTest.java b)PebblesGameTest.java c)TestSuite.java d)TestRunner.java and other various .csv and .txt cases that hold the files used in testing 
4. Drag the the test files into the src folder. These files are  a) BagTest.java b)PebblesGameTest.java c)TestSuite.java
5. Drag the other various .txt and .csv files out of the test folder and into the main folder branch. It should not be inside any other folder, just inside the main parent folder.(make sure it is not inside any other folder like the src etc.) 
7. Make sure to add JUnit4 to your class path if not already done so 
8. Run the tests 



