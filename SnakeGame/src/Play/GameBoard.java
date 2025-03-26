package Play;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JPanel implements ActionListener, KeyListener {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int DOT_SIZE = 25;
    private final int ALL_DOTS = (WIDTH * HEIGHT) / (DOT_SIZE * DOT_SIZE);
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private ArrayList<Point> snake;
    private Point food;
    private boolean left = false, right = true, up = false, down = false;
    private boolean inGame = true;
    private Timer timer;

    public GameBoard() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);

        addKeyListener(this);
        snake = new ArrayList<>();
        snake.add(new Point(100, 100)); // Initial snake position
        spawnFood();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void spawnFood() {
        Random rand = new Random();
        food = new Point(rand.nextInt(RAND_POS) * DOT_SIZE, rand.nextInt(RAND_POS) * DOT_SIZE);
    }

    private void move() {
        if (!inGame) return;

        Point head = snake.get(0);
        Point newHead = null;

        if (left) {
            newHead = new Point(head.x - DOT_SIZE, head.y);
        } else if (right) {
            newHead = new Point(head.x + DOT_SIZE, head.y);
        } else if (up) {
            newHead = new Point(head.x, head.y - DOT_SIZE);
        } else if (down) {
            newHead = new Point(head.x, head.y + DOT_SIZE);
        }

        if (newHead.equals(food)) {
            snake.add(0, newHead); // Add new head without removing tail
            spawnFood();
        } else {
            snake.add(0, newHead);
            snake.remove(snake.size() - 1); // Remove tail
        }

        checkCollisions();
    }

    private void checkCollisions() {
        Point head = snake.get(0);

        // Check for collision with walls
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            inGame = false;
        }

        // Check for collision with itself
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                inGame = false;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            // Draw the food
            g.setColor(Color.red);
            g.fillRect(food.x, food.y, DOT_SIZE, DOT_SIZE);

            // Draw the snake
            g.setColor(Color.green);
            for (Point point : snake) {
                g.fillRect(point.x, point.y, DOT_SIZE, DOT_SIZE);
            }

            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font font = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics metr = getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (WIDTH - metr.stringWidth(msg)) / 2, HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            move();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && !right) {
            left = true;
            up = down = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && !left) {
            right = true;
            up = down = false;
        }

        if ((key == KeyEvent.VK_UP) && !down) {
            up = true;
            left = right = false;
        }

        if ((key == KeyEvent.VK_DOWN) && !up) {
            down = true;
            left = right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
