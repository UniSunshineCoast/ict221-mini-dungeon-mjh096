package dungeon.gui;

import dungeon.engine.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class EndController {

    @FXML private Label endTitleLabel;
    @FXML private Label finalScoreLabel;
    @FXML private Label finalStepsLabel;
    @FXML private Button exitBtn;
    @FXML private Button restartBtn;

    private GameEngine engine;
    private boolean won;

    @FXML
    public void initialize() {
        exitBtn.setOnAction(e -> {
            ((Stage) exitBtn.getScene().getWindow()).close();
        });

        restartBtn.setOnAction(e -> {
            try {
                new GameGUI().start((Stage) restartBtn.getScene().getWindow());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void endGame(GameEngine engine, boolean won) {
        this.engine = engine;
        this.won = won;

        endTitleLabel.setText(won ? "🎉 You Escaped the Dungeon!" : "💀 You Lost!");
        finalScoreLabel.setText("Final Score: " + engine.getPlayer().getScore());
        finalStepsLabel.setText("Steps Taken: " + engine.getPlayer().getStepsTaken());
    }

}