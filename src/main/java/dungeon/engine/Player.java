package dungeon.engine;

public class Player {
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
     * @return Players Current y-coordinate
     */
    public int getY() { return y; }

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

        incrementSteps();

        System.out.println("You moved to (" + newX + ", " + newY + ").");
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
