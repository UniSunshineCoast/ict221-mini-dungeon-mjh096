package dungeon.gui;

import dungeon.engine.Cell;
import dungeon.engine.GameEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.OutputStream;
import java.io.PrintStream;

public class GameController {
    @FXML private GridPane gridPane;
    @FXML private Label nameLabel;
    @FXML private Label levelLabel;
    @FXML private Label hpLabel;
    @FXML private Label scoreLabel;
    @FXML private Label stepsLabel;
    @FXML private Button upBtn, downBtn, leftBtn, rightBtn, saveBtn;
    @FXML private TextArea statusArea;

    GameEngine engine;

    @FXML
    public void initialize() throws Exception {
        engine = new GameEngine(10);

        upBtn.setOnAction(e -> {try {handleMove("up");} catch (Exception ex) {throw new RuntimeException(ex);}});
        downBtn.setOnAction(e -> {try {handleMove("down");} catch (Exception ex) {throw new RuntimeException(ex);}});
        leftBtn.setOnAction(e -> {try {handleMove("left");} catch (Exception ex) {throw new RuntimeException(ex);}});
        rightBtn.setOnAction(e -> {try {handleMove("right");} catch (Exception ex) {throw new RuntimeException(ex);}});

        redirectSystemOut();
        updateGui();
    }

    private void handleMove(String direction) throws Exception {
        switch (direction) {
            case "up" -> engine.moveUp();
            case "down" -> engine.moveDown();
            case "left" -> engine.moveLeft();
            case "right" -> engine.moveRight();
        }

        updateGui();

        if (engine.checkWin()) {
            GameGUI.showEnd((Stage) gridPane.getScene().getWindow(), engine, true);
        } else if (engine.checkLose()) {
            GameGUI.showEnd((Stage) gridPane.getScene().getWindow(), engine, false);
        }
    }

    private void updateGui() {
        // Clear old GUI grid pane
        gridPane.getChildren().clear();

        // Checks if Player is present before proceeding
        if (engine.getPlayer() == null) {return;}

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

    private void redirectSystemOut() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                Platform.runLater(() -> {statusArea.appendText(String.valueOf((char) b));});
            }
        }, true); // autoFlush

        System.setOut(printStream);
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
        updateGui();
    }
}
