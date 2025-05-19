package dungeon.engine;

import javafx.scene.text.Text;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The GameEngine class manages the core logic of the MiniDungeon.
 * It is responsible for initialising the map and handling player movement,
 * processing interactions between the player and cells, and tracking game progress.
 *
 * This class operates the game loop in the text-based version.
 */
public class GameEngine implements Serializable {

    /**
     * Unique ID for serialization compatibility.
     * Used to ensure that object matches the version of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * HashMap of levels generated in the game presently.
     */
    private Map<Integer, Cell[][]> levelMaps = new HashMap<>();

    /**
     * Current level player is on.
     */
    private int currentLevel = 1;

    private Player player;

    /**
     * If the player has met all conditions to win the game.
     */
    private boolean playerHasWon = false;
    /**
     * Current difficulty of the game
     */
    private int difficulty;

    /**
     * Amount of Steps Player can take in a single game.
     */
    private static final int maxStepsTaken = 100;

    /**
     * List of High Scores.
     */
    private List<HighScores> highScores = new ArrayList<>();

    /**
     * Loads the existing High Scores file.
     */
    public void loadHighScores() {highScores = HighScores.load("highscores.dat");}

    /**
     * Saves High Scores to file for persistence.
     */
    public void saveHighScores() {HighScores.save(highScores, "highscores.dat");}

    public int getCurrentLevel() {return currentLevel;}

    /**
     * Adds the current players score to the high score list and returns the top 5.
     *
     * @return the top 5 high scores
     */
    public List<HighScores> updateHighScores() {
        loadHighScores();
        HighScores newEntry = new HighScores(
                player.getName(),
                player.getScore(),
                LocalDate.now()
        );

        highScores.add(newEntry);
        highScores.sort(Comparator.comparingInt(HighScores::getScore).reversed());

        if (highScores.size() > 5) {
            highScores = new ArrayList<>(highScores.subList(0, 5));
        }

        saveHighScores(); // Save High Scores
        return new ArrayList<>(highScores);
    }

    /**
     * Formats HighScores into a numbered list
     *
     * @return formated highscores list.
     */
    public List<String> formatHighScores() {
        List<String> formattedHighScores = new ArrayList<>();
        int rank = 1;
        for (HighScores entry : highScores) {
            formattedHighScores.add(String.format("#%d: %-12s - %-3d (%s)",
                    rank++,
                    entry.getPlayerName(),
                    entry.getScore(),
                    entry.getDate()));
        }
        return formattedHighScores;
    }

