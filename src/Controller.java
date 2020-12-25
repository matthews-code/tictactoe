import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;
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
    private ImageView[] imgArr = {iv00, iv01, iv02, iv10, iv11, iv12, iv20, iv21, iv22};

    @FXML
    private ChoiceBox<String> cbxLevel;

    // Variables

    private final Scanner sc = new Scanner(System.in);

    private final char[][] board = {{' ', '|', ' ', '|', ' '},
                              {'-', '+', '-', '+', '-'},
                              {' ', '|', ' ', '|', ' '},
                              {'-', '+', '-', '+', '-'},
                              {' ', '|', ' ', '|', ' '}};

    private final Tile[][] guiBoard = new Tile[3][3];

    private List<Integer> playerPos = new ArrayList<>();
    private List<Integer> aiPos = new ArrayList<>();

    private final Image imgX = new Image("x.png");
    private final Image imgO = new Image("o.png");

    private int moveCtr = 0;
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

        int ctr = 0;
        for(int i = 0; i < guiBoard.length; i++)
            for(int j = 0; j < guiBoard.length; j++) {
                guiBoard[i][j] = new Tile(i, j, ctr, imgArr[ctr]);
                ctr++;
            }

        int aiDifficulty = Integer.parseInt(cbxLevel.getValue().substring(6));
        moveList.appendText("Game Start: AI level " + aiDifficulty + "\n");

        if(aiFirstMove)
            moveList.appendText("AI has first move" + "\n");
        else
            moveList.appendText("Player has first move" + "\n");

        startGame(aiDifficulty);
        aiFirstMove = !aiFirstMove;
    }

    public void startGame (int aiDifficulty) { // Whole game function

        int score;
        // Fix loop
        while(!gameIsDone) {
            choosePos(aiTurn, aiDifficulty);
            aiTurn = !aiTurn;
            score = checkWinner();
            if(score == 1) {
                System.out.println("You are the winner!");
                moveList.appendText("You are the winner!\n");
            }

            if(score == -1) {
                System.out.println("You are the loser!");
                moveList.appendText("You are the loser!\n");
            }

            if(score == 0) {
                System.out.println("It's a tie!");
                moveList.appendText("It's a tie!\n");
            }

            moveCtr++;
        }
        System.out.println("The game is done!");
    }

    private void mouseHandler (MouseEvent e) {

        Tile tile;
        tile = (Tile) e.getSource();

    }

    private void choosePos(boolean aiTurn, int aiDifficulty) { // AI or player chooses position based on the board

        boolean invalid = true;

        Random rand = new Random();
        int pos = 0;

        while (invalid) {
            pos = rand.nextInt(9) + 1;
            if (!aiTurn) {
                System.out.print("Player turn \nChoose position (1-9): ");
                moveList.appendText("Player turn \nClick on a tile\n");
                pos = sc.nextInt(); // Figure out a way to get value from mouse event

                System.out.println();
            }
            if (!playerPos.contains(pos) && !aiPos.contains(pos) && (pos < 10 && pos > 0))
                invalid = false;
            else if (!aiTurn) {
                System.out.println("Invalid move!");
                moveList.appendText("Invalid move!\n");
            }
        }

        if(!aiTurn) {
            playerPos.add(pos);
        }
        else {
            System.out.println("AI's turn \nAI chose position: " + pos + "\n");
            moveList.appendText("AI's turn \nAI chose position: " + pos + "\n");
            aiPos.add(pos);
        }

        setPos(moveCtr, pos);
        displayBoard(board);
    }

    private int checkWinner () { // Checker for winner

        List<Integer> topRow = Arrays.asList(1, 2, 3);
        List<Integer> midRow = Arrays.asList(4, 5, 6);
        List<Integer> botRow = Arrays.asList(7, 8, 9);

        List<Integer> leftCol = Arrays.asList(1, 4, 7);
        List<Integer> midCol = Arrays.asList(2, 5, 8);
        List<Integer> rightCol = Arrays.asList(3, 6, 9);

        List<Integer> positiveDiag = Arrays.asList(7, 5, 3);
        List<Integer> negativeDiag = Arrays.asList(1, 5, 9);

        List<List<Integer>> winConditions = new ArrayList<>();
        winConditions.add(topRow);
        winConditions.add(midRow);
        winConditions.add(botRow);
        winConditions.add(leftCol);
        winConditions.add(midCol);
        winConditions.add(rightCol);
        winConditions.add(positiveDiag);
        winConditions.add(negativeDiag);

        for(List<Integer> winCond: winConditions) {
            if(playerPos.containsAll(winCond)) {
                gameIsDone = true;
                return 1;
            }
            else if(aiPos.containsAll(winCond)) {
                gameIsDone = true;
                return -1;
            }
            else if(aiPos.size() + playerPos.size() == 9) {
                gameIsDone = true;
                return 0;
            }
        }
        return -100;
    }

    private void setPos (int moveCtr, int pos) { // Position setter based from choosePos

        char symbol;
        Image img;

        if(moveCtr % 2 == 0) {
            symbol = 'X';
            img = imgX;
        }
        else {
            symbol = 'O';
            img = imgO;
        }

        switch (pos) {
            case 1: board[0][0] = symbol;
                    iv00.setImage(img);
                    break;
            case 2: board[0][2] = symbol;
                    iv01.setImage(img);
                    break;
            case 3: board[0][4] = symbol;
                    iv02.setImage(img);
                    break;
            case 4: board[2][0] = symbol;
                    iv10.setImage(img);
                    break;
            case 5: board[2][2] = symbol;
                    iv11.setImage(img);
                    break;
            case 6: board[2][4] = symbol;
                    iv12.setImage(img);
                    break;
            case 7: board[4][0] = symbol;
                    iv20.setImage(img);
                    break;
            case 8: board[4][2] = symbol;
                    iv21.setImage(img);
                    break;
            case 9: board[4][4] = symbol;
                    iv22.setImage(img);
                    break;

        }
    }

    private void displayBoard (char[][] board) { // Console display of board
        for(char[] row: board) {
            for(char c: row)
                System.out.print(c);
            System.out.println();
        }
        System.out.println();
    }
}
