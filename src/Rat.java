import javax.swing.*;
import java.util.*;

public class Rat extends Thread {
    private Position position;
    private final Maze maze;
    private final MazeGUI gui;
    private Random random = new Random();

    public Rat(Maze maze, Position startPosition, MazeGUI gui) {
        this.maze = maze;
        this.position = startPosition;
        this.gui = gui;
    }

    public synchronized Position getPosition() {
        return position;
    }

    public synchronized void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void run() {
        System.out.println("Rato começando na thread: " + Thread.currentThread().getName());
        bfsMove();
        System.out.println("Rato terminando na thread: " + Thread.currentThread().getName());
    }

    private void bfsMove() {
        Queue<Position> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();  // Conjunto para rastrear posições visitadas
        queue.offer(position);  // Inicia a fila com a posição inicial do rato
        visited.add(position);  // Marca a posição inicial como visitada

        while (!queue.isEmpty() && !Thread.currentThread().isInterrupted()) {
            Position current = queue.poll();
            System.out.println("Thread " + Thread.currentThread().getName() + " visitando: " + current);
            setPosition(current); // Atualiza a posição do rato no labirinto

            // Verifica se o rato alcançou o objetivo
            if (current.equals(maze.getGoalPosition())) {
                gui.notifyRatHasWon(this);
                break;
            }

            // Itera sobre todas as posições possíveis a partir da posição atual
            for (Position next : getPossibleMoves(current)) {
                if (!visited.contains(next) && !maze.isWall(next)) {  // Checa se não é parede e se não foi visitado
                    visited.add(next);  // Marca como visitado
                    queue.offer(next);  // Adiciona à fila para futura exploração
                }
            }

            SwingUtilities.invokeLater(gui::repaint); // Solicita a repintura da GUI
            try {
                Thread.sleep(500); // Pausa para tornar os movimentos visíveis
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + " interrompida.");
                break; // Interrompe se a thread for interrompida
            }
        }
    }


    private List<Position> getPossibleMoves(Position pos) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Direções: cima, direita, baixo, esquerda

        List<int[]> shuffledDirections = Arrays.asList(directions);
        Collections.shuffle(shuffledDirections, random);  // Embaralha as direções

        for (int[] d : shuffledDirections) {
            int nx = pos.getX() + d[0];
            int ny = pos.getY() + d[1];
            if (isValidMove(nx, ny)) {
                moves.add(new Position(nx, ny));
            }
        }

        return moves;
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < maze.getWidth() && y >= 0 && y < maze.getHeight() && !maze.isWall(new Position(x, y));
    }
}