    /**
     * Saves the current game to be resumed later.
     *
     * @param filename name of file to save game to
     */
    public void saveGame(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    /**
     * Loads a Saved game to play.
     *
     * @param filename name of file to load game
     * @return Game Engine for MiniDungeon
     */
    public static GameEngine loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            GameEngine engine = (GameEngine) in.readObject();
            engine.getPlayer().setGameEngine(engine); // re-link
            System.out.println("Game loaded successfully.");
            return engine;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    /**
     * Constructs a new GameEngine with a square map of the specified size.
     *
     * @param size the width and height of the square map.
     */
    public GameEngine(int size) {
        this.levelMaps = new HashMap<>();
        this.difficulty = 1;

        Cell[][] map = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new EmptyCell(i, j);
            }
        }
        levelMaps.put(1, map);
    }

    /**
     * The size of the current game.
     *
     * @return this is both the width and the height.
     */
    public int getSize() {
        return getMap().length;
    }

    /**
     * The map of the current game.
     *
     * @return the map, which is a 2d array.
     */
    public Cell[][] getMap() {return levelMaps.get(currentLevel);}

    /**
     * Progresses player to new gevel and generates map if needed
     *
     * @param newLevel level number that the player will progress to
     */
    public void switchLevel(int newLevel) {
        if (!levelMaps.containsKey(newLevel)) {
            // Create empty map first
            int size = 10;
            Cell[][] map = new Cell[size][size];
            levelMaps.put(newLevel, map);

            currentLevel = newLevel;
            generateMap(); // Generates level
        } else {
            currentLevel = newLevel;
        }

        // Reset player position or spawn point
        player.setX(0);
        player.setY(0);

        System.out.println("You moved to level " + newLevel);
    }

    /**
     * Prints a text based map of the game
     */
    public void printMap() {
        for (int i = 0; i < getMap().length; i++) {
            for (int j = 0; j < getMap()[i].length; j++) {
                if (player.getX() == i && player.getY() == j) {
                    System.out.print("P "); // Show player
                } else {
                    System.out.print(getMap()[i][j].getIcon() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("Player: " + player.getName() + " | Level: " + getCurrentLevel() + " | Difficulty: " + difficulty);
        System.out.println("HP: " + player.getHP() + " | Score: " + player.getScore() + " | Steps Taken: " + player.getStepsTaken() + "/" + maxStepsTaken);
    }

    /**
     * Creates the player object used by this game engine.
     *
     * @param p the player to associate with the game
     */
    public void setPlayer(Player p) {
        this.player = p;
        p.setGameEngine(this);
    }

    public Player getPlayer() {
        return this.player;
    }

    /**
     * Method to move player in the correct direction on the map (Up, Down, Left, Right).
     */
    public void moveUp() {levelMaps.put(currentLevel, player.moveUp(getMap()));}
    public void moveDown() {levelMaps.put(currentLevel, player.moveDown(getMap()));}
    public void moveLeft() {levelMaps.put(currentLevel, player.moveLeft(getMap()));}
    public void moveRight() {levelMaps.put(currentLevel, player.moveRight(getMap()));}

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
        int size = getMap().length;

        int cellTrap = 2 + difficulty;
        int cellGold = 2 + difficulty;
        int cellWall = 5 + difficulty * 3;
        int cellMeleeMutants = 1 + (difficulty > 1 ? 1 : 0);
        int cellRangedMutants = 1 + (difficulty == 3 ? 1 : 0);
        int cellPotions = 1;

        // Populate the map with EmptyCells to start with
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                getMap()[i][j] = new EmptyCell(i, j);
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
        int size = getMap().length;
        int x, y;

        do {
            x = rand.nextInt(size);
            y = rand.nextInt(size);
        } while (
                (x == 0 && y == 0) || getMap()[x][y] instanceof WallCell || !(getMap()[x][y] instanceof EmptyCell)
        );

        cell.x = x;
        cell.y = y;
        getMap()[x][y] = cell;
    }

    /**
     * Checks to determine if player is on a ladder,
     * this indicates either player has won or proceeds to next level.
     *
     * @return true if player has completed the game
     */
    public boolean checkWin() {
        return playerHasWon;
    }

    /**
     * Updates playerHasWon variable which confirms if the player has won the game or not.
     *
     * @param won true or false statement if player has won or not.
     */
    public void setPlayerHasWon(boolean won) {this.playerHasWon = won;}

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
     * Replaces the cell at the given coordinates with an EmptyCell.
     * Used when player interacts with cells that are one time use (e.g GoldCells)
     *
     * @param x the cells x-coordinate.
     * @param y the cells y-coordinate.
     */
    public void clearCell(int x, int y) {
        getMap()[x][y] = new EmptyCell(x, y);
    }

    /**
     * Checks cells around player to check if cells can attack player (e.g. RangedMutantCell)
     */
    public void checkRangedAttack () {
        int px = player.getX();
        int py = player.getY();

        for (int i = 0; i < getMap().length; i++) {
            for (int j = 0; j < getMap()[i].length; j++) {
                Cell cell = getMap()[i][j];

                if (cell instanceof RangedMutantCell) {
                    ((RangedMutantCell) cell).tryAttack(player);
                }
            }
        }
    }

    /**
     * Plays a text-based version of the game
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameEngine engine;

        System.out.print("MiniDungeon (N = New Game, C = Continue Game): ");
        String input = scanner.nextLine().trim().toLowerCase();

        switch (input) {
            case "n" -> {
                engine = new GameEngine(10);
                engine.loadHighScores();
                System.out.print("Enter Player Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter difficulty (1 = Easy, 2 = Medium, 3 = Hard): ");
                int difficulty = Integer.parseInt(scanner.nextLine());
                engine.setDifficulty(difficulty);
                engine.setPlayer(new Player(name, 0, 0));
                engine.generateMap();
            }
            case "c" -> {
                engine = GameEngine.loadGame("savegame.dat");
                if (engine == null) {
                    System.out.println("Failed to load game. Starting new one.");
                    engine = new GameEngine(10);
                    engine.loadHighScores();
                    System.out.print("Enter Player Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter difficulty (1 = Easy, 2 = Medium, 3 = Hard): ");
                    int difficulty = Integer.parseInt(scanner.nextLine());
                    engine.setDifficulty(difficulty);
                    engine.setPlayer(new Player(name, 0, 0));
                    engine.generateMap();
                }
            }
            default -> {
                System.out.println("Invalid input. Exiting.");
                return;
            }
        }

        // Main gameplay loop
        while (!engine.checkWin() && !engine.checkLose()) {
            engine.printMap();

            System.out.print("Enter move (u = up, d = down, l = left, r = right, s = save game): ");
            String move = scanner.nextLine().trim().toLowerCase();

            switch (move) {
                case "u" -> engine.moveUp();
                case "d" -> engine.moveDown();
                case "l" -> engine.moveLeft();
                case "r" -> engine.moveRight();
                case "s" -> {
                    engine.saveGame("savegame.dat");
                    System.out.println("Game saved. Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid input.");
            }

//            engine.checkNextLevel();
            System.out.println();
        }

        if (engine.checkWin()) {
            System.out.println("You escaped the dungeon!");

            List<HighScores> top5 = engine.updateHighScores();
            System.out.println("=======================");
            System.out.println("Top 5 High Scores:");
            for (String line : engine.formatHighScores()) {
                System.out.println(line);
            }
        }
        else System.out.println("You lost the game.");
    }

}
