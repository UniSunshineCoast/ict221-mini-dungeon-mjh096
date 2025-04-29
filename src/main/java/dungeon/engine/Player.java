package dungeon.engine;

public class Player {
    private String name;
    private int x;
    private int y;
    private int hp;
    private int score;
    private int stepsTaken;

    public Player(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.hp = 10;
        this.score = 0;
        this.stepsTaken = 0;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    public void incrementSteps() {
        stepsTaken++;
    }
    public void updateHP(int amount) {}
    public void updateScore(int amount) {}

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHP() { return hp; }
    public int getScore() { return score; }
    public int getStepsTaken() { return stepsTaken; }
    public void setName(String name) { this.name = name; }

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

    // These just call movePlayer() with correct directions
    public Cell[][] moveUp(Cell[][] map) {return movePlayer(map,-1, 0); }
    public Cell[][] moveDown(Cell[][] map) {return movePlayer(map,1, 0); }
    public Cell[][] moveLeft(Cell[][] map) {return movePlayer(map,0, -1); }
    public Cell[][] moveRight(Cell[][] map) {return movePlayer(map,0, 1); }


    // Getters and setters for x, y, hp, score, stepsTaken (as needed)
}
