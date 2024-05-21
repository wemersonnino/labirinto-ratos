import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MazeGUI extends JFrame {
    private Maze maze;
    private List<Rat> rats;
    private static final int CELL_SIZE = 20;

    Random random = new Random();

    public MazeGUI(Maze maze, List<Rat> rats) {
        this.maze = maze;
        this.rats = new CopyOnWriteArrayList<>(rats);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Maze Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        MazePanel mazePanel = new MazePanel();
        add(mazePanel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetMaze());
        add(resetButton, BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(maze.getWidth() * CELL_SIZE + 20, maze.getHeight() * CELL_SIZE + 100));
        setVisible(true);
    }

    public void resetMaze() {
        rats.forEach(Thread::interrupt);
        rats.clear();

        maze.generateMaze();
        maze.placeCheese();

        for (int i = 0; i < 5; i++) {
            Position startPosition;
            do {
                int startX = random.nextInt(maze.getWidth());
                int startY = random.nextInt(maze.getHeight());
                startPosition = new Position(startX, startY);
            } while (maze.isWall(startPosition));
            Rat newRat = new Rat(maze, startPosition, this);
            rats.add(newRat);
            newRat.start();
        }

        SwingUtilities.invokeLater(this::repaint);
        repaint();
    }

    public void notifyRatHasWon(Rat rat) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("O rato encontrou o queijo na thread: " + Thread.currentThread().getName());
            for (Rat r : rats) {
                if (r != rat) {
                    r.interrupt();
                }
            }
            JOptionPane.showMessageDialog(this, "O rato " + rat + " encontrou o queijo!");
        });
    }

    private class MazePanel extends JPanel {
        public MazePanel() {
            setPreferredSize(new Dimension(maze.getWidth() * CELL_SIZE, maze.getHeight() * CELL_SIZE));
            setBackground(Color.GRAY);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("Iniciando paintComponent na thread: " + Thread.currentThread().getName());
            drawMaze(g);
            drawRats(g);
            System.out.println("Terminando paintComponent na thread: " + Thread.currentThread().getName());
        }

        private void drawMaze(Graphics g) {
            for (int x = 0; x < maze.getWidth(); x++) {
                for (int y = 0; y < maze.getHeight(); y++) {
                    boolean wall = maze.isWall(new Position(x, y));
                    if (wall) {
                        g.setColor(Color.BLACK);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
            if (maze.getGoalPosition() != null) {
                g.setColor(Color.YELLOW);
                Position cheesePos = maze.getGoalPosition();
                g.fillRect(cheesePos.getX() * CELL_SIZE, cheesePos.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        private void drawRats(Graphics g) {
            g.setColor(Color.RED);
            for (Rat rat : rats) {
                Position pos = rat.getPosition();
                g.fillOval(pos.getX() * CELL_SIZE, pos.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
