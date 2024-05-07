import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        int numberOfRats = args.length > 0 ? Integer.parseInt(args[0]) : 5;  // Pega o número de ratos da linha de comando ou usa 5 como padrão

        SwingUtilities.invokeLater(() -> {
            // Crie o labirinto e a lista de ratos.
            Maze maze = new Maze(50, 30);
            List<Rat> rats = new ArrayList<>();

            // Cria a GUI. O construtor deve configurar o layout e os componentes,
            // mas não deve tornar a janela visível ainda.
            MazeGUI gui = new MazeGUI(maze, rats);

            // Cria uma instância de Random para as posições iniciais dos ratos.
            Random random = new Random();

            // Crie e inicie várias instâncias de Rat.
            for (int i = 0; i < numberOfRats; i++) {
                Position startPosition;
                do {
                    int startX = random.nextInt(maze.getWidth());
                    int startY = random.nextInt(maze.getHeight());
                    startPosition = new Position(startX, startY);
                } while (maze.isWall(startPosition)); // Verifica se a posição não é uma parede.
                Rat rat = new Rat(maze, startPosition, gui);
                rats.add(rat);
                rat.start();
            }

            // Torna a GUI visível agora que tudo está inicializado.
            gui.setVisible(true);
        });
    }
}