import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class Controller {

    ObservableList<String> cbxLevelList = FXCollections.
            observableArrayList("Level 1", "Level 2", "Level 3");

    // FXML components

    @FXML
    private Button btnClear;

    @FXML
    private Button btnStart;

    @FXML
    private TextArea moveList;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView iv00, iv01, iv02, iv10, iv11, iv12, iv20, iv21, iv22;

    @FXML
    private ChoiceBox<String> cbxLevel;

    // Variables

    private Scanner sc = new Scanner(System.in);

    private char[][] board = new char[3][3];

    private final Image imgX = new Image("x.png");
    private final Image imgO = new Image("o.png");

    private int moveCtr = 0;
    private int aiDifficulty;
    private boolean aiTurn = true;
    private boolean aiFirstMove = true;

    private void initCbx () {
        cbxLevel.setValue("Level 1");
        cbxLevel.setItems(cbxLevelList);
    }

    public void gameInit () { initCbx(); } // Set values inside choice box

    @FXML
    private void gameSetup () {

        aiDifficulty = Integer.parseInt(cbxLevel.getValue().substring(6));
        moveList.appendText("Game Start: AI level " + aiDifficulty + "\n");

        if(aiFirstMove)
            moveList.appendText("AI has first move" + "\n");
        else
            moveList.appendText("Player has first move" + "\n");

        aiFirstMove = !aiFirstMove;
        startGame(aiDifficulty);
    }

    private void startGame (int aiDifficulty) {



    }


    @FXML
    private void sample () {


    }


}
