package dungeon.gui;

import dungeon.engine.Cell;
import dungeon.engine.GameEngine;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller {
    @FXML private GridPane gridPane;
    @FXML private Label nameLabel;
    @FXML private Label levelLabel;
    @FXML private Label hpLabel;
    @FXML private Label scoreLabel;
    @FXML private Label stepsLabel;
    @FXML private Button upBtn, downBtn, leftBtn, rightBtn;
    @FXML private Button saveBtn, loadBtn, helpBtn;
    @FXML private TextArea statusArea;

    GameEngine engine;

    @FXML
    public void initialize() {
        engine = new GameEngine(10);

        updateGui();
    }

    private void updateGui() {
        // Clear old GUI grid pane
        gridPane.getChildren().clear();

        if (engine.getPlayer() != null) {
            nameLabel.setText("Name: " + engine.getPlayer().getName());
        } else {
            nameLabel.setText("Name: (Unknown)");
        }

        // Loop through map board and add each cell into grid pane
        for (int i = 0; i < engine.getSize(); i++) {
            for (int j = 0; j < engine.getSize(); j++) {
                Cell cell = engine.getMap()[i][j];

                // For now, represent each cell with a Label or Pane
                Label cellLabel = new Label(cell.getIcon());
                cellLabel.setMinSize(25, 25);
                cellLabel.setStyle("-fx-border-color: black; -fx-alignment: center;");

                gridPane.add(cellLabel, j, i);
            }
        }
        gridPane.setGridLinesVisible(true);
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
        updateGui();
    }
}
