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
    /** GridPane that renders the 10x10 dungeon grid. */
    @FXML private GridPane gridPane;
    /** Players Name. */
    @FXML private Label nameLabel;
    /** Players current Level. */
    @FXML private Label levelLabel;
    /** Players current HP. */
    @FXML private Label hpLabel;
    /** Players current score. */
    @FXML private Label scoreLabel;
    /** Players steps taken. */
    @FXML private Label stepsLabel;
    /** Movement and function buttons. */
    @FXML private Button upBtn, downBtn, leftBtn, rightBtn, saveBtn;
    /** TextArea that shows status messages (e.g., "You picked up gold"). */
    @FXML private TextArea statusArea;

    GameEngine engine;

    /**
     * Initialises the controller. Wiring movement actions to button.
     */
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


    /**
     * Processes player movement, insturcting engine to move player in specific direction.
     *
     * @param direction Up, Down, Left, Right.
     * @throws Exception if player movement fails to complete.
     */
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

    /**
     * Refreshes grid to ensure cells are in the proper position and player stats stay up to date.
     */
    private void updateGui() {
        // Clear old GUI grid pane
        gridPane.getChildren().clear();

        // Checks if Player data is present before proceeding
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
        levelLabel.setText("Level: " + engine.getCurrentLevel());
        scoreLabel.setText("Score: " + engine.getPlayer().getScore());
        stepsLabel.setText("Steps Taken: " + engine.getPlayer().getStepsTaken());
        gridPane.setGridLinesVisible(true);
    }

    /**
     * Redirects terminal output in the StatusArea in the GUI
     */
    private void redirectSystemOut() {
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                Platform.runLater(() -> {statusArea.appendText(String.valueOf((char) b));});
            }
        }, true);

        System.setOut(printStream);
    }

    /**
     * Applies engine updates back to GameEngine
     *
     * @param engine current GameEngine
     */
    public void setEngine(GameEngine engine) {
        this.engine = engine;
        updateGui();
    }
}
