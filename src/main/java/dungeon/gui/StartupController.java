package dungeon.gui;

import dungeon.engine.GameEngine;
import dungeon.engine.HighScores;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StartupController {
    @FXML private TextField nameField;
    @FXML private ComboBox<String> difficultyBox;
    @FXML private Button newGameBtn, loadGameBtn;
    @FXML private ListView<String> highScoresList;

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

    private void loadGame() {
        GameEngine engine = GameEngine.loadGame("savegame.dat");
        switchToGameGUI(engine);
    }

    private void switchToGameGUI(GameEngine engine) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game_gui.fxml"));
            Scene scene = new Scene(loader.load());
            Controller gameController = loader.getController();
            gameController.setEngine(engine);

            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MiniDungeon Game");
            stage.show();
        } catch (IOException ex) {
            showAlert("Failed to load game screen.");
        }
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.WARNING, message).showAndWait();
    }
}
