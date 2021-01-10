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

/**
 * This class is the controller for the whole game
 */
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

    //private final Tile[][] guiBoard = new Tile[3][3];

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

    /**
     * This function initializes the gui for the levels and the game board
     */
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
    }

    /**
     * This function initializes the choice box for the levels
     */
    private void initCbx () {
        cbxLevel.setValue("Level 0");
        cbxLevel.setItems(cbxLevelList);
    }

    /**
     * This function initializes the game
     */
    @FXML
    private void gameSetup () {

        btnStart.setDisable(true);
        btnClear.setDisable(false);
        cbxLevel.setDisable(true);

        for(ImageView iv : ivArray)
            iv.setDisable(false);

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

    /**
     * This function resets board information, clears the board and switches first player to make move
     */
    private void resetGame() {
        moveCtr = 0;
        aiPos.clear();
        moveList.clear();
        playerPos.clear();
        gameIsDone = false;
        lblX.setText(null);
        lblO.setText(null);

        for(ImageView iv : ivArray) {
            iv.setImage(null);
            iv.setDisable(true);
        }

        btnClear.setDisable(true);
        btnStart.setDisable(false);
        cbxLevel.setDisable(false);
        aiFirstMove = !aiFirstMove;
        aiTurn = aiFirstMove;

    }

    /**
     * This function starts the game (function not really needed)
     * @param pos the position of the first move (initially set to 0)
     */
    public void startGame (int pos) {
        choosePos(aiTurn, pos);
    }

    /**
     * This function makes the best move for the AI player depending on the level and stores the moves made by the
     * player and AI
     * @param aiTurn the indicator for the current turn (i.e. true if AI's turn, false if player's turn)
     * @param pos the position chosen by the player or AI
     */
    private void choosePos(boolean aiTurn, int pos) {
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
                pos = level1BestMove();
                invalid = false;
            }
            else if(aiDifficulty == 2) {
                pos = level2BestMove();
                invalid = false;
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
            for(ImageView iv : ivArray)
                iv.setDisable(true);
        }
    }

    /**
     * This function if there is a winner for the game
     * @param ifActual specifies if the checking is only for evaluation or not
     * @return 100 if the ai wins, -100 if the player wins, 0 if there is a tie, -20 if game is not over yet
     */
    private int checkWinner (boolean ifActual) { // Checker for winner
        List<List<Integer>> winConditions = new ArrayList<>();

        winConditions.add(Arrays.asList(1, 2, 3)); //top row
        winConditions.add(Arrays.asList(4, 5, 6)); //mid row
        winConditions.add(Arrays.asList(7, 8, 9)); //bottom row
        winConditions.add(Arrays.asList(1, 4, 7)); //left col
        winConditions.add(Arrays.asList(2, 5, 8)); //mid col
        winConditions.add(Arrays.asList(3, 6, 9)); //right col
        winConditions.add(Arrays.asList(7, 5, 3)); //positive diagonal
        winConditions.add(Arrays.asList(1, 5, 9)); //negative diagonal

        for(List<Integer> winCond: winConditions) {
            if(playerPos.containsAll(winCond)) {
                if(ifActual) {
                    gameIsDone = true;
                    moveList.appendText("You are the winner!\n");
                }
                return -100;
            }
            else if(aiPos.containsAll(winCond)) {
                if(ifActual) {
                    gameIsDone = true;
                    moveList.appendText("You are the loser!\n");
                }
                return 100;
            }
        }
        if(aiPos.size() + playerPos.size() == 9 && !gameIsDone) {
            if(ifActual) {
                gameIsDone = true;
                moveList.appendText("It's a tie!\n");
            }
            return 0;
        }
        return -20;
    }

    /**
     * This function updates the gui based on the moves selected by the AI or player
     * @param moveCtr counter for the number of moves made
     * @param pos the position to be updated
     */
    private void setPos (int moveCtr, int pos) { // Position setter based from choosePos
        Image img;

        if(moveCtr % 2 == 0)
            img = imgX;
        else
            img = imgO;

        switch (pos) {
            case 1 -> iv00.setImage(img);
            case 2 -> iv01.setImage(img);
            case 3 -> iv02.setImage(img);
            case 4 -> iv10.setImage(img);
            case 5 -> iv11.setImage(img);
            case 6 -> iv12.setImage(img);
            case 7 -> iv20.setImage(img);
            case 8 -> iv21.setImage(img);
            case 9 -> iv22.setImage(img);
        }
    }

    /**
     * This function checks if the AI or player is one move away from winning (i.e. can win in the next move)
     * @param isAI turn indicator; true if AI's turn, false if player's turn
     * @return -1 if AI or player cannot win in the next move; otherwise, returns the position of the winning move
     */
    private int oneMoveAway(boolean isAI) {
        List<List<Integer>> winConditions = new ArrayList<>();
        winConditions.add(Arrays.asList(1, 2, 3)); //top row
        winConditions.add(Arrays.asList(4, 5, 6)); //mid row
        winConditions.add(Arrays.asList(7, 8, 9)); //bottom row
        winConditions.add(Arrays.asList(1, 4, 7)); //left col
        winConditions.add(Arrays.asList(2, 5, 8)); //mid col
        winConditions.add(Arrays.asList(3, 6, 9)); //right col
        winConditions.add(Arrays.asList(7, 5, 3)); //positive diagonal
        winConditions.add(Arrays.asList(1, 5, 9)); //negative diagonal

        boolean oneMoveAway = false;
        int neededPos = -1;

        for(List<Integer> winCond : winConditions) {
            int count = 0;
            neededPos = -1;
            for(int pos : winCond) {
                if(isAI) {
                    if(aiPos.contains(pos))
                        count++;
                    else
                        neededPos = pos;
                }
                else {
                    if(playerPos.contains(pos))
                        count++;
                    else
                        neededPos = pos;
                }

            }

            if(count == 2) {
                if(isAI) {
                    if(!playerPos.contains(neededPos)) {
                        oneMoveAway = true;
                        break;
                    }
                }
                else {
                    if(!aiPos.contains(neededPos)) {
                        oneMoveAway = true;
                        break;
                    }
                }
            }
        }

        if(oneMoveAway)
            return neededPos;
        else
            return -1;
    }

    /**
     * This function calculates for the best move for the AI. Level 1 uses hard coded rules to find the best move
     * Steps:
     * 1 - Check if AI can win in next move (if yes, take the move; if no, step 2)
     * 2 - Check if player can win in next move (if yes, block; if no, step 3)
     * 3 - Check if blank/available moves <= 2 (if yes, choose randomly; if no, step 4)
     * 4 - Check hard coded states
     *
     * Checking if AI or player can win in the next move effectively eliminates the need to hard code those states
     * Additionally, if available moves <= 2, and both player and AI cannot win in the next move, then all
     * succeeding configurations will result in a tie so no need to hard code those states
     * @return the position of the best move
     */
    private int level1BestMove() {
        //check if AI is one move away from winning, if yes take move to win game, if no check player
        int pos = oneMoveAway(true);
        if(pos != -1)
            return pos;

        //check if player is one move away from winning, if yes block
        pos = oneMoveAway(false);
        if(pos != -1)
            return pos;

        /*if both AI and player cannot win in the next move, check if available moves left is <= 2
        if yes, choose any available position
        if no, check hard coded tables*/
        if(9 - (playerPos.size() + aiPos.size()) <= 2) {
            boolean found = false;
            for(int i = 1; i <= 9 && !found; i++) {
                if(!playerPos.contains(i) && !aiPos.contains(i)) {
                    found = true;
                    pos = i;
                }
            }
            return pos;
        }

        /*-------------------------- HARD CODED TABLES BELOW (refer to level 1 diagram) --------------------------*/
        /*if moveCtr is even, AI goes first; otherwise player goes first
        * moveCtr is also the current depth of the tree*/
        switch(moveCtr) {
            case 0:
                pos = 5;
                break;
            case 1:
                if(!playerPos.contains(5))
                    pos = 5;
                else
                    pos = 1;
                break;
            case 2:
                if(playerPos.contains(1) || playerPos.contains(2))
                    pos = 9;
                else if(playerPos.contains(3) || playerPos.contains(6))
                    pos = 7;
                else if(playerPos.contains(9) || playerPos.contains(8))
                    pos = 1;
                else if(playerPos.contains(7) || playerPos.contains(4))
                    pos = 3;
            case 3:
                if(playerPos.containsAll(Arrays.asList(1,9)) && aiPos.contains(5))
                    pos = 4;
                else if(playerPos.containsAll(Arrays.asList(3,7)) && aiPos.contains(5))
                    pos = 2;
                else if(playerPos.containsAll(Arrays.asList(1,6)) && aiPos.contains(5))
                    pos = 9;
                else if(playerPos.containsAll(Arrays.asList(3,8)) && aiPos.contains(5))
                    pos = 7;
                else if(playerPos.containsAll(Arrays.asList(4,9)) && aiPos.contains(5))
                    pos = 1;
                else if(playerPos.containsAll(Arrays.asList(2,7)) && aiPos.contains(5))
                    pos = 3;
                else if(playerPos.containsAll(Arrays.asList(2,9)) && aiPos.contains(5))
                    pos = 1;
                else if(playerPos.containsAll(Arrays.asList(6,7)) && aiPos.contains(5))
                    pos = 3;
                else if(playerPos.containsAll(Arrays.asList(1,8)) && aiPos.contains(5))
                    pos = 9;
                else if(playerPos.containsAll(Arrays.asList(3,4)) && aiPos.contains(5))
                    pos = 7;
                else if(playerPos.containsAll(Arrays.asList(2,8)) && aiPos.contains(5))
                    pos = 4;
                else if(playerPos.containsAll(Arrays.asList(4,6)) && aiPos.contains(5))
                    pos = 2;
                else if(playerPos.containsAll(Arrays.asList(2,4)) && aiPos.contains(5))
                    pos = 1;
                else if(playerPos.containsAll(Arrays.asList(2,6)) && aiPos.contains(5))
                    pos = 3;
                else if(playerPos.containsAll(Arrays.asList(6,8)) && aiPos.contains(5))
                    pos = 9;
                else if(playerPos.containsAll(Arrays.asList(4,8)) && aiPos.contains(5))
                    pos = 7;
                else if(playerPos.containsAll(Arrays.asList(5,9)) && aiPos.contains(1))
                    pos = 7;
                break;
            case 4:
                if(playerPos.containsAll(Arrays.asList(1,8)) && aiPos.containsAll(Arrays.asList(5, 9)))
                    pos = 6;
                else if(playerPos.containsAll(Arrays.asList(3,4)) && aiPos.containsAll(Arrays.asList(5, 7)))
                    pos = 8;
                else if(playerPos.containsAll(Arrays.asList(2,9)) && aiPos.containsAll(Arrays.asList(5, 1)))
                    pos = 4;
                else if(playerPos.containsAll(Arrays.asList(6,7)) && aiPos.containsAll(Arrays.asList(5, 3)))
                    pos = 2;
                else if(playerPos.containsAll(Arrays.asList(1,6)) && aiPos.containsAll(Arrays.asList(5, 9)))
                    pos = 8;
                else if(playerPos.containsAll(Arrays.asList(3,8)) && aiPos.containsAll(Arrays.asList(5, 7)))
                    pos = 4;
                else if(playerPos.containsAll(Arrays.asList(4,9)) && aiPos.containsAll(Arrays.asList(5, 1)))
                    pos = 2;
                else if(playerPos.containsAll(Arrays.asList(2,7)) && aiPos.containsAll(Arrays.asList(5, 3)))
                    pos = 6;
                break;
            case 5:
                if(playerPos.containsAll(Arrays.asList(1,6,7)) && aiPos.containsAll(Arrays.asList(4,5)))
                    pos = 8;
                else if(playerPos.containsAll(Arrays.asList(1,3,8)) && aiPos.containsAll(Arrays.asList(2,5)))
                    pos = 4;
                else if(playerPos.containsAll(Arrays.asList(3,4,9)) && aiPos.containsAll(Arrays.asList(6,5)))
                    pos = 2;
                else if(playerPos.containsAll(Arrays.asList(2,7,9)) && aiPos.containsAll(Arrays.asList(8,5)))
                    pos = 6;
                else if(playerPos.containsAll(Arrays.asList(1,6,8)) && aiPos.containsAll(Arrays.asList(9,5)))
                    pos = 3;
                else if(playerPos.containsAll(Arrays.asList(3,4,8)) && aiPos.containsAll(Arrays.asList(7,5)))
                    pos = 9;
                else if(playerPos.containsAll(Arrays.asList(2,4,9)) && aiPos.containsAll(Arrays.asList(1,5)))
                    pos = 7;
                else if(playerPos.containsAll(Arrays.asList(2,6,7)) && aiPos.containsAll(Arrays.asList(3,5)))
                    pos = 1;
                else if(playerPos.containsAll(Arrays.asList(2,6,8)) && aiPos.containsAll(Arrays.asList(4,5)))
                    pos = 7;
                else if(playerPos.containsAll(Arrays.asList(4,6,8)) && aiPos.containsAll(Arrays.asList(2,5)))
                    pos = 1;
                else if(playerPos.containsAll(Arrays.asList(2,4,8)) && aiPos.containsAll(Arrays.asList(6,5)))
                    pos = 3;
                else if(playerPos.containsAll(Arrays.asList(2,4,6)) && aiPos.containsAll(Arrays.asList(8,5)))
                    pos = 9;
                else if(playerPos.containsAll(Arrays.asList(4,5,9)) && aiPos.containsAll(Arrays.asList(1,6)))
                    pos = 3;
                else if(playerPos.containsAll(Arrays.asList(2,5,7)) && aiPos.containsAll(Arrays.asList(3,8)))
                    pos = 9;
                else if(playerPos.containsAll(Arrays.asList(1,5,6)) && aiPos.containsAll(Arrays.asList(4,9)))
                    pos = 7;
                else if(playerPos.containsAll(Arrays.asList(3,5,8)) && aiPos.containsAll(Arrays.asList(2,7)))
                    pos = 1;
                else if(playerPos.containsAll(Arrays.asList(5,6,7)) && aiPos.containsAll(Arrays.asList(3,4)))
                    pos = 1;
                else if(playerPos.containsAll(Arrays.asList(1,5,8)) && aiPos.containsAll(Arrays.asList(2,9)))
                    pos = 3;
                else if(playerPos.containsAll(Arrays.asList(3,4,5)) && aiPos.containsAll(Arrays.asList(6,7)))
                    pos = 9;
                else if(playerPos.containsAll(Arrays.asList(2,5,9)) && aiPos.containsAll(Arrays.asList(8,1)))
                    pos = 7;
                break;
            case 6:
                //at this point it doesnt matter which position is chosen so choose any blank tile
                boolean found = false;
                for(int i = 1; i <= 9 && !found; i++) {
                    if(!playerPos.contains(i) && !aiPos.contains(i)) {
                        found = true;
                        pos = i;
                    }
                }
                break;
        }

        //additional checker only, in case something goes wrong with the above
        if(pos <= 0) {
            boolean found = false;
            for(int i = 1; i <= 9 && !found; i++) {
                if(!playerPos.contains(i) && !aiPos.contains(i)) {
                    found = true;
                    pos = i;
                }
            }
        }

        return pos;
    }

    /**
     * This function calculates for the best move for the AI. Level 2 is unbeatable because it generates the whole
     * game tree in order to determine the best move.
     * @return the position of the best move
     */
    private int level2BestMove() {
        int bestScore = -1000;
        int bestMove = -1;

        for(int i = 1; i <= 9; i++) {
            if(!aiPos.contains(i) && !playerPos.contains(i)) {
                aiPos.add(i); //make the move
                int currScore = level2Minimax(false, -10000, 10000, 1);
                aiPos.remove((Integer) i); //undo the move

                if(currScore > bestScore) {
                    bestScore = currScore;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    /**
     * This function calculates for the move which will result in the highest evaluation function using the minimax
     * algorithm with alpha beta pruning.
     * Level 2 evaluation function checks the terminal states and returns a corresponding value if the game
     * results in the AI winning, the player winning or a tie.
     * Heuristics: H(n) = S(n) +- depth where S(n) is the score and depth is the depth of the terminal node
     * 100 - depth if AI wins
     * -100 + depth if player wins
     * 0 if tie.
     * Prioritizes winning moves with least depth (shorten game) while prioritizes losing moves with highest depth (prolongs game)
     * @param isAI represents the current turn
     * @param alpha the max value found
     * @param beta the min value found
     * @param depth the current depth of the tree
     * @return the score of the terminal state with the best score
     */
    private int level2Minimax (boolean isAI, int alpha, int beta, int depth) {
        int bestScore;
        int currScore = checkWinner(false);

        //check if the terminal node has been reached (i.e. game is over)
        if(currScore == 100)
            return currScore - depth;
        else if(currScore == -100)
            return currScore + depth;
        else if(currScore == 0)
            return currScore;

        if(isAI){
            bestScore = -1000;
            for(int i = 1; i <= 9; i++) {
                if(!aiPos.contains(i) && !playerPos.contains(i)) {
                    aiPos.add(i); //make the move
                    bestScore = Math.max(bestScore, level2Minimax(!isAI, alpha, beta, depth + 1));
                    alpha = Math.max(alpha, bestScore);
                    aiPos.remove((Integer) i); //undo the move
                }
                if(alpha >= beta)
                    break;
            }
        }
        else {
            bestScore = 1000;
            for(int i = 1; i <= 9; i++) {
                if(!aiPos.contains(i) && !playerPos.contains(i)) {
                    playerPos.add(i); //make the move
                    bestScore = Math.min(bestScore, level2Minimax(!isAI, alpha, beta, depth + 1));
                    beta = Math.min(beta, bestScore);
                    playerPos.remove((Integer) i); //undo the move
                }
                if(alpha >= beta)
                    break;
            }
        }
        return bestScore;
    }
}
