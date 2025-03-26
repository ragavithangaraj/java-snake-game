package Play;

import javax.swing.*;

public class SnakeGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        GameBoard gameBoard = new GameBoard();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameBoard);
        frame.pack();
        frame.setVisible(true);
    }
}
