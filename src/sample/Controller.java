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
    @FXML
    private ListView<Student> studentlistView;
    @FXML
    private ListView<Student> filterlistView;
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
                s.execute("CREATE TABLE Student ("+
                        "Fname CHAR(25), "+
                        "Lname CHAR(25), "+
                        "major CHAR(24), " +
                        "age INT,"+
                        "gpa FLOAT (36),"+
                        "id VARCHAR(36))");
                System.out.println("TABLE CREATED");
            }
            catch(Exception ex){
                System.out.println("Table already exists, not created");
            }
            UUID id = UUID.randomUUID();
            String idString = id.toString();

            String sql = "INSERT INTO Student VALUES" +
                    "('Jane', 'Doe','Computer Science',18,3.6,'" + idString+"')";
            s.executeUpdate(sql);

            String sql2 = "INSERT INTO Student VALUES" +
                    "('Marc', 'Jacobs','Biology',23, 3.1,'" + idString+"')";
            s.executeUpdate(sql2);

            String sql3 = "INSERT INTO Student VALUES" +
                    "('Bruce', 'Wayne','Chemistry',20, 2.1,'" + idString+"')";
            s.executeUpdate(sql3);

            String sql4 = "INSERT INTO Student VALUES" +
                    "('Alexander', 'Wang','Biology',21, 2.7,'" + idString+"')";
            s.executeUpdate(sql4);
            String sql5 = "INSERT INTO Student VALUES" +
                    "('Yves', 'St.Laurent','Graphic Design',19, 3.0,'" + idString+"')";
            s.executeUpdate(sql5);
            String sql6 = "INSERT INTO Student VALUES" +
                    "('Bella', 'Hadid','Business',21, 3.3,'" + idString+"')";
            s.executeUpdate(sql6);
            String sql7 = "INSERT INTO Student VALUES" +
                    "('Gigi', 'Hadid','Undecided',18, 3.0,'" + idString+"')";
            s.executeUpdate(sql7);
            String sql8 = "INSERT INTO Student VALUES" +
                    "('Madison', 'Boubenider','CIS',21, 3.4,'" + idString+"')";
            s.executeUpdate(sql8);
            String sql9 = "INSERT INTO Student VALUES" +
                    "('John', 'Doe','Biology',20, 2.9,'" + idString+"')";
            s.executeUpdate(sql9);

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
    ObservableList<Student> dbStudentList = FXCollections.observableArrayList();
    ObservableList<Student> filteredList = FXCollections.observableArrayList();

    private void loadDatabase(String url){
        try{
            Connection c = DriverManager.getConnection(url);
            Statement s = c.createStatement();
            String sqlStatement = "SELECT Fname, Lname, major, age, gpa, id FROM Student";
            ResultSet result = s.executeQuery(sqlStatement);

            while (result.next()){
                Student stu = new Student();
                stu.Fname = result.getString("Fname");
                stu.Lname = result.getString("Lname");
                stu.major = result.getString("major");
                stu.age   = result.getInt("age");
                stu.gpa   = result.getFloat("gpa");
                stu.id    = UUID.fromString(result.getString("id"));
                dbStudentList.add(stu);
            }
            studentlistView.setItems(dbStudentList);

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
            public void handle(ActionEvent actionEvent) { createDatabase(AWS_URL); }
        });
        deletebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { deleteDatabase(AWS_URL); }
        });
        loadbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { loadDatabase(AWS_URL); }
        });
        gpabutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                filterlistView.getItems().clear();
                dbStudentList.forEach(student -> {
                    if (student.getGpa() >=3.0) {
                        filteredList.add(student);
                    }
                    filterlistView.setItems(filteredList);
                });

            }
        });
        agebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                filterlistView.getItems().clear();
                dbStudentList.forEach(student -> {
                    if (student.getAge() >= 21) {
                        filteredList.add(student);
                    }
                    filterlistView.setItems(filteredList);
                });
            }
        });
        majorbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("majorbutton worked");
                filterlistView.getItems().clear();
                dbStudentList.forEach(student -> {
                    System.out.println(student.getMajor());
                    if (student.getMajor().equals("Biology")) {
                        System.out.println(student.getMajor());
                        filteredList.add(student);
                    }
                    filterlistView.setItems(filteredList);
                });
            }
        });


    }
}
