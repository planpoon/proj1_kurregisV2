package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {
    @FXML private Button addBtn,viewAllSubjectBtn,regisCheckBtn, instrucBtn;
    @FXML private CheckRegisPageController checkRegisPageController;
    @FXML private AddSubjectToDBController addSubjectToDBController;
    @FXML private TableViewPageController tableViewPageController;
    @FXML private InstructionPageController instructionPageController;

    @FXML
    private void handlerRegisCheckBtn(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/checkRegisPage.fxml"));
            stage.setScene(new Scene(loader.load(), 515, 400));
            checkRegisPageController = loader.getController();
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerAddBtn(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/addSubjectToDB.fxml"));
            stage.setScene(new Scene(loader.load(), 600, 400));
            addSubjectToDBController = loader.getController();
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerInstructionBtn(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/instructionPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Subject information ");
            stage.setScene(new Scene(root, 500, 300));
            instructionPageController = loader.getController();
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerGoToEditOrDelPage(ActionEvent event) {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tableViewPage.fxml"));
            stage.setScene(new Scene(loader.load(), 900, 400));
            tableViewPageController = loader.getController();
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
