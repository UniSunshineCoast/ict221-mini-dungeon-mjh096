package dungeon.engine;

import javafx.scene.text.Text;
import java.util.Scanner;

public class GameEngine {

    private Cell[][] map;
    private Player player;
    private int level;
    private int difficulty;

    public void generateMap() {}
    public boolean checkWin() { return false; }
    public boolean checkLose() { return false; }

    public void saveGame() {}
    public void loadGame() {}

    /**
     * Creates a square game board.
     *
     * @param size the width and height.
     */
    public GameEngine(int size) {
        map = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new EmptyCell(i, j);
            }
        }
    }

    /**
     * The size of the current game.
     *
     * @return this is both the width and the height.
     */
    public int getSize() {
        return map.length;
    }

    /**
     * The map of the current game.
     *
     * @return the map, which is a 2d array.
     */
    public Cell[][] getMap() {
        return map;
    }

    public void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (player.getX() == i && player.getY() == j) {
                    System.out.print("P "); // Show player
                } else {
                    System.out.print(map[i][j].getIcon() + " ");
                }
            }
            System.out.println();
        }

        System.out.println("HP: " + player.getHP() + " | Score: " + player.getScore() + " | Steps Taken: " + player.getStepsTaken());
    }

    public void setPlayer(Player p) {
        this.player = p;
    }


    // These just call movePlayer() with correct directions
    public void moveUp() {map = player.moveUp(map); }
    public void moveDown() {map =  player.moveDown(map); }
    public void moveLeft() {map =  player.moveLeft(map); }
    public void moveRight() {map =  player.moveRight(map); }


    /**
     * Plays a text-based game
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameEngine engine = new GameEngine(10);
        engine.setPlayer(new Player("TestPlayer", 0, 0)); // Set player manually for now
        engine.generateMap(); // For now it can just fill with EmptyCells

        while (!engine.checkWin() && !engine.checkLose()) {
            engine.printMap();

            System.out.print("Enter move (u = up, d = down, l = left, r = right): ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "u": engine.moveUp(); break;
                case "d": engine.moveDown(); break;
                case "l": engine.moveLeft(); break;
                case "r": engine.moveRight(); break;
                default:
                    System.out.println("Invalid input. Please use u/d/l/r.");
            }

            System.out.println();
        }

        if (engine.checkWin()) {
            System.out.println("🎉 You escaped the dungeon!");
        } else {
            System.out.println("💀 Game over!");
        }
    }
}
