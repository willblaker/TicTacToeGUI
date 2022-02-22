import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame{
    JPanel mainPnl, boardPnl, quitPnl;
    TicTacToeTile[][] guiTiles = new TicTacToeTile[3][3];
    JButton quitBtn;

    private static String[][] board = new String[3][3];
    private String player = "X";
    private int moveCnt = 0;
    private final int MOVES_FOR_WIN = 5;
    private final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame()
    {
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        clearBoard();
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        boardPnl = new JPanel();
        boardPnl.setLayout(new GridLayout(3,3));

        for( int row = 0; row < 3; row++)
            for(int col= 0; col < 3; col++)
            {
                guiTiles[row][col] = new TicTacToeTile(row, col);
                guiTiles[row][col].setText(" ");
                int finalCol = col;
                int finalRow = row;
                guiTiles[row][col].addActionListener((ActionEvent ae) ->
                {
                    if(isValidMove(finalRow,finalCol)){
                        board[finalRow][finalCol] = player;
                        guiTiles[finalRow][finalCol].setText(player);

                        moveCnt++;
                        if(moveCnt >= MOVES_FOR_WIN) {
                            if(isWin(player))
                            {
                                if(player.equals("X")){
                                    JOptionPane.showMessageDialog(null, "Player 1 Wins!");
                                    int input = JOptionPane.showConfirmDialog(null, "Would you like to play again?");
                                    if(input == 0){
                                        clearBoard();
                                        player = "O";
                                        moveCnt = 0;
                                        for(int row2=0; row2 < 3; row2++)
                                        {
                                            for(int col2=0; col2 < 3; col2++)
                                            {
                                                guiTiles[row2][col2].setText(" ");
                                            }
                                        }
                                    }
                                    else if(input == 1 || input == 2)
                                        System.exit(0);
                                }
                                else if(player.equals("O")){
                                    JOptionPane.showMessageDialog(null, "Player 2 Wins!");
                                    int input = JOptionPane.showConfirmDialog(null, "Would you like to play again?");
                                    if(input == 0){
                                        clearBoard();
                                        player = "O";
                                        moveCnt = 0;
                                        for(int row2=0; row2 < 3; row2++)
                                        {
                                            for(int col2=0; col2 < 3; col2++)
                                            {
                                                guiTiles[row2][col2].setText(" ");
                                            }
                                        }
                                    }
                                    else if(input == 1 || input == 2)
                                        System.exit(0);
                                }
                            }
                        }
                        if(moveCnt >= MOVES_FOR_TIE) {
                            if(isTie())
                            {
                                JOptionPane.showMessageDialog(null, "Its a tie!");
                                int input = JOptionPane.showConfirmDialog(null, "Would you like to play again?");
                                if(input == 0){
                                    clearBoard();
                                    player = "O";
                                    moveCnt = 0;
                                    for(int row2=0; row2 < 3; row2++)
                                    {
                                        for(int col2=0; col2 < 3; col2++)
                                        {
                                            guiTiles[row2][col2].setText(" ");
                                        }
                                    }
                                }
                                else if(input == 1 || input == 2)
                                    System.exit(0);
                            }
                        }

                        if(player.equals("X")) {
                            player = "O";
                        }
                        else {
                            player = "X";
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Invalid Move!, Please try again.");
                });
                boardPnl.add(guiTiles[row][col]);
            }

        mainPnl.add(boardPnl, BorderLayout.CENTER);
        add(mainPnl);

        quitPnl = new JPanel();
        quitBtn = new JButton("Quit the TTT Game!");
        quitBtn.addActionListener((ActionEvent ae) ->
        {
            System.exit(0);
        });

        quitPnl.add(quitBtn);
        mainPnl.add(quitPnl, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void clearBoard() {
        for(int row=0; row < 3; row++)
        {
            for(int col=0; col < 3; col++)
            {
                board[row][col] = " ";
            }
        }
    }

    private boolean isWin(String player) {
        if(isColWin(player) || isRowWin(player) || isDiagnalWin(player)) {
            return true;
        }
        return false;
    }

    private boolean isDiagnalWin(String player) {
        if(board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player) ) {
            return true;
        }
        if(board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player) ) {
            return true;
        }
        return false;
    }

    private boolean isRowWin(String player) {
        for(int row=0; row < 3; row++)
        {
            if(board[row][0].equals(player) &&
                    board[row][1].equals(player) &&
                    board[row][2].equals(player))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isColWin(String player) {
        for(int col=0; col < 3; col++)
        {
            if(board[0][col].equals(player) &&
                    board[1][col].equals(player) &&
                    board[2][col].equals(player))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidMove(int row, int col)
    {
        boolean retVal = false;
        if(board[row][col].equals(" "))
            retVal = true;

        return retVal;
    }

    private static boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;

        for(int row=0; row < 3; row++)
        {
            if(board[row][0].equals("X") ||
                    board[row][1].equals("X") ||
                    board[row][2].equals("X")) {
                xFlag = true; // there is an X in this row
            }
            if(board[row][0].equals("O") ||
                    board[row][1].equals("O") ||
                    board[row][2].equals("O")) {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) ) {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < 3; col++)
        {
            if(board[0][col].equals("X") ||
                    board[1][col].equals("X") ||
                    board[2][col].equals("X")) {
                xFlag = true; // there is an X in this col
            }
            if(board[0][col].equals("O") ||
                    board[1][col].equals("O") ||
                    board[2][col].equals("O")) {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) ) {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(board[0][0].equals("X") ||
                board[1][1].equals("X") ||
                board[2][2].equals("X") ) {
            xFlag = true;
        }
        if(board[0][0].equals("O") ||
                board[1][1].equals("O") ||
                board[2][2].equals("O") ) {
            oFlag = true;
        }
        if(! (xFlag && oFlag) ) {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(board[0][2].equals("X") ||
                board[1][1].equals("X") ||
                board[2][0].equals("X") ) {
            xFlag =  true;
        }
        if(board[0][2].equals("O") ||
                board[1][1].equals("O") ||
                board[2][0].equals("O") ) {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) ) {
            return false; // No tie can still have a diag win
        }

        return true;
    }
}
