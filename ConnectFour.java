 package cs1302.game;

import cs1302.gameutil.GamePhase;
import cs1302.gameutil.Token;
import cs1302.gameutil.TokenGrid;

public class ConnectFour {
    private int rows;        // number of grid rows                                                                                                                
    private int cols;        // number of grid columns                                                                                                             
    private Token[][] grid;  // 2D array of tokens in the grid                                                                                                     
    private Token[] player;  // 1D array of player tokens (length 2)                                                                                               
    private int numDropped;  // number of tokens dropped so far                                                                                                    
    private int lastDropRow; // row index of the most recent drop                                                                                                  
    private int lastDropCol; // column index of the most recent drop                                                                                               
    private GamePhase phase;

    public ConnectFour(int rows, int cols)  {
        this.rows = rows;
        this.cols = cols;
        grid = new Token[rows][cols];
        player = new Token[2];
        numDropped = 0;
        lastDropRow = -1;
        lastDropCol = -1;
        phase = GamePhase.NEW;
        if ((rows < 6 || rows > 9) || (cols < 7 || cols > 9)) {
            throw new IllegalArgumentException("Illegal Argument");
        } // if  
    }
    public int getRows() {
        return rows;

    public int getCols() {
        return cols;

    public boolean isInBounds(int row, int col) {
        boolean inBounds = ((row < rows && row >= 0) && (col >= 0 &&  col < cols));
        return inBounds;
   
    public Token getTokenAt(int row, int col) {
        if (row > rows || row < 0 || col > cols || col < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } // if                                                                                                                                                   
            return grid[row][col];
    } // getTokenAt

    public void setPlayerTokens(Token token0, Token token1) {
        if (token0 == null || token1 == null) {
            throw new NullPointerException ("null error");
        } else if (token0 == token1) {
            throw new IllegalArgumentException ("Illegal args");
        } else if (getPhase() != GamePhase.NEW) {
            throw new IllegalStateException ("Illegal state");
        } else {
            phase = GamePhase.READY;
            player[0] = token0;
            player[1] = token1;
        } // else                                                                                                                                                 
    } // setPlayerTokens  

    public Token getPlayerToken(int player) {
        if (player != 0 && player != 1) {
            throw new IllegalArgumentException ("Illegal args");
        } else if (getPhase() == GamePhase.NEW) {
            throw new IllegalStateException ("Illegal state");
        } else {
            return this.player[player];
        } // else                                                                                                                                                 
    } // getPlayerToken  

    public int getNumDropped() {
        if (getPhase() != GamePhase.OVER && getPhase() != GamePhase.PLAYABLE) {
            throw new IllegalStateException ("Illegal state");
        } // if                                                                                                                                                   
  
        return numDropped;
    } // getNumDropped

    public int getLastDropRow() {
        if (getPhase() != GamePhase.OVER && getPhase() != GamePhase.PLAYABLE) {
            throw new IllegalStateException ("Illegal state");
        } // if                                                                                                                                                   
        return lastDropRow;
    } // getLastDropRow

    public int getLastDropCol() {
        if (getPhase() != GamePhase.OVER && getPhase() != GamePhase.PLAYABLE) {
            throw new IllegalStateException ("Illegal state");
        } // if                                                                                                                                                   
        return lastDropCol;
    } // getLastDropCol

    public GamePhase getPhase() {
        return phase;
    } // getPhase   

    public void dropToken(int player, int col) {
        if (col < 0 || col >= grid[0].length) {
            throw new IndexOutOfBoundsException("invalid column");
        }
        if (getPhase() != GamePhase.READY && getPhase() != GamePhase.PLAYABLE) {
            throw new IllegalStateException("incorrect phase");
        }
        if (player != 0 && player != 1) {
            throw new IllegalArgumentException("incorrect player");
        }
        if (grid[0][col] != null) {
            throw new IllegalStateException("incorrect place");
        } else {
            phase = GamePhase.PLAYABLE;
            for (int r = grid.length - 1; r >= 0; r--) {
                if (grid[r][col] == null) {
                    grid[r][col] = this.player[player];
                    numDropped += 1;
                    lastDropRow = r;
                    lastDropCol = col;
                    break;
                } // if                                                                                                                                           
            } // for                                                                                                                                              
        } // else                                                                                                                                                 
    } // dropToken 

    public boolean isLastDropConnectFour() {
        if (gridFull()) {
            phase = GamePhase.OVER;
        } // if                                                                                                                                                        
        int a = lastDropRow, b = lastDropCol, counter = 0;
        boolean winner = false;
        for (int c = b; c >= 0; c--) {
            if (grid[a][c] == grid[a][b]) {
                counter++;
            } else {
                break;
            } // else                                                                                                                                                  
         } // for                                                                                                                                                       
        for (int c = b + 1; c < cols; c++) {
            if (grid[a][c] == grid[a][b]) {
                counter++;
            } else {
                break;
            } // else                                                                                                                                                  
        } // for                                                                                                                                                       
        if (counter >= 4) {
            phase = GamePhase.OVER;
            winner = true;
        } // if                                                                                                                                                        
        counter = 0;
        for (int r = a; r >= 0; r--) {
            if (grid[r][b] == grid[a][b]) {
                counter++;
            } else {
                break;
            } // else                                                                                                                                                  
        } // for                                                                                                                                                       
        for (int r = a + 1; r < rows; r++) {
            if (grid[r][b] == grid[a][b]) {
                counter++;
            } else {
                break;
            } // else                                                                                                                                                  
        } // for                                                                                                                                                       
        if (counter >= 4) {
            phase = GamePhase.OVER;
            winner = true;
        } // if                                                                                                                                                        
        if (diagonal()) {
            winner = true;
        } // if                                                                                                                                                        
        if (diagonall()) {
            winner = true;
        } // if                                                                                                                                                        
        return winner;
    } // isLastDropConnectFour

    public boolean gridFull() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == null) {
                    return false;
                } // if                                                                                                                                                
            } // for                                                                                                                                                   
        } // for                                                                                                                                                       
        return true;
    } // gridFull   

    public boolean diagonal() {
        int row = lastDropRow, col = lastDropCol, counter = 0;
        boolean winner = false;
        while (row >= 0 && col < cols) {
            if (grid[row][col] == grid[lastDropRow][lastDropCol]) {
                counter++;
                if (counter == 4) {
                    phase = GamePhase.OVER;
                    winner = true;
                } // if                                                                                                                                                
            } else {
                counter = 0;
            } // else                                                                                                                                                  
            row--;
            col++;
        } // shilw                                                                                                                                                     
        row = lastDropRow;
        col = lastDropCol;
        counter = 0;
        while (row < rows && col >= 0) {
            if (grid[row][col] == grid[lastDropRow][lastDropCol]) {
                counter++;
                if (counter == 4) {
                    phase = GamePhase.OVER;
                    winner = true;
                } // if                                                                                                                                                
            } else {
                counter = 0;
            } // else                                                                                                                                                  
            row++;
            col--;
        } // while                                                                                                                                                     
        return winner;
    } // diagonal  

    public boolean diagonall() {
        boolean winner = false;
        int row = lastDropRow;
        int col = lastDropCol;
        int counter = 0;
        while (row >= 0 && col >= 0) {
            if (grid[row][col] == grid[lastDropRow][lastDropCol]) {
                counter++;
                if (counter == 4) {
                    phase = GamePhase.OVER;
                    winner =  true;
                } // if                                                                                                                                                
            } else {
                counter = 0;
            } // else                                                                                                                                                  
            row--;
            col--;
        } // while                                                                                                                                                     
        row = lastDropRow;
        col = lastDropCol;
        counter = 0;
        while (row < rows && col < cols) {
            if (grid[row][col] == grid[lastDropRow][lastDropCol]) {
                counter++;
                if (counter == 4) {
                    phase = GamePhase.OVER;
                    winner = true;
                } // if                                                                                                                                                
            } else {
                counter = 0;
            } // else                                                                                                                                                  
            row++;
            col++;
        } // while                                                                                                                                                     
        return winner;
    } // diagonall

    public void printGrid() {
        TokenGrid.println(this.grid);
    } // printGrid  
} //ConectFour