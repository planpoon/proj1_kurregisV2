package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import sun.plugin2.util.ColorUtil;

import java.sql.*;

public class SubjectInfoPageController {
    @FXML private Label nameLabel, IDLabel, creditLabel, isPassLabel, prerequireLabel, diffLvlLabel;

    public void initData(String subjectID){
        Connection connection = null;
        Statement statement = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:subjectInfoDB.db");
            System.out.println("init Data");
            statement = connection.createStatement();
            String sqlCommand = String.format("SELECT NAME,CREDIT,PREREQUIRE,DIFFCULTLEVEL FROM subjectInfoTable WHERE ID="+ subjectID);
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            nameLabel.setText(resultSet.getString("NAME"));
            IDLabel.setText(subjectID);
            creditLabel.setText(Integer.toString(resultSet.getInt("CREDIT")));

//            connection.close();
//            statement.close();
//            resultSet.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } catch (SQLException e) {
            e.printStackTrace();
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    public void initData(String subjectID,String subjectName,int credit,String preRequire,boolean isPassed,int difficultLevel){
        nameLabel.setText(subjectName);
        IDLabel.setText(subjectID);
        creditLabel.setText(Integer.toString(credit));
        prerequireLabel.setText(preRequire);
        if (difficultLevel == 1) {
            diffLvlLabel.setText("Easy");
            diffLvlLabel.setTextFill(Color.GREEN);
        }
        if (difficultLevel == 2) {
            diffLvlLabel.setText("Normal");
            diffLvlLabel.setTextFill(Color.ORANGE);
        }if (difficultLevel == 3){
            diffLvlLabel.setText("Hard");
            diffLvlLabel.setTextFill(Color.RED);
        }
        if (isPassed){
            isPassLabel.setText("PASSED");
            isPassLabel.setStyle("-fx-background-color: #00FF00");
        }else{
            isPassLabel.setText("FAILED");
            isPassLabel.setStyle("-fx-background-color: #FF0000");
        }


    }
}
