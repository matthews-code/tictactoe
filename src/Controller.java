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
import java.util.*;

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

    private char[][] board = {{' ', '|', ' ', '|', ' '},
                              {'-', '+', '-', '+', '-'},
                              {' ', '|', ' ', '|', ' '},
                              {'-', '+', '-', '+', '-'},
                              {' ', '|', ' ', '|', ' '}};

    private List<Integer> playerPos = new ArrayList<>();
    private List<Integer> aiPos = new ArrayList<>();

    private final Image imgX = new Image("x.png");
    private final Image imgO = new Image("o.png");

    private int moveCtr = 0;
    private int aiDifficulty;
    private String winMessage;
    private boolean aiTurn = true;
    private boolean aiFirstMove = true;
    private boolean gameIsDone = false;

    private void initCbx () {
        cbxLevel.setValue("Level 1");
        cbxLevel.setItems(cbxLevelList);
    }

    public void gameInit () { initCbx(); } // Set values inside choice box

    @FXML
    private void gameSetup () {

        btnStart.setDisable(true);
        aiDifficulty = Integer.parseInt(cbxLevel.getValue().substring(6));
        moveList.appendText("Game Start: AI level " + aiDifficulty + "\n");

        if(aiFirstMove)
            moveList.appendText("AI has first move" + "\n");
        else
            moveList.appendText("Player has first move" + "\n");

        aiFirstMove = !aiFirstMove;
        startGame(aiDifficulty);
    }

    public void startGame (int aiDifficulty) { // Whole game function

        // Fix loop
        while(!gameIsDone) {
            choosePos(aiTurn);
            aiTurn = !aiTurn;
            checkWinner();
            moveCtr++;
        }
        System.out.println("The game is done!");
    }

    private void choosePos(boolean aiTurn) {

        boolean invalid = true;

        Random rand = new Random();
        int pos = 0;

        while(invalid) {
            pos = rand.nextInt(9) + 1;
            if(!aiTurn) {
                System.out.print("Player turn \nChoose position (1-9): ");
                pos = sc.nextInt();
                System.out.println();
            }

            if(!playerPos.contains(pos) && !aiPos.contains(pos) && (pos < 10 && pos > 0))
                invalid = false;
            else if(!aiTurn)
                System.out.println("Invalid move!");
        }

        if(!aiTurn) {
            playerPos.add(pos);
        }
        else {
            System.out.println("AI turn \nAI chose position: " + pos + "\n");
            aiPos.add(pos);
        }

        setPos(moveCtr, pos);

        for(char[] row: board) {
            for(char c: row)
                System.out.print(c);
            System.out.println();
        }
        System.out.println();
    }

    private void checkWinner () {

        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);

        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);

        List positiveDiag = Arrays.asList(7, 5, 3);
        List negativeDiag = Arrays.asList(1, 5, 9);

        List<List> winConditions = new ArrayList<>();
        winConditions.add(topRow);
        winConditions.add(midRow);
        winConditions.add(botRow);
        winConditions.add(leftCol);
        winConditions.add(midCol);
        winConditions.add(rightCol);
        winConditions.add(positiveDiag);
        winConditions.add(negativeDiag);

        for(List winCond: winConditions) {
            if(playerPos.containsAll(winCond)) {
                gameIsDone = true;
                System.out.println("You are the winner!");
            }
            else if(aiPos.containsAll(winCond)) {
                gameIsDone = true;
                System.out.println("You are the loser!");
            }
        }
        if(aiPos.size() + playerPos.size() == 9) {
            gameIsDone = true;
            System.out.println("It's a tie!");
        }
    }

    private void setPos (int moveCtr, int pos) {

        char symbol;

        if(moveCtr % 2 == 0)
            symbol = 'X';
        else
            symbol = 'O';

        switch (pos) {
            case 1 -> board[0][0] = symbol;
            case 2 -> board[0][2] = symbol;
            case 3 -> board[0][4] = symbol;
            case 4 -> board[2][0] = symbol;
            case 5 -> board[2][2] = symbol;
            case 6 -> board[2][4] = symbol;
            case 7 -> board[4][0] = symbol;
            case 8 -> board[4][2] = symbol;
            case 9 -> board[4][4] = symbol;

        }
    }
}
