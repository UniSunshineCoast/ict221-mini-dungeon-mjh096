package dungeon.engine;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Tracks High Scores taken for the MiniDungeon
 */
public class HighScores implements Serializable {
    /**
     * Unique ID for serialization compatibility.
     * Used to ensure that object matches the version of the class.
     */
    private static final long serialVersionUID = 1L;

    private final String playername;
    private final int score;
    private final LocalDate date;

    public HighScores(String name, int score, LocalDate date) {
        this.playername = name;
        this.score = score;
        this.date = date;
    }

    public String getPlayerName() {return playername;}
    public int getScore() {return score;}
    public LocalDate getDate() {return date;}

    @Override
    public String toString() {
        return playername + " - " + score + " (" + date + ")";
    }

    public static void save(List<HighScores> scores, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(scores);
        } catch (IOException e) {
            System.out.println("Failed to save high scores");
        }
    }

    public static List<HighScores> load(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<HighScores>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // If no file exists, create a new one instead.
        }
    }
}
