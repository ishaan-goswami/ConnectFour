package cs1302.game; 
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import cs1302.game.ConnectFour;
import cs1302.gameutil.GamePhase;
import cs1302.gameutil.Token;
 
/**                                                                                                                                                                     
* A class to allow for CLI gameplay of a connect four game.                                                                                                            
*/
public class ConnectFourCLI {

   private static final Scanner STD_IN = new Scanner(System.in);
   public static void main(String[] args) {
      ConnectFourCLI.printWelcome();
      ConnectFour game = ConnectFourCLI.manualSetUp();
      ConnectFourCLI.play(game);
   } // main                                                                                                                                                           

 
   public static ConnectFour manualSetUp() {
      while (true) {
         try {
            System.out.print("\nPlease enter the number of row for the board (6-9): ");
            int rows = Integer.parseInt(STD_IN.nextLine());
            System.out.print("\nPlease enter the number of columns for the board (7-9): ");
            int columns = Integer.parseInt(STD_IN.nextLine());
            ConnectFour game = new ConnectFour(rows,columns);
            System.out.print("\nEnter a color for Player 1's token " + "(BLUE, CYAN, GREEN, PURPLE, RED, YELLOW): ");
            Token player1 = Token.valueOf(STD_IN.nextLine().toUpperCase());
            System.out.print("\nEnter a color for Player 2's token " + "(BLUE, CYAN, GREEN, PURPLE, RED, YELLOW): ");
            Token player2 = Token.valueOf(STD_IN.nextLine().toUpperCase());
            game.setPlayerTokens(player1, player2);
            return game;
         } catch (NumberFormatException nfe) {
            System.out.println("\n*** Rows and columns must be valid integers. ***\n");
            nfe.printStackTrace();
         } catch (IllegalArgumentException iae) {
            System.out.println("\n*** Could not construct the game. " +
                  "Either the number of rows/cols is too large/small " +
                  "or a selected player color is invalid. ***\n");
            iae.printStackTrace();
         } catch (IllegalStateException ise) {
            System.out.println(
                   "\n*** Game must be in the NEW phase when initially " +
                   "setting the player tokens. ***\n");
         } // try                                                                                                                                                    
      } // while                                                                                                                                                      
   } // manualSetUp                                                                                                                                                     

   public static void printFileLines(String filename) {
      try {
         File inFile = new File(filename);
         Scanner input = new Scanner(inFile);
         while (input.hasNextLine()) {
            System.out.println(input.nextLine());
         }
         input.close();
      } catch (FileNotFoundException fnfe) {
         System.out.println("Missing " + filename + ". Make sure it is in the" +
            "resource folder in the projects home directory.");
         fnfe.printStackTrace();
         System.exit(1);
      } // try                                                                                                                                                       
   } // printFileLines                                                                                                                                                
 
   public static void printHeader() {
      printFileLines("resources/connectfour.txt");
   } // printHeader                                                                                                                                                   
 

   public static void printWelcome() {
      printFileLines("resources/welcome.txt");
      System.out.println();
      ConnectFourCLI.printHeader();
   } // printWelcome                                                                                                                                                  


   public static void printGameOver() {
      printFileLines("resources/gameover.txt");
   } // printGameOver                                                                                                                                                 

   public static void play(ConnectFour game) {
      int player = 0;
      boolean isWon = false;
  
      while (game.getPhase() != GamePhase.OVER) {
         try {
            ConnectFourCLI.printHeader();
            System.out.println();
            game.printGrid();
            System.out.print("\nPlayer " + player + ", enter a column to drop your token: ");
            int col = Integer.parseInt(STD_IN.nextLine().trim());
            game.dropToken(player, col);
            player = (player + 1) % 2;
            isWon = game.isLastDropConnectFour();
         } catch (IndexOutOfBoundsException e) {
            System.out.println("\n *** Invalid column entry, try again ***");
            e.printStackTrace();
         } catch (IllegalStateException ise) {
            System.out.println("\n *** Token cannot be dropped either because the column is " + "full or the game is not in the READY or PLAYABLE phase");
            ise.printStackTrace();
         } // try                                                                                                                                                   
      } // while                                                                                                                                                     
      ConnectFourCLI.printGameOver();
      game.printGrid();
  
      if (game.isLastDropConnectFour()) {
         player = (player + 1) % 2;
         System.out.printf("\n\nCongratulations, player %s. You won!\n\n", player);
      } // if                                                                                                                                                        
   } // play                                                                                                                                                          
} // ConnectFourCLI        

