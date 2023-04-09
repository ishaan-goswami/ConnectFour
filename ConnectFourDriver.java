package cs1302.game;

import java.util.Scanner;
import cs1302.gameutil.Token;
 
public class ConnectFourDriver {
  public static final Scanner INPUT_SCANNER = new Scanner(System.in);
  public static String getInputLine() {
    return INPUT_SCANNER.nextLine().trim();
  } // getInputLine                                                                                                                                                   
  public static void main(String[] args) {                                                                                                                                                             
    ConnectFour game = new ConnectFour(6, 7);
    game.setPlayerTokens(Token.RED, Token.BLUE);
    game.dropToken(0, 0); // first player, column 0     
    game.dropToken(1, 1); // second player, column 1                                                                                                               
    game.dropToken(0, 1); // first player, column 1                                                                                                                
    game.dropToken(1, 2); // second player, column 2                                                                                                                        game.dropToken(0, 2);
    game.dropToken(1, 3);
    game.dropToken(0, 3);
    game.dropToken(1, 4); // second player, column 4                                                                                                               
    if (game.isLastDropConnectFour()) {
      System.out.println("second player has won!");
    } // if                                                                                                                                                        
    throw new UnsupportedOperationException("ConnectFourDriver.main: not yet implemented");
  } // main                                                                                                                                                          

} // ConnectFourDriver                                                                                                                                                 
     
    