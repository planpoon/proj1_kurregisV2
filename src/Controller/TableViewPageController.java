package Controller;

import Model.DBConnector;
import Model.Subject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class TableViewPageController {
    @FXML private TableView<Subject> tableView;
    @FXML private Button backBtn, delBtn, editBtn;
    @FXML private TableColumn<Subject, String> idTC, nameTC, preReqTC, diffLvlTC;
    @FXML private TableColumn<Subject, Integer> creditTC, passStatTC ,semTC;

    private AddSubjectToDBController addSubjectToDBController;
    private FirstPageController firstPageController;
    private ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();
    private DBConnector dbConnector = new DBConnector();

    public void initialize() {
        editBtn.setDisable(true);
        delBtn.setDisable(true);
        subjectObservableList = dbConnector.getObservableList();
        tableView.setItems(null);
        tableView.setItems(subjectObservableList);
        diffLvlTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDifficultLvl()));
        diffLvlTC.setCellFactory(column -> new TableCell<Subject, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setText(null);
                    setStyle("");
                }else {
                    setText(item);
                    setTextFill(Color.WHITE);
                    if (item.equalsIgnoreCase("Hard")) {
                        setStyle("-fx-background-color: red");
                    }else if(item.equalsIgnoreCase("Normal")){
                        setStyle("-fx-background-color: blue");
                    }else if(item.equalsIgnoreCase("Easy")){
                        setStyle("-fx-background-color: green");
                    }
                }
            }

        });
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    if (tableView.getSelectionModel().getSelectedItem() != null) {
                        editBtn.setDisable(false);
                        delBtn.setDisable(false);
                    }
                }
            }
        });
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
