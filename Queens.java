package Classwork;

import java.util.Arrays;
import java.awt.*;
import javax.swing.*;
import javax.accessibility.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
/**
 * Write a description of class Queens here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Queens
{
    private static int[][] board;    
    private static JLayeredPane layeredPane;
    private static JLabel chessBoard;
    private static Image boardImage = null;
    private static JTextArea textArea;
    private static int boardSize = -1;
    private static int speed = -1;
    //slow = 0, fast = 1, ludicrous = 2
    public static void main(String[] args) {
        runSelectionPhase();
        
        // try {
        // URL url = new URL("https://raw.githubusercontent.com/rsymons22/Projects/master/hydra.png");
        // boardImage = ImageIO.read(url);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        // ImageIcon chessBoardIcon = new ImageIcon(boardImage);

        // layeredPane = new JLayeredPane();
        // layeredPane.setPreferredSize(new Dimension(480, 570));

        // chessBoard = new JLabel(chessBoardIcon);

        // chessBoard.setBounds(0, 0, chessBoardIcon.getIconWidth(), chessBoardIcon.getIconHeight());

        // layeredPane.add(chessBoard, new Integer(0), 0); // integer(x), x = index of what goes on top, higher = on top

        //panel.add(layeredPane);

        
        //runGame(4);
    }

    public static void runGame(int n) {
        board = new int[n][n];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }
        placeQueens(0, 0);
        //printBoard();
    }

    public static void printBoard() {
        for(int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
        String boardCode = "";
    }

    public static boolean isSafe(int row, int column) {
        int length = board[0].length;

        // Check Horizontal
        for(int i = 0; i < length; i++) {
            //System.out.println(row + ", " + i);
            if(board[row][i] == 1) {
                return false;
            }
        }

        // Check Vertical
        for(int i = 0; i < length; i++) {
            //System.out.println(i + ", " + column);
            if(board[i][column] == 1) {
                return false;
            }
        }

        // Check Diagonals
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                // If two spaces are on the same diagonal, the difference in coordinates will be the same.
                if(Math.abs(j - column) == Math.abs(i - row)) {
                    if(board[i][j] == 1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static int nullifyColumn(int column) {
        int rowOfPreviousQueen = -1;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(j == column) {
                    if(board[i][j] == 1) {
                        rowOfPreviousQueen = i;
                    }
                    board[i][j] = 0;
                }
            }
        }
        return rowOfPreviousQueen;
    }

    public static void placeQueens(int row, int column) {
        if(isSafe(row, column)) {
            //System.out.println("Placed queen at: " + row + ", " + column);
            board[row][column] = 1;
            if(column != board[0].length - 1) { // If we aren't on the last column, then move to the next one
                //System.out.println("placeQueens(" + 0 + ", " + (column + 1) + ")");
                placeQueens(0, column + 1);
            } else {
                printBoard();
                board[row][column] = 0;
                moveToNextRow(row, column); // Move down or back even though a solution was found, this is needed to keep finding solutions.
            }
        } else {
            moveToNextRow(row, column);
        }
    }

    public static void moveToNextRow(int row, int column) {
        while(row == board[0].length - 1) { // If we are on the last row, that means a mistake was made previously.
            if(column == 0) { // If we are on the last row of the first column, that means we have tried every solution and it is over.
                return;
            }
            row = nullifyColumn(column - 1);
            column --;
        }
        //System.out.println("placeQueens(" + (row + 1) + ", " + column + ")");
        placeQueens(row + 1, column); // Move down to the next row.
    }
    
    public static void runSelectionPhase() {
        JFrame frame = new JFrame("Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JCheckBox board4x4CB = new JCheckBox("4 x 4");
        JCheckBox board5x5CB = new JCheckBox("5 x 5");
        JCheckBox board6x6CB = new JCheckBox("6 x 6");
        JCheckBox board7x7CB = new JCheckBox("7 x 7");
        JCheckBox board8x8CB = new JCheckBox("8 x 8");

        JCheckBox slowSpeedCB = new JCheckBox("Slow Speed");
        JCheckBox fastSpeedCB = new JCheckBox("Fast Speed");
        JCheckBox ludicrousSpeedCB = new JCheckBox("Ludicrous Speed!");

        JPanel panel = new JPanel();
        panel.setOpaque(true); //content panes must be opaque

        panel.add(board4x4CB);
        panel.add(board5x5CB);
        panel.add(board6x6CB);
        panel.add(board7x7CB);
        panel.add(board8x8CB);

        panel.add(slowSpeedCB);
        panel.add(fastSpeedCB);
        panel.add(ludicrousSpeedCB);

        board4x4CB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    board5x5CB.setEnabled(false);
                    board6x6CB.setEnabled(false);
                    board7x7CB.setEnabled(false);
                    board8x8CB.setEnabled(false);
                    boardSize = 4;
                } else {//checkbox has been deselected
                    board5x5CB.setEnabled(true);
                    board6x6CB.setEnabled(true);
                    board7x7CB.setEnabled(true);
                    board8x8CB.setEnabled(true);
                    boardSize = -1;
                }
            }
        });
        
        board5x5CB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    board4x4CB.setEnabled(false);
                    board6x6CB.setEnabled(false);
                    board7x7CB.setEnabled(false);
                    board8x8CB.setEnabled(false);
                    boardSize = 5;
                } else {//checkbox has been deselected
                    board4x4CB.setEnabled(true);
                    board6x6CB.setEnabled(true);
                    board7x7CB.setEnabled(true);
                    board8x8CB.setEnabled(true);
                    boardSize = -1;
                }
            }
        });
        
        board6x6CB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    board5x5CB.setEnabled(false);
                    board4x4CB.setEnabled(false);
                    board7x7CB.setEnabled(false);
                    board8x8CB.setEnabled(false);
                    boardSize = 6;
                } else {//checkbox has been deselected
                    board5x5CB.setEnabled(true);
                    board4x4CB.setEnabled(true);
                    board7x7CB.setEnabled(true);
                    board8x8CB.setEnabled(true);
                    boardSize = -1;
                }
            }
        });
        
        board7x7CB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    board5x5CB.setEnabled(false);
                    board6x6CB.setEnabled(false);
                    board4x4CB.setEnabled(false);
                    board8x8CB.setEnabled(false);
                    boardSize = 7;
                } else {//checkbox has been deselected
                    board5x5CB.setEnabled(true);
                    board6x6CB.setEnabled(true);
                    board4x4CB.setEnabled(true);
                    board8x8CB.setEnabled(true);
                    boardSize = -1;
                }
            }
        });
        
        board8x8CB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    board5x5CB.setEnabled(false);
                    board6x6CB.setEnabled(false);
                    board7x7CB.setEnabled(false);
                    board4x4CB.setEnabled(false);
                    boardSize = 8;
                } else {//checkbox has been deselected
                    board5x5CB.setEnabled(true);
                    board6x6CB.setEnabled(true);
                    board7x7CB.setEnabled(true);
                    board4x4CB.setEnabled(true);
                    boardSize = -1;
                }
            }
        });
        
        slowSpeedCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    fastSpeedCB.setEnabled(false);
                    ludicrousSpeedCB.setEnabled(false);
                    speed = 0;
                } else {//checkbox has been deselected
                    fastSpeedCB.setEnabled(true);
                    ludicrousSpeedCB.setEnabled(true);
                    speed = -1;
                }
            }
        });
        
        fastSpeedCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    slowSpeedCB.setEnabled(false);
                    ludicrousSpeedCB.setEnabled(false);
                    speed = 1;
                } else {//checkbox has been deselected
                    slowSpeedCB.setEnabled(true);
                    ludicrousSpeedCB.setEnabled(true);
                    speed = -1;
                }
            }
        });
        
        ludicrousSpeedCB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    fastSpeedCB.setEnabled(false);
                    slowSpeedCB.setEnabled(false);
                    speed = 2;
                } else {//checkbox has been deselected
                    fastSpeedCB.setEnabled(true);
                    slowSpeedCB.setEnabled(true);
                    speed = -1;
                }
            }
        });

        textArea = new JTextArea(); 
        textArea.setEditable(false);
        textArea.append("Select one board size and one speed.");

        panel.add(textArea);
        frame.setContentPane(panel);

        frame.pack();
        frame.setResizable(false);
        frame.setSize(250,150); // 480, 515
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        while(boardSize == -1 || speed == -1) { /* Wait for user to make selection */ }
        
        System.out.println("size: " + boardSize + ", speed: " + speed);
    }
}
