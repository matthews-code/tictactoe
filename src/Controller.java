import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.*;

public class Controller {

    ObservableList<String> cbxLevelList = FXCollections.
            observableArrayList("Level 0", "Level 1", "Level 2");

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

    @FXML
    private Label lblX, lblO;

    // Variables

    private final Tile[][] guiBoard = new Tile[3][3];

    // List of current positions for each player (1-9) (Tiles numbered from left to right, top to bottom)
    private final List<Integer> playerPos = new ArrayList<>();
    private final List<Integer> aiPos = new ArrayList<>();

    private final Image imgX = new Image("x.png");
    private final Image imgO = new Image("o.png");

    private int moveCtr = 0;
    private int aiDifficulty = 0;
    private boolean aiTurn = true;
    private boolean aiFirstMove = true;
    private boolean gameIsDone = false;
    private final ArrayList<ImageView> ivArray = new ArrayList<>();

    public void gameInit () {
        initCbx();
        ivArray.add(iv00);
        ivArray.add(iv01);
        ivArray.add(iv02);
        ivArray.add(iv10);
        ivArray.add(iv11);
        ivArray.add(iv12);
        ivArray.add(iv20);
        ivArray.add(iv21);
        ivArray.add(iv22);

    } // Set values inside choice box

    private void initCbx () {
        cbxLevel.setValue("Level 0");
        cbxLevel.setItems(cbxLevelList);
    }

