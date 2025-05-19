package dungeon.gui;

import dungeon.engine.GameEngine;
import dungeon.engine.HighScores;
import dungeon.engine.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StartupController {
    /** Players Name. */
    @FXML private TextField nameField;
    /** Game Difficulty. */
    @FXML private ComboBox<String> difficultyBox;
    /** Game Actions (e.g. New Game and Load Game). */
    @FXML private Button newGameBtn, loadGameBtn;
    /** Displayed List of High Scores. */
    @FXML private ListView<String> highScoresList;

    /**
     * Initialises the controller. Wiring movement actions to button.
     */
    @FXML
    public void initialize() {
        difficultyBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyBox.setValue("Easy");

        List<HighScores> scores = HighScores.load("highscores.dat");
        for (HighScores s : scores) {
            highScoresList.getItems().add(s.toString());
        }

        newGameBtn.setOnAction(e -> NewGame());
        loadGameBtn.setOnAction(e -> loadGame());
    }

    /**
     * When New Game is pressed, triggers new game and progresses to game GUI.
     */
    private void NewGame() {
        String name = nameField.getText().trim();
        int difficulty = difficultyBox.getSelectionModel().getSelectedIndex() + 1;

        if (name.isEmpty()) {
            showAlert("Please enter a name.");
            return;
        }

        GameEngine engine = new GameEngine(10);
        engine.setDifficulty(difficulty);
        engine.setPlayer(new dungeon.engine.Player(name, 0, 0));
        engine.generateMap();

        switchToGameGUI(engine);
    }

    /**
     * When Load Game is pressed, loads game file if possible and proceeds to game GUI.
     */
    private void loadGame() {
        GameEngine engine = GameEngine.loadGame("savegame.dat");
        switchToGameGUI(engine);
    }

    /**
     * Progresses to the Game GUI and setups the engine and game grid.
     *
     * @param engine engine for Mini Dungeon
     */
    private void switchToGameGUI(GameEngine engine) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game_gui.fxml"));
            Scene scene = new Scene(loader.load());
            GameController gameController = loader.getController();
            String name = nameField.getText().trim();
            String difficultyText = difficultyBox.getValue();
            int difficulty = switch (difficultyText) {
                case "Easy" -> 1;
                case "Medium" -> 2;
                case "Hard" -> 3;
                default -> 1; // fallback
            };
            engine.setDifficulty(difficulty);
            engine.setPlayer(new Player(name, 0, 0));
            gameController.setEngine(engine);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(GameGUI.gameTitle);
            stage.show();
        } catch (IOException ex) {
            showAlert("Failed to load game screen.");
        }
    }

    /**
     * Shows Alert to user if issue occurs (e.g, No Name for Player).
     *
     * @param message message to present
     */
    private void showAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message).showAndWait();
    }
}
