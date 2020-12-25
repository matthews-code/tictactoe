import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        //primaryStage.show();

        //controller.gameInit();
        controller.startGame(1);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
