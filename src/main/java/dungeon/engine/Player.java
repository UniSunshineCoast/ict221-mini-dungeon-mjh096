package dungeon.engine;

import java.io.Serializable;

public class Player implements Serializable {
    /**
     * Unique ID for serialization compatibility.
     * Used to ensure that object matches the version of the class.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Name of Player
     */
    private String name;
    /**
     * x-coordinate (vertical move)
     */
    private int x;
    /**
     * y-coordinate (horizontal move)
     */
    private int y;
    /**
     * Health Points
     */
    private int hp;
    /**
     * Players Score
     */
    private int score;
    /**
     * Players Steps Taken
     */
    private int stepsTaken;

    /**
     * Allows GameEngine to pass through to manage Cells the player passes over
     */
    private GameEngine gameEngine;

    /**
     * The Player class represents the player in the MiniDungeon game.
     * It tracks the player's position, health, score, and steps taken.
     *
     * @param name Name of Player
     * @param x Current x-coordinate
     * @param y Current y-coordinate
     */
    public Player(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.hp = 10;
        this.score = 0;
        this.stepsTaken = 0;
    }

    /**
     * Updates Players Current Health Points.
     *
     * @param amount Updated Health Points
     */
    public void updateHP(int amount) {
        this.hp = amount;
    }

    /**
     * @param amount amount of health to add
     */
    public void healPlayer(int amount) {
        this.hp += amount;
    }

    /**
     * @param amount amount of damage player takes
     */
    public void damagePlayer(int amount) {
        this.hp -= amount;
    }

    /**
     * Updates Players Current Score.
     *
     * @param amount Updated Score
     */
    public void updateScore(int amount) {
        this.score += amount;
    }

    /**
     * Gets Players Current Name.
     *
     * @return Players Name
     */
    public String getName() { return name; }

    /**
     * @return Players Current x-coordinate
     */
    public int getX() { return x; }

    /**
     * Sets the Players x-coordinate
     *
     * @param x Players Current x-coordinate
     */
    public void setX(int x) { this.x = x; }

    /**
     * @return Players Current y-coordinate
     */
    public int getY() { return y; }

    /**
     * Sets the Players y-coordinate
     *
     * @param y Players Current y-coordinate
     */
    public void setY(int y) { this.y = y; }

    /**
     * @return Players Current HP (Health Points).
     */
    public int getHP() { return hp; }

    /**
     * @return Players Current Score
     */
    public int getScore() { return score; }

    /**
     * @return Players Current Steps Taken
     */
    public int getStepsTaken() { return stepsTaken; }

    /**
     * Sets Players Current Name
     *
     * @param name Updated Player Name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Passes changes back to the gameEngine
     *
     * @param engine Engine of the MiniDungeon
     */
    public void setGameEngine(GameEngine engine) {
        this.gameEngine = engine;
    }

    /**
     * Fetches MiniDungeon Game Engine
     * Allows management of Cells through the Player
     *
     * @return Engine of the MiniDungeon
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * Moves Player to specified postion on the map.
     *
     * @param dx The changed x-coordinate.
     * @param dy The changed y-coordinate.
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Increments Players Steps Taken by One.
     */
    public void incrementSteps() {
        stepsTaken++;
    }

    /**
     * Moves the player by the specified deltas on the map, if valid.
     * If not, prints a message stating the reason player cannot move.
     *
     * @param map The game map used to check for obstacles and interactions.
     * @param dx The changed x-coordinate.
     * @param dy The changed y-coordinate.
     * @return updated map variable.
     */
    private Cell[][] movePlayer(Cell[][] map, int dx, int dy) {
        int newX = getX() + dx;
        int newY = getY() + dy;


        if (newX < 0 || newX >= map.length || newY < 0 || newY >= map[0].length) {
            System.out.println("You cannot move outside the map.");
            return map;
        }

        if (map[newX][newY] instanceof WallCell) {
            System.out.println("You bumped into a wall.");
            return map;
        }

        move(dx, dy);
        map[newX][newY].interact(this);

        gameEngine.checkRangedAttack();

        incrementSteps();

        return map;
    }


    /**
     * Method to move player in the correct direction on the map (Up, Down, Left, Right).
     *
     * @param map The game map used to check for obstacles and interactions.
     * @return updated map variable.
     */
    public Cell[][] moveUp(Cell[][] map) {return movePlayer(map,-1, 0); }
    public Cell[][] moveDown(Cell[][] map) {return movePlayer(map,1, 0); }
    public Cell[][] moveLeft(Cell[][] map) {return movePlayer(map,0, -1); }
    public Cell[][] moveRight(Cell[][] map) {return movePlayer(map,0, 1); }
}
