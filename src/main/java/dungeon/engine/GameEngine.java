package dungeon.engine;

import javafx.scene.text.Text;

import java.util.Random;
import java.util.Scanner;

/**
 * The GameEngine class manages the core logic of the MiniDungeon.
 * It is responsible for initialising the map and handling player movement,
 * processing interactions between the player and cells, and tracking game progress.
 *
 * This class operates the game loop in the text-based version.
 */
public class GameEngine {

    private Cell[][] map;
    private Player player;
    /**
     * Current Level of the game.
     */
    private int level;
    /**
     * Current difficulty of the game
     */
    private int difficulty;

    /**
     * Amount of Steps Player can take in a single game.
     */
    private static final int maxStepsTaken = 100;

    public void saveGame() {}
    public void loadGame() {}

    /**
     * Constructs a new GameEngine with a square map of the specified size.
     *
     * @param size the width and height of the square map.
     */
    public GameEngine(int size) {
        map = new Cell[size][size];
        this.difficulty = 1;
        this.level = 1;

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

    /**
     * Prints a text based map of the game
     */
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
        System.out.println("Player: " + player.getName() + " | Level: " + level + " | Difficulty: " + difficulty);
        System.out.println("HP: " + player.getHP() + " | Score: " + player.getScore() + " | Steps Taken: " + player.getStepsTaken() + "/" + maxStepsTaken);
    }

    /**
     * Creates the player object used by this game engine.
     *
     * @param p the player to associate with the game
     */
    public void setPlayer(Player p) {
        this.player = p;
    }

    /**
     * Method to move player in the correct direction on the map (Up, Down, Left, Right).
     */
    public void moveUp() {map = player.moveUp(map); }
    public void moveDown() {map =  player.moveDown(map); }
    public void moveLeft() {map =  player.moveLeft(map); }
    public void moveRight() {map =  player.moveRight(map); }

    /**
     * Sets the game difficulty level.
     * Must be called before generateMap().
     *
     * @param difficulty the difficulty level (1 = easy, 2 = medium, 3 = hard)
     */
    public void setDifficulty(int difficulty) {
        if (difficulty < 1 || difficulty > 3) {
            System.out.println("Invalid difficulty. Setting to 1 (Easy) by default.");
            this.difficulty = 1;
        } else {
            this.difficulty = difficulty;
        }
    }

    /**
     * Adds non-player items to the map (gold, wall, traps etc.)
     * Items scale based on set difficulty.
     */
    public void generateMap() {
        Random rand = new Random();
        int size = map.length;

        int cellTrap = 2 + difficulty;
        int cellGold = 2 + difficulty;
        int cellWall = 5 + difficulty * 3;
        int cellMeleeMutants = 1 + (difficulty > 1 ? 1 : 0);
        int cellRangedMutants = 1 + (difficulty == 3 ? 1 : 0);
        int cellPotions = 1;

        // Populate the map with EmptyCells to start with
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new EmptyCell(i, j);
            }
        }

        // Scatter unique cells around the map (Gold, Traps etc.)
        for (int i = 0; i < cellWall; i++) {placeRandom(new WallCell(0, 0), rand);}
        for (int i = 0; i < cellGold; i++) {placeRandom(new GoldCell(0, 0), rand);}
        for (int i = 0; i < cellTrap; i++) {placeRandom(new TrapCell(0, 0), rand);}
        for (int i = 0; i < cellPotions; i++) {placeRandom(new HealthPotionCell(0, 0), rand);}
        for (int i = 0; i < cellMeleeMutants; i++) {placeRandom(new MeleeMutantCell(0, 0), rand);}
        for (int i = 0; i < cellRangedMutants; i++) {placeRandom(new RangedMutantCell(0, 0), rand);}

        placeRandom(new LadderCell(0, 0), rand);
    }

    /**
     * Places a cell at a random unoccupied (non-wall, non-player) location.
     *
     * @param cell Specific cell to
     * @param rand java.util.Random Object.
     */
    private void placeRandom(Cell cell, Random rand) {
        int size = map.length;
        int x, y;

        do {
            x = rand.nextInt(size);
            y = rand.nextInt(size);
        } while (
                (x == 0 && y == 0) || map[x][y] instanceof WallCell || !(map[x][y] instanceof EmptyCell)
        );

        cell.x = x;
        cell.y = y;
        map[x][y] = cell;
    }

    /**
     * Checks to determine if player is on a ladder,
     * this indicates either player has won or proceeds to next level.
     *
     * @return true if player is on top of a ladder
     */
    public boolean checkWin() {
        int x = player.getX();
        int y = player.getY();
        return map[x][y] instanceof LadderCell;
    }

    /**
     * Checks whether the player has lost the game.
     * A player loses if their HP reaches Zero or they run out of steps.
     *
     * @return true if the player has lost
     */
    public boolean checkLose() {
        return player.getHP() <= 0 || player.getStepsTaken() >= maxStepsTaken;
    }

    /**
     * Plays a text-based version of the game
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameEngine engine = new GameEngine(10);
        System.out.print("Enter Player Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter difficulty (1 = Easy, 2 = Medium, 3 = Hard): ");
        int difficulty = Integer.parseInt(scanner.nextLine());
        engine.setDifficulty(difficulty); // Sets difficulty of the game.
        engine.setPlayer(new Player(name, 0, 0)); // Set player manually for now
        engine.generateMap(); // Fills map with unique cells

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
            System.out.println("You escaped the dungeon!");
        } else if (engine.checkLose()) {
            System.out.println("You lost the game.");
        } else {
            System.out.println("💀 Game over!");
        }
    }
}
