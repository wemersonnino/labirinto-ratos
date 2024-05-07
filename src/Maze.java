import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Maze {
    private final int width;
    private final int height;
    private final boolean[][] walls;
    private Position goalPosition;
    private List<Position> openPositions;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        this.walls = new boolean[width][height];
        this.openPositions = new ArrayList<>();
        generateMaze();
        placeCheese();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void generateMaze() {
//        System.out.println("Gerando o labirinto...");
        // Inicialize todas as células como paredes
        for (int x = 0; x < width; x++) {
            Arrays.fill(walls[x], true);
        }

        Random random = new Random();
        // Escolha um ponto de partida aleatório e remova a parede para essa célula
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        walls[startX][startY] = false;
        openPositions.add(new Position(startX, startY));

        // Lista para manter as paredes que podem ser removidas
        List<Wall> wallsList = new ArrayList<>();

        // Adiciona paredes iniciais ao redor da célula de início
        addWalls(startX, startY, wallsList);

        while (!wallsList.isEmpty()) {
            Wall wall = wallsList.remove(random.nextInt(wallsList.size()));
            Position opposite = wall.getOpposite();

            // Determina a célula oposta à parede
            if (opposite.getX() >= 0 && opposite.getX() < width &&
                    opposite.getY() >= 0 && opposite.getY() < height && walls[opposite.getX()][opposite.getY()]) {

                // Conecta a célula ao labirinto
                walls[wall.x][wall.y] = false;
                walls[opposite.getX()][opposite.getY()] = false;

                // Adiciona as paredes vizinhas da célula conectada ao fronteira
                addWalls(opposite.getX(), opposite.getY(), wallsList);

                // Adiciona posições livres para adicionar o queijo
                openPositions.add(new Position(opposite.getX(), opposite.getY()));
            }
        }
//        System.out.println("Labirinto gerado.");

    }

    private void addWalls(int x, int y, List<Wall> frontier) {
        // Certifique-se de que apenas as paredes que têm uma célula não visitada do outro lado são adicionadas
        if (x > 0) frontier.add(new Wall(x - 1, y, x - 2, y));
        if (y > 0) frontier.add(new Wall(x, y - 1, x, y - 2));
        if (x < width - 1) frontier.add(new Wall(x + 1, y, x + 2, y));
        if (y < height - 1) frontier.add(new Wall(x, y + 1, x, y + 2));
    }

    public void placeCheese() {
        System.out.println("Posicionando o queijo...");
        /*Random random = new Random();
        int cheeseX, cheeseY;
        do {
            cheeseX = random.nextInt(width);
            cheeseY = random.nextInt(height);
        } while (walls[cheeseX][cheeseY]); // Garante que o queijo não esteja numa parede
        goalPosition = new Position(cheeseX, cheeseY);*/
        /*System.out.println("Queijo posicionado na posição: " + goalPosition);*/
        if (!openPositions.isEmpty()) {
            Random random = new Random();
            goalPosition = openPositions.get(random.nextInt(openPositions.size())); // Escolhe uma posição aberta para o queijo
            System.out.println("Queijo posicionado na posição: " + goalPosition);
        }
    }

    public Position getGoalPosition() {
        return goalPosition;
    }


    public boolean isWall(Position position) {
        if (position.getX() < 0 || position.getX() >= width ||
                position.getY() < 0 || position.getY() >= height) {
            return false; // Ou considere retornar true, dependendo de como deseja tratar as bordas.
        }
        return walls[position.getX()][position.getY()];
    }

    private static class Wall {
        int x, y; // A posição da parede
        int oppositeX, oppositeY; // A posição do lado oposto da parede

        Wall(int x, int y, int oppositeX, int oppositeY) {
            this.x = x;
            this.y = y;
            this.oppositeX = oppositeX;
            this.oppositeY = oppositeY;
        }

        Position getOpposite() {
            return new Position(oppositeX, oppositeY);
        }
    }
}
