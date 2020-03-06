package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import dbUtil.dbConnection; //se la classe appartiene ad un package diverso (dbUtil) va sempre importata
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;

public class AdminController implements Initializable {
    @FXML
    private TextField id;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;
    @FXML
    private TableView<StudentData> studentable;

    /*componenti della tabella studenti*/
    @FXML
    private TableColumn<StudentData, String> idcolumn;
    @FXML
    private TableColumn<StudentData, String> firstnamecolumn;
    @FXML
    private TableColumn<StudentData, String> lastnamecolumn;
    @FXML
    private TableColumn<StudentData, String> emailcolumn;
    @FXML
    private TableColumn<StudentData, String> datecolumn;

    private dbConnection dc;
    private ObservableList<StudentData> data;

    private String sql = "SELECT * FROM students";

    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new dbConnection(); /*quando la finestra si apre, viene automaticamente connesso il database! */
    }

    @FXML
    private void loadStudentData(ActionEvent event) {
        try {
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM students");
            while (rs.next()) {
                this.data.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            System.err.println("Error " + e);
        }
        this.idcolumn.setCellValueFactory(new PropertyValueFactory("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory("firstName"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory("email"));
        this.datecolumn.setCellValueFactory(new PropertyValueFactory("DOB"));

        this.studentable.setItems(null);
        this.studentable.setItems(this.data);
    }

    @FXML
    private void addStudent(ActionEvent event) {
        String sqlinsert = "INSERT INTO students(id,firstname,lastname,email,birthdate) VALUES (?,?,?,?,?)";
        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlinsert);
            stmt.setString(1, this.id.getText());
            stmt.setString(2, this.firstname.getText());
            stmt.setString(3, this.lastname.getText());
            stmt.setString(4, this.email.getText());
            stmt.setString(5, this.dob.getEditor().getText());

            stmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void clearAll(ActionEvent event){
        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);

    }
}
