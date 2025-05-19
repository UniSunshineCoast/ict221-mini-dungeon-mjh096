package dungeon.gui;

import dungeon.engine.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class EndController {
    /** Exit Game Button. */
    @FXML private Button exitBtn;
    /** Restart Game Button. */
    @FXML private Button restartBtn;

    /** Mini Dungeon Engine. */
    private GameEngine engine;
    /** Win/Loss Check. */
    private boolean won;

    /**
     * Initialises the controller. Wiring movement actions to button.
     */
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
}