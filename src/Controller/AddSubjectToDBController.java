package Controller;

import Model.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddSubjectToDBController {
    @FXML private TextField nameTextField,IDTextField,creditTextField,prerequireTextField;
    @FXML private ComboBox<Integer> semCmb;
    @FXML private ComboBox<String> diffLvlCmb;
    @FXML private Button backBtn,confirmBtn;
    @FXML private FirstPageController firstPageController = null;
    private DBConnector dbConnector = new DBConnector();
    private boolean editMode = false;
    private String oldID;

    public void initialize(){
        semCmb.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8);
        diffLvlCmb.getItems().addAll("Easy", "Normal", "Hard");
    }

    public void initData(String id,String subjectName,int credit,String preReq,int sem,String diffLvl) {
        oldID = id;
        nameTextField.setText(subjectName);
        IDTextField.setText(id);
        creditTextField.setText(credit + "");
        prerequireTextField.setText(preReq);
        semCmb.getSelectionModel().select(sem - 1);
        diffLvlCmb.getSelectionModel().select(diffLvl);
    }

    @FXML
    private void handlerBackBtn(ActionEvent event){
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/firstPage.fxml"));
            stage.setScene(new Scene(loader.load(), 625, 400));
            firstPageController = loader.getController();
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerConfirmBtn(ActionEvent event) {
        Optional<ButtonType> result;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        if (editMode) {
            alert.setContentText("Are you sure to edit subject");
            result = alert.showAndWait();
            if (result.isPresent() && result.get().equals(ButtonType.OK)) {
                if (checkTextFieldCondition()) {
                    dbConnector.updateEditedSubject(oldID, IDTextField.getText(),
                            nameTextField.getText(), Integer.parseInt(creditTextField.getText()),
                            prerequireTextField.getText(), semCmb.getSelectionModel().getSelectedItem(),
                            diffLvlCmb.getSelectionModel().getSelectedItem());
                }
            }
        }else{
            alert.setContentText("Are you sure to add subject");
            result = alert.showAndWait();
            if (result.isPresent() && result.get().equals(ButtonType.OK)) {
                if (checkTextFieldCondition()){
                    if (prerequireTextField.getText().isEmpty()) {
//                        int diffLvl = 0;
//                        if (diffLvlCmb.getSelectionModel().getSelectedItem().equals("Easy")) {
//                            diffLvl = 1;
//                        } else if (diffLvlCmb.getSelectionModel().getSelectedItem().equals("Normal")) {
//                            diffLvl = 2;
//                        } else if (diffLvlCmb.getSelectionModel().getSelectedItem().equals("Hard")) {
//                            diffLvl = 3;
//                        }
                        dbConnector.addSubject(IDTextField.getText(), nameTextField.getText(), Integer.parseInt(creditTextField.getText()), semCmb.getSelectionModel().getSelectedItem(), diffLvlCmb.getSelectionModel().getSelectedItem());
                        nameTextField.setText("");
                        IDTextField.setText("");
                        creditTextField.setText("");
                        prerequireTextField.setText("");
                        semCmb.getSelectionModel().select(null);
                        diffLvlCmb.getSelectionModel().select(null);
                    }else{
                        dbConnector.addSubject(IDTextField.getText(), nameTextField.getText(), Integer.parseInt(creditTextField.getText()), semCmb.getSelectionModel().getSelectedItem(), prerequireTextField.getText(), diffLvlCmb.getSelectionModel().getSelectedItem());
                        nameTextField.setText("");
                        IDTextField.setText("");
                        creditTextField.setText("");
                        prerequireTextField.setText("");
                        semCmb.getSelectionModel().select(null);
                        diffLvlCmb.getSelectionModel().select(null);
                    }
                }
            }
        }

    }

    private boolean checkTextFieldCondition(){
        if (nameTextField.getText().isEmpty() || IDTextField.getText().isEmpty() || creditTextField.getText().isEmpty() || semCmb.getSelectionModel().getSelectedItem() == null || diffLvlCmb.getSelectionModel().getSelectedItem() == null) {
            if (nameTextField.getText().isEmpty()) {
                nameTextField.setStyle("-fx-background-color: #FF0000;");
            }
            if (!nameTextField.getText().isEmpty()) {
                nameTextField.setStyle("");
            }
            if (IDTextField.getText().isEmpty()) {
                IDTextField.setStyle("-fx-background-color: #FF0000;");
            }
            if (!IDTextField.getText().isEmpty()) {
                IDTextField.setStyle("");
            }
            if (creditTextField.getText().isEmpty()) {
                creditTextField.setStyle("-fx-background-color: #FF0000;");
            }
            if (!creditTextField.getText().isEmpty()) {
                creditTextField.setStyle("");
            }
            if (semCmb.getSelectionModel().getSelectedItem() == null) {
                semCmb.setStyle("-fx-background-color: #FF0000;");
            }
            if (!(semCmb.getSelectionModel().getSelectedItem() == null)) {
                semCmb.setStyle("");
            }
            if (diffLvlCmb.getSelectionModel().getSelectedItem() == null) {
                diffLvlCmb.setStyle("-fx-background-color: #FF0000;");
            }
            if (!(diffLvlCmb.getSelectionModel().getSelectedItem() == null)) {
                diffLvlCmb.setStyle("");
            }
            return false;
        } else {
            nameTextField.setStyle("");
            IDTextField.setStyle("");
            creditTextField.setStyle("");
            semCmb.setStyle("");
            diffLvlCmb.setStyle("");
            return true;
        }
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}