    @FXML
    private void gameSetup () {

        btnStart.setDisable(true);
        btnClear.setDisable(false);
        cbxLevel.setDisable(true);

        for(ImageView iv : ivArray)
            iv.setDisable(false);

        /*iv00.setDisable(false);
        iv01.setDisable(false);
        iv02.setDisable(false);
        iv10.setDisable(false);
        iv11.setDisable(false);
        iv12.setDisable(false);
        iv20.setDisable(false);
        iv21.setDisable(false);
        iv22.setDisable(false);*/

        int ctr = 0;
        for(int i = 0; i < guiBoard.length; i++)
            for(int j = 0; j < guiBoard.length; j++)
                guiBoard[i][j] = new Tile(i, j, ctr);


        aiDifficulty = Integer.parseInt(cbxLevel.getValue().substring(6));
        moveList.appendText("Game Start: AI level " + aiDifficulty + "\n");

        if(aiFirstMove) {
            lblX.setText("AI");
            lblO.setText("Player");
            moveList.appendText("""
                    AI has first move

                    """);
        }
        else {
            lblX.setText("Player");
            lblO.setText("AI");
            moveList.appendText("""
                    Player has first move

                    """);
        }

        if(aiFirstMove)
            startGame(0);

        // Below is hardcoded, replace with mouseHandler getSource()

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

    private void resetGame() { // Resets board information, switches first player to make move
        moveCtr = 0;
        aiPos.clear();
        moveList.clear();
        playerPos.clear();
        gameIsDone = false;
        lblX.setText(null);
        lblO.setText(null);
        /*iv00.setImage(null);
        iv01.setImage(null);
        iv02.setImage(null);
        iv10.setImage(null);
        iv11.setImage(null);
        iv12.setImage(null);
        iv20.setImage(null);
        iv21.setImage(null);
        iv22.setImage(null);*/
        for(ImageView iv : ivArray) {
            iv.setImage(null);
            iv.setDisable(true);
        }
        /*iv00.setDisable(true);
        iv01.setDisable(true);
        iv02.setDisable(true);
        iv10.setDisable(true);
        iv11.setDisable(true);
        iv12.setDisable(true);
        iv20.setDisable(true);
        iv21.setDisable(true);
        iv22.setDisable(true);*/

        btnClear.setDisable(true);
        btnStart.setDisable(false);
        cbxLevel.setDisable(false);
        aiFirstMove = !aiFirstMove;
        aiTurn = aiFirstMove;
        for(Tile[] tiles: guiBoard)
            for(Tile tile: tiles)
                tile.setOccupied(false);
    }

    public void startGame (int pos) { // Whole game function. Function not really needed

        choosePos(aiTurn, pos);

    }

    private int bestMove (Tile[][] board) {

        int bestVal = -100;
        int row = -1;
        int col = -1;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(!board[i][j].getOccupied()) {

                    board[i][j].setOccupied(true);
                    aiPos.add(board[i][j].getPos());

                    int currVal = gameTree(board, 0, aiFirstMove);

                    board[i][j].setOccupied(false);
                    aiPos.remove(aiPos.size() - 1);

                    if(currVal > bestVal) {
                        row = i;
                        col = j;
                        bestVal = currVal;
                    }
                }
            }
        }
        return board[row][col].getPos();
    }

    private int gameTree (Tile[][] board, int depth, boolean isMax) {
        int score = checkWinner(false);

        if(score == 1)
            return score;

        if(score == -1)
            return score;

        if(score == 0)
            return score;

        if(isMax) {
            int maxVal = -100;
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(!board[i][j].getOccupied()) {

                        board[i][j].setOccupied(true);
                        playerPos.add(board[i][j].getPos());
                        //System.out.println(board[i][j].getPos());

                        maxVal = Math.max(maxVal, gameTree(board, depth + 1, false));

                        board[i][j].setOccupied(false);
                        //System.out.println(playerPos.get(playerPos.size() - 1));
                        playerPos.remove(playerPos.size() - 1);
                    }
                }
            }
            return maxVal;
        }
        else {
            int minVal = 100;

            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    if(!board[i][j].getOccupied()) {

                        board[i][j].setOccupied(true);
                        aiPos.add(board[i][j].getPos());

                        minVal = Math.min(minVal, gameTree(board, depth + 1, true));

                        board[i][j].setOccupied(false);
                        aiPos.remove(aiPos.size() - 1);
                    }
                }
            }
            return minVal;
        }
    }

    private void choosePos(boolean aiTurn, int pos) { // AI or player chooses position based on the board

        boolean invalid = true;

        Random rand = new Random();
        if(aiTurn) {
            // ------------------------------> INSERT LEVELS BELOW <----------------------------- //

            if(aiDifficulty == 0)
                while (invalid) {
                    pos = rand.nextInt(9) + 1;
                    if (!playerPos.contains(pos) && !aiPos.contains(pos))
                        invalid = false;
                }
            else if(aiDifficulty == 1) { //Hard coded tables
                pos = bestMove(guiBoard);
                invalid = false;
            }
            else if(aiDifficulty == 2) { // MiniMax with AB pruning

            }
        }
        else if (!playerPos.contains(pos) && !aiPos.contains(pos)) // If player's choice is valid
            invalid = false;


        if(!invalid) { // If position chosen by AI or player is valid
            if(!aiTurn) {
                moveList.appendText("You chose position: " + pos + "\n\n");
                playerPos.add(pos);
            }
            else {
                //System.out.println("AI's turn \nAI chose position: " + pos + "\n");
                moveList.appendText("AI chose position: " + pos + "\n\n");
                aiPos.add(pos);
            }

            setPos(moveCtr, pos);
            this.aiTurn = !aiTurn;
            moveCtr++;
        }
        else
            moveList.appendText("Invalid move. Chose another position\n\n");

        checkWinner(true);
        if(this.aiTurn && !gameIsDone) {
            moveList.appendText("AI's turn\n");
            choosePos(true, 0); // Run choosePos right after player's turn
        }
        else if (!this.aiTurn && !gameIsDone)
            moveList.appendText("Player's turn\n");
        else {
            /*
            iv00.setDisable(true);
            iv01.setDisable(true);
            iv02.setDisable(true);
            iv10.setDisable(true);
            iv11.setDisable(true);
            iv12.setDisable(true);
            iv20.setDisable(true);
            iv21.setDisable(true);
            iv22.setDisable(true);*/
            for(ImageView iv : ivArray)
                iv.setDisable(true);
        }
    }

    private int checkWinner (boolean ifActual) { // Checker for winner

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
                if(ifActual) {
                    gameIsDone = true;
                    moveList.appendText("You are the winner!\n");
                }
                return -1;
            }
            else if(aiPos.containsAll(winCond)) {
                if(ifActual) {
                    gameIsDone = true;
                    moveList.appendText("You are the loser!\n");
                }
                return 1;
            }
        }
        if(aiPos.size() + playerPos.size() == 9 && !gameIsDone) {
            if(ifActual) {
                gameIsDone = true;
                moveList.appendText("It's a tie!\n");
            }
            return 0;
        }
        return -10;
    }

    private void setPos (int moveCtr, int pos) { // Position setter based from choosePos

        Image img;

        if(moveCtr % 2 == 0)
            img = imgX;
        else
            img = imgO;

        switch (pos) {
            case 1: iv00.setImage(img); guiBoard[0][0].setOccupied(true); break;
            case 2: iv01.setImage(img); guiBoard[0][1].setOccupied(true); break;
            case 3: iv02.setImage(img); guiBoard[0][2].setOccupied(true); break;
            case 4: iv10.setImage(img); guiBoard[1][0].setOccupied(true); break;
            case 5: iv11.setImage(img); guiBoard[1][1].setOccupied(true); break;
            case 6: iv12.setImage(img); guiBoard[1][2].setOccupied(true); break;
            case 7: iv20.setImage(img); guiBoard[2][0].setOccupied(true); break;
            case 8: iv21.setImage(img); guiBoard[2][1].setOccupied(true); break;
            case 9: iv22.setImage(img); guiBoard[2][2].setOccupied(true); break;
        }
    }


}
