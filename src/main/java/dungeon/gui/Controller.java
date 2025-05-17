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

        upBtn.setOnAction(e -> handleMove("up"));
        downBtn.setOnAction(e -> handleMove("down"));
        leftBtn.setOnAction(e -> handleMove("left"));
        rightBtn.setOnAction(e -> handleMove("right"));

        updateGui();
    }

    private void handleMove(String direction) {
        switch (direction) {
            case "up" -> engine.moveUp();
            case "down" -> engine.moveDown();
            case "left" -> engine.moveLeft();
            case "right" -> engine.moveRight();
        }

        updateGui();

        // Optional: check for win or lose
//        if (engine.checkWin()) {
//            showEndScreen(true); // win
//        } else if (engine.checkLose()) {
//            showEndScreen(false); // lose
//        }
    }

    private void updateGui() {
        // Clear old GUI grid pane
        gridPane.getChildren().clear();

        // Checks if Player is present before proceeding
        if (engine.getPlayer() == null) {
            System.out.println("Player not set yet — skipping GUI update.");
            return;
        }

        nameLabel.setText("Name: " + engine.getPlayer().getName());

        // Gets Players position
        int playerX = engine.getPlayer().getX();
        int playerY = engine.getPlayer().getY();

        // Loop through map board and add each cell into grid pane
        for (int i = 0; i < engine.getSize(); i++) {
            for (int j = 0; j < engine.getSize(); j++) {
                Cell cell = engine.getMap()[i][j];

                String icon = (i == playerX && j == playerY) ? "P" : cell.getIcon();

                Label cellLabel = new Label(icon);
                cellLabel.setMinSize(25, 25);
                cellLabel.setStyle("-fx-border-color: black; -fx-alignment: center; -fx-font-weight: bold;");

                gridPane.add(cellLabel, j, i);
            }
        }

        // Sets up Player stats
        hpLabel.setText("HP: " + engine.getPlayer().getHP());
        scoreLabel.setText("Score: " + engine.getPlayer().getScore());
        stepsLabel.setText("Steps Taken: " + engine.getPlayer().getStepsTaken());
        gridPane.setGridLinesVisible(true);
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
        updateGui();
    }
}
