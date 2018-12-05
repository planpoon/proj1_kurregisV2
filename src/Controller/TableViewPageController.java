package Controller;

import Model.DBConnector;
import Model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class TableViewPageController {
    @FXML private TableView<Subject> tableView;
    @FXML private Button backBtn, delBtn;
    @FXML private TableColumn<Subject, String> idTC, nameTC, preReqTC;
    @FXML private TableColumn<Subject, Integer> creditTC, diffLvlTC, passStatTC ,semTC;

    private AddSubjectToDBController addSubjectToDBController;
    private FirstPageController firstPageController;
    private ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();
    private DBConnector dbConnector = new DBConnector();

    public void initialize() {
        subjectObservableList = dbConnector.getObservableList();
        tableView.setItems(null);
        tableView.setItems(subjectObservableList);
    }

    @FXML
    private void handlerDelBtn(ActionEvent event) {
        Subject selectedSubject = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure to delete subject");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {
            if (selectedSubject != null) {
                dbConnector.deleteSubject(selectedSubject.getId());
                subjectObservableList.clear();
                subjectObservableList = dbConnector.getObservableList();
                tableView.setItems(subjectObservableList);
                tableView.refresh();
            }
        }
    }

    @FXML
    private void handlerEditBtn(ActionEvent event) {
        Subject selectedSubject = tableView.getSelectionModel().getSelectedItem();
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/addSubjectToDB.fxml"));
            stage.setScene(new Scene(loader.load(), 625, 400));
            addSubjectToDBController = loader.getController();
            addSubjectToDBController.initData(selectedSubject.getId(), selectedSubject.getSubjectName(),
                    selectedSubject.getCredit(), selectedSubject.getPreRequire(),
                    selectedSubject.getSem(), selectedSubject.getDifficultLvl());
            addSubjectToDBController.setEditMode(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerBackBtn(ActionEvent event) {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/firstPage.fxml"));
            stage.setScene(new Scene(loader.load(), 625, 400));
            firstPageController = loader.getController();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
