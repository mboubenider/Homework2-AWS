package sample;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.UUID;

public class Controller implements Initializable{
    private ListView<Student> studentListView;
    @FXML
    Button createbutton;
    @FXML
    Button deletebutton;
    @FXML
    Button loadbutton;
    @FXML
    JFXButton gpabutton;
    @FXML
    JFXButton majorbutton;
    @FXML
    JFXButton agebutton;


    final String hostname = "student-db.c5cfwou0gygn.us-east-1.rds.amazonaws.com";
    final String dbName = "student_db";
    final String port = "3306";
    final String userName = "mlbouben";
    final String password = "Mlb1399!";
    final String AWS_URL = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;

    private void createDatabase(String url){
        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            try{
                s.execute("CREATE TABLE Student("+
                        "Fname CHAR(25),"+
                        "Lname CHAR(25),"+
                        "major CHAR(24)," +
                        "age VARCHAR(36)"+
                        "gpa VARCHAR(36)"+
                        "id VARCHAR(36)");
                System.out.println("TABLE CREATED");
            }
            catch(Exception ex){
                System.out.println("Table already exists, not created");
            }
            UUID id = UUID.randomUUID();
            String idString = id.toString();
            String Fname = url.equals(AWS_URL) ? "Clark" : "Super";
            String Lname = url.equals(AWS_URL) ? "Clark" : "Super";
            String major = url.equals(AWS_URL) ? "Clark" : "Super";
            String age = url.equals(AWS_URL) ? "Clark" : "Super";
            String gpa = url.equals(AWS_URL) ? "Clark" : "Super";

            String sql = "INSERT INTO Student VALUES" +
                    "('" + Fname + "', '" +Lname + "', '" + major + "','" + age + "','" + gpa + "', '" + Fname + "','" + idString +"', TRUE)";
            s.executeUpdate(sql);
            System.out.println("TABLE FILLED");

            s.close();
            c.close();
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            System.out.println(msg);
        }
    }
    private void deleteDatabase(String url){
        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            s.execute("DROP TABLE Student");
            s.close();
            c.close();
            System.out.println("TABLE DROPPED");
        }
        catch(Exception ex){
            String msg = ex.getMessage();
            System.out.println("TABLE NOT DROPPED");
            System.out.println(msg);
        }
    }
    private void loadDatabase(String url){
        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            String sqlStatement = "SELECT Fname, Lname, major, age, gpa, id FROM Student";
            ResultSet result = s.executeQuery(sqlStatement);
            ObservableList<Student> dbStudentList = FXCollections.observableArrayList();

            System.out.println("DATA LOADED");
            s.close();
            c.close();
        }
        catch (Exception ex){
            String msg = ex.getMessage();
            System.out.println("DATA NOT LOADED");
            System.out.println(msg);
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle){
        createbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                createDatabase(AWS_URL);
            }
        });
        deletebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteDatabase(AWS_URL);
            }
        });
        loadbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loadDatabase(AWS_URL);
            }
        });

    }
}
