package dungeon.gui;

import dungeon.engine.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class EndController {

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
}