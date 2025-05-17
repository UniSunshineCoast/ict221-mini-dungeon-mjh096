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


}
