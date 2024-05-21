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
        for (int x = 0; x < width; x++) {
            Arrays.fill(walls[x], true);
        }

        Random random = new Random();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        walls[startX][startY] = false;
        openPositions.add(new Position(startX, startY));

        List<Wall> wallsList = new ArrayList<>();
        addWalls(startX, startY, wallsList);

        while (!wallsList.isEmpty()) {
            Wall wall = wallsList.remove(random.nextInt(wallsList.size()));
            Position opposite = wall.getOpposite();

            if (opposite.getX() >= 0 && opposite.getX() < width && opposite.getY() >= 0 && opposite.getY() < height && walls[opposite.getX()][opposite.getY()]) {
                walls[wall.x][wall.y] = false;
                walls[opposite.getX()][opposite.getY()] = false;
                addWalls(opposite.getX(), opposite.getY(), wallsList);
                openPositions.add(new Position(opposite.getX(), opposite.getY()));
            }
        }
    }

    private void addWalls(int x, int y, List<Wall> frontier) {
        if (x > 0) frontier.add(new Wall(x - 1, y, x - 2, y));
        if (y > 0) frontier.add(new Wall(x, y - 1, x, y - 2));
        if (x < width - 1) frontier.add(new Wall(x + 1, y, x + 2, y));
        if (y < height - 1) frontier.add(new Wall(x, y + 1, x, y + 2));
    }

    public void placeCheese() {
        goalPosition = openPositions.get(new Random().nextInt(openPositions.size()));
        System.out.println("Queijo posicionado na posição: " + goalPosition);
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public boolean isWall(Position position) {
        if (position.getX() < 0 || position.getX() >= width || position.getY() < 0 || position.getY() >= height) {
            return false;
        }
        return walls[position.getX()][position.getY()];
    }

    private static class Wall {
        int x, y;
        int oppositeX, oppositeY;

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
