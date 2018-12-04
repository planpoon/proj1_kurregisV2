package Controller;

import Model.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckRegisPageController {
    @FXML private List<Button> buttonList = new ArrayList<>();
    @FXML private AnchorPane anchorPane = new AnchorPane();
    @FXML private FirstPageController firstPageController;
    @FXML private Button backBtn;
    private DBConnector dbConnector = new DBConnector();
    private int buttonNumber = 0;
    private SubjectInfoPageController subjectInfoPageController = new SubjectInfoPageController();
    private String[][] subjectInfoArr = new String[dbConnector.countAllRow()][6];



    @FXML
    public void initialize() {
        dbConnector.generateButton(this.buttonList);
        dbConnector.generateSubjectInfoArr(this.subjectInfoArr);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        try {
            System.out.println("initialize");

            //group subject by semester
            for (int semester = 1; semester <= 8; semester++) {
                //x and y are coordinate of each button that contain subject
                //x Axis control by semester
                int xValue = ((semester - 1) * 300) + 35;

                ArrayList<String> subjectIDArr = new ArrayList<>();
                ArrayList<String> subjectNameArr = new ArrayList<>();
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:subjectInfoDB.db");
                statement = connection.createStatement();
                String sqlCommand = String.format("SELECT ID,NAME,CREDIT,PREREQUIRE,ISPASSED,DIFFICULTLEVEL FROM subjectInfoTable WHERE SEM=%d", semester);
                resultSet1= statement.executeQuery(sqlCommand);

                while (resultSet1.next()) {
                    subjectNameArr.add(resultSet1.getString("NAME"));
                    subjectIDArr.add(resultSet1.getString("ID"));
                }

                //generate button by semester
                for (int subject = 1; subject <= subjectNameArr.size(); subject++) {
                    //y Axis control by each subject
                    int yValue = (40*subject)+20;
                    anchorPane.getChildren().add(buttonList.get(buttonNumber));
                    buttonList.get(buttonNumber).setLayoutX(xValue);
                    buttonList.get(buttonNumber).setLayoutY(yValue);
                    buttonList.get(buttonNumber).setText(subjectNameArr.get(subject-1));
                    buttonNumber++;
                }

                /*binding mouseEventHandler to each button
                left click(MouseButton.PRIMARY) mean mark that you pass this subject
                right click(MouseButton.SECONDARY) mean get information of subject*/
                for (int index = 0; index < buttonList.size(); index++) {
                    final int buttonNum = index;
                    buttonList.get(index).setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                            if (buttonList.get(buttonNum).getStyle().equals("")) {
                                buttonList.get(buttonNum).setStyle("-fx-background-color: #00FF00;");
                                subjectInfoArr[buttonNum][4] = "1";
                                for (int i = 0; i <subjectInfoArr.length ; i++) {
                                    if (subjectInfoArr[i][3].contains(subjectInfoArr[buttonNum][0])) {
                                        buttonList.get(i).setDisable(false);
                                    }
                                }
                            }else{
                                buttonList.get(buttonNum).setStyle("");
                                subjectInfoArr[buttonNum][4] = "0";
                                for (int i = 0; i <subjectInfoArr.length ; i++) {
                                    if (subjectInfoArr[i][3].contains(subjectInfoArr[buttonNum][0])) {
                                        buttonList.get(i).setDisable(true);
                                    }
                                }
                            }
                        }

                        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                            //index0 = id ind1 = name  ind2 = credit  ind3 = prerequire ind4 = ispassed ind5 = DIFFICULTLEVEL
                            passingSubjectData(subjectInfoArr[buttonNum][0]
                                    , subjectInfoArr[buttonNum][1]
                                    , Integer.parseInt(subjectInfoArr[buttonNum][2])
                                    , subjectInfoArr[buttonNum][3]
                                    , Integer.parseInt(subjectInfoArr[buttonNum][4])
                                    , Integer.parseInt(subjectInfoArr[buttonNum][5])
                            );
                        }
                    });

                }

            }
            /*disable button that have prerequire subject
            * by create preRequireSubject Arraylist
            * and disable button that in preRequireSubject Arraylist*/
            ArrayList<String> preRequireSubjectArr = new ArrayList<>();
            resultSet2 = statement.executeQuery("SELECT ID FROM subjectInfoTable WHERE PREREQUIRE notnull");
            while (resultSet2.next()) {
                preRequireSubjectArr.add(resultSet2.getString("ID"));
            }
            for (int index = 0; index <subjectInfoArr.length ; index++) {
                for (String element : preRequireSubjectArr) {
                    if (subjectInfoArr[index][0].equals(element)) {
                        buttonList.get(index).setDisable(true);
                    }
                }
            }
            if (!resultSet1.isClosed()) {
                resultSet1.close();
            }
            if (!resultSet2.isClosed()) {
                resultSet2.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
            if (!statement.isClosed()) {
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getStackTrace()[0].getLineNumber());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getStackTrace()[0].getLineNumber());
        }
    }

    private void passingSubjectData(String subjectID,String subjectName,int credit,String preRequire,int isPassed,int difficultLevel){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/subjectInfoPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Subject information ");
            stage.setScene(new Scene(root, 500, 300));
            SubjectInfoPageController subjectInfoPageController = loader.getController();
            subjectInfoPageController.initData(subjectID,subjectName,credit,preRequire,isPassed,difficultLevel);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
