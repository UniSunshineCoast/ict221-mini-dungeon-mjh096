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

    private final String playerName;
    private final int score;
    private final LocalDate date;

    public HighScores(String name, int score, LocalDate date) {
        this.playerName = name;
        this.score = score;
        this.date = date;
    }

    /**
     * @return Name of scoring player
     */
    public String getPlayerName() {return playerName;}
    /**
     * @return Returns captured score
     */
    public int getScore() {return score;}

    /**
     * @return Date score was taken on
     */
    public LocalDate getDate() {return date;}

    @Override
    public String toString() {
        return playerName + " - " + score + " (" + date + ")";
    }

    /**
     * Saves HighScores to maintain persistant scores between games.
     *
     * @param scores List of scores as they stand.
     * @param filename Scores will be saved as.
     */
    public static void save(List<HighScores> scores, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(scores);
        } catch (IOException e) {
            System.out.println("Failed to save high scores");
        }
    }

    /**
     * Loads HighScores from file
     *
     * @param filename Name of file to load.
     * @return Array List of highScores.
     */
    public static List<HighScores> load(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<HighScores>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // If no file exists, create a new one instead.
        }
    }
}
