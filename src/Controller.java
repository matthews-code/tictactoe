import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javax.swing.*;
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
    private int aiDifficulty = 0;
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

        gridPane.getOnMouseClicked();

        btnStart.setDisable(true);
        btnClear.setDisable(false);
        cbxLevel.setDisable(true);

        iv00.setDisable(false);
        iv01.setDisable(false);
        iv02.setDisable(false);
        iv10.setDisable(false);
        iv11.setDisable(false);
        iv12.setDisable(false);
        iv20.setDisable(false);
        iv21.setDisable(false);
        iv22.setDisable(false);



        int ctr = 0;
        for(int i = 0; i < guiBoard.length; i++)
            for(int j = 0; j < guiBoard.length; j++) {
                guiBoard[i][j] = new Tile(i, j, ctr, imgArr[ctr]);
                ctr++;
            }

        aiDifficulty = Integer.parseInt(cbxLevel.getValue().substring(6));
        moveList.appendText("Game Start: AI level " + aiDifficulty + "\n");

        if(aiFirstMove)
            moveList.appendText("""
                    AI has first move

                    """);
        else
            moveList.appendText("""
                    Player has first move

                    """);

        if(aiFirstMove)
            startGame(0);

        iv00.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(1);
            }
        });

        iv01.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(2);
            }
        });

        iv02.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(3);
            }
        });

        iv10.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(4);
            }
        });

        iv11.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(5);
            }
        });

        iv12.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(6);
            }
        });

        iv20.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(7);
            }
        });

        iv21.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(8);
            }
        });

        iv22.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                startGame(9);
            }
        });

        btnClear.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetGame();
            }
        });
    }

    private void resetGame() {
        moveCtr = 0;
        aiTurn = true;
        aiPos.clear();
        moveList.clear();
        playerPos.clear();
        gameIsDone = false;
        aiFirstMove = true;
        iv00.setImage(null);
        iv01.setImage(null);
        iv02.setImage(null);
        iv10.setImage(null);
        iv11.setImage(null);
        iv12.setImage(null);
        iv20.setImage(null);
        iv21.setImage(null);
        iv22.setImage(null);
        iv00.setDisable(true);
        iv01.setDisable(true);
        iv02.setDisable(true);
        iv10.setDisable(true);
        iv11.setDisable(true);
        iv12.setDisable(true);
        iv20.setDisable(true);
        iv21.setDisable(true);
        iv22.setDisable(true);
        btnClear.setDisable(true);
        btnStart.setDisable(false);
        cbxLevel.setDisable(false);
    }

    public void startGame (int pos) { // Whole game function

        choosePos(aiTurn, pos);

        if(gameIsDone) {
            iv00.setDisable(true);
            iv01.setDisable(true);
            iv02.setDisable(true);
            iv10.setDisable(true);
            iv11.setDisable(true);
            iv12.setDisable(true);
            iv20.setDisable(true);
            iv21.setDisable(true);
            iv22.setDisable(true);
            aiFirstMove = !aiFirstMove;
        }
    }

    private void choosePos(boolean aiTurn, int pos) { // AI or player chooses position based on the board

        boolean invalid = true;

        Random rand = new Random();
        if(aiTurn)
            while (invalid) {
                pos = rand.nextInt(9) + 1;
                if (!playerPos.contains(pos) && !aiPos.contains(pos))
                    invalid = false;
            }
        else
            if (!playerPos.contains(pos) && !aiPos.contains(pos))
                invalid = false;

        if(!invalid) {
            if(!aiTurn) {
                moveList.appendText("You chose position: " + pos + "\n\n");
                playerPos.add(pos);
            }
            else {
                System.out.println("AI's turn \nAI chose position: " + pos + "\n");
                moveList.appendText("AI chose position: " + pos + "\n\n");
                aiPos.add(pos);
            }

            setPos(moveCtr, pos);
            displayBoard(board);
            moveCtr++;
            this.aiTurn = !aiTurn;
        }
        checkWinner();
        if(this.aiTurn && !gameIsDone) {
            moveList.appendText("AI's turn\n");
            choosePos(true, 0);
        }
        else if (!this.aiTurn && !gameIsDone)
            moveList.appendText("Player's turn\n");

    }

    private void checkWinner () { // Checker for winner

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
                System.out.println("You are the winner!");
                moveList.appendText("You are the winner!\n");
            }
            else if(aiPos.containsAll(winCond)) {
                gameIsDone = true;
                System.out.println("You are the loser!");
                moveList.appendText("You are the loser!\n");
            }
        }
        if(aiPos.size() + playerPos.size() == 9 && !gameIsDone) {
            gameIsDone = true;
            System.out.println("It's a tie!");
            moveList.appendText("It's a tie!\n");
        }
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
