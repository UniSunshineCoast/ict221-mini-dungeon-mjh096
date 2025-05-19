package dungeon.gui;

import dungeon.engine.GameEngine;
import dungeon.engine.HighScores;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * GUI for the Maze Runner Game.
 *
 * NOTE: Do NOT run this class directly in IntelliJ - run 'RunGame' instead.
 */
public class GameGUI extends Application {

    public static final String gameTitle = "Mini Dungeon";

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("startup_gui.fxml"));

        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.setTitle(gameTitle);
        primaryStage.show();
    }

    public static void showEnd(Stage primaryStage, GameEngine engine, boolean won) throws Exception {
        BorderPane root = FXMLLoader.load(GameGUI.class.getResource("end_gui.fxml"));
        engine.updateHighScores();

        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.setTitle(gameTitle);
        primaryStage.show();
    }

    /** In IntelliJ, do NOT run this method.  Run 'RunGame.main()' instead. */
    public static void main(String[] args) {
        launch(args);
    }
}
