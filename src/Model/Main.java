package Model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../View/menuPage.fxml"));
            primaryStage.setTitle("KU regis Menu");
            primaryStage.setScene(new Scene(root, 625, 400));
            primaryStage.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/instructionPage.fxml"));
            Parent rootLoader = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Instruction ");
            stage.setScene(new Scene(rootLoader, 500, 300));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
