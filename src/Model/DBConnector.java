package Model;


import Controller.SubjectInfoPageController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DBConnector {
    private String classForName = "org.sqlite.JDBC";
    private String dbUrl = "jdbc:sqlite:subjectInfoDB.db";
    private Connection connection = null;
    private Statement statement = null;
    private ObservableList<Subject> observableList = FXCollections.observableArrayList();

    public void addSubject(String ID, String subjectName, int credit, int sem,int difficultLevel) {
        try {
            Class.forName(classForName);
            connection = DriverManager.getConnection(dbUrl);
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            String sql = "INSERT INTO subjectInfoTable (ID,NAME,CREDIT,PREREQUIRE,SEM,DIFFICULTLEVEL) " + "VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            preparedStatement.setString(2, subjectName);
            preparedStatement.setInt(3, credit);
            preparedStatement.setInt(5, sem);
            preparedStatement.setInt(6,difficultLevel);

            preparedStatement.executeUpdate();

            statement.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSubject(String ID, String subjectName, int credit, int sem, String preReSub,int difficultLevel) {
        try {
            Class.forName(classForName);
            connection = DriverManager.getConnection(dbUrl);
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            String sql = "INSERT INTO subjectInfoTable (ID,NAME,CREDIT,PREREQUIRE,SEM,DIFFICULTLEVEL) " + "VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            preparedStatement.setString(2, subjectName);
            preparedStatement.setInt(3, credit);
            preparedStatement.setString(4, preReSub);
            preparedStatement.setInt(5, sem);
            preparedStatement.setInt(6,difficultLevel);
            preparedStatement.executeUpdate();

            statement.close();
            connection.commit();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void passingSubjectData(String subjectID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/subjectInfoPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Subject information ");
            stage.setScene(new Scene(root, 500, 300));
            SubjectInfoPageController subjectInfoPageController = loader.getController();
            subjectInfoPageController.initData(subjectID);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void generateButton(List<Button> buttonList) {
        int count = 0;
        try {
            Class.forName(classForName);
            connection = DriverManager.getConnection(dbUrl);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM subjectInfoTable");
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            for (int i = 0; i < count; i++) {
                buttonList.add(new Button("button" + i));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getStackTrace()[0].getLineNumber());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getStackTrace()[0].getLineNumber());
        }
    }
    public int countAllRow() {
        int count = 0;
        try {
            Class.forName(classForName);
            connection = DriverManager.getConnection(dbUrl);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM subjectInfoTable");
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getStackTrace()[0].getLineNumber());
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getStackTrace()[0].getLineNumber());
        }
        return count;
    }

    public void generateSubjectInfoArr(String[][] targetArr) {
        int index = 0;
        try {
            Class.forName(classForName);
            connection = DriverManager.getConnection(dbUrl);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID,NAME,CREDIT,PREREQUIRE,DIFFICULTLEVEL FROM subjectInfoTable ORDER BY SEM ASC");
            while (resultSet.next()) {
                if (index < targetArr.length) {
                    targetArr[index][0] = resultSet.getString("ID");
                    targetArr[index][1] = resultSet.getString("NAME");
                    targetArr[index][2] = resultSet.getString("CREDIT");
                    if (resultSet.getString("PREREQUIRE") == null){
                        targetArr[index][3] = "";
                    }else {
                        targetArr[index][3] = resultSet.getString("PREREQUIRE");
                    }
                    targetArr[index][4] = resultSet.getInt("DIFFICULTLEVEL")+"";
                    index++;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList getObservableList(){
        try {
            Class.forName(classForName);
            connection = DriverManager.getConnection(dbUrl);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM subjectInfoTable");
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("NAME");
                int credit = resultSet.getInt("CREDIT");
                String preReq = resultSet.getString("PREREQUIRE");
                int sem = resultSet.getInt("SEM");
                int diffLvl = resultSet.getInt("DIFFICULTLEVEL");
                observableList.add(new Subject(id, name, credit, preReq, sem, diffLvl));
            }
            if (!connection.isClosed()) {
                connection.close();
            }
            return observableList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return observableList;
    }

    public void deleteSubject(String id) {
        try {
            Class.forName(classForName);
            connection = DriverManager.getConnection(dbUrl);
            statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM subjectInfoTable WHERE ID=" + id);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEditedSubject(String oldID, String newID, String newName, int credit, String preRequire, int sem, int diffLvl) {
        try {
            Class.forName(classForName);
            connection = DriverManager.getConnection(dbUrl);
            statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE subjectInfoTable SET ID=" + "\"" + newID + "\"" +
                    ",NAME=" + "\"" + newName + "\"" + ",CREDIT=" + credit + ",PREREQUIRE=" + "\"" + preRequire + "\"" + ",SEM=" + sem +
                    ",DIFFICULTLEVEL=" + diffLvl + " WHERE ID=" + oldID);
            preparedStatement.executeUpdate();

            if (!statement.isClosed()) {
                statement.close();
            }
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
