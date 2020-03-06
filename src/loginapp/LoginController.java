package loginapp;

import Admin.AdminController;
import Students.StudentsController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*
 * Dove è evocata questa classe? Nel file login.xml settando il controller all'interno dell'AnchorPanel!
 * Con il codice  fx:controller="loginapp.LoginController" dentro i tag
 */

public class LoginController implements Initializable {
    LoginModel loginModel = new LoginModel(); //istanza della classe loginModel che gestisce la connessione al database per il login

    //accediamo ai componenti grafici del file login.fxml attraverso gli fx:ID che abbiamo impostato tramite scenebuilder. li dichiariamo qui
    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField passwrd;
    @FXML
    private ComboBox<options> combobox;
    @FXML
    private Button loginbutton;
    @FXML
    private Label credentials;

    public void initialize(URL url, ResourceBundle rb) {
        /*
         * MODIFICA LABEL, in base alla riuscita o fallita connessione al server
         *   se la connesione col server è riuscita (cioè se isDatabaseConnected restituisce true) allora setta il testo della Label a "connesso"
         * */
        if (this.loginModel.isDatabaseConnected()) {
            this.dbstatus.setText("Connected");
        } else {
            this.dbstatus.setText("Not Connected to DB");
        }
        this.combobox.setItems(FXCollections.observableArrayList(options.values())); //richiama la classe option e fa comparire le due opzioni fra admin o student
    }
    /*
     * N.B: i metodi del componente Controller compariranno nella casella "onAction" dei componenti fxml
     * il metodo Login in base alla scelta fra admin/studente richiamerà il metodo giusto
     * attraverso un semplice switch
     */

    @FXML
    public void Login(ActionEvent event) {
        try {
            if (this.loginModel.isLogin(this.username.getText(), this.passwrd.getText(), ((options) this.combobox.getValue()).toString())) {
                Stage stage = (Stage) this.loginbutton.getScene().getWindow();
                stage.close();
                switch (((options) this.combobox.getValue()).toString()) {
                    case "Admin":
                        adminLogin();
                        break;
                    case "Student":
                        studentLogin();
                        break;
                }
            } else {
                this.credentials.setText("wrong credentials!");
            }
        } catch (Exception localExeption) {

        }
    }

    /*
     * In base al tipo di login (come admin o come studente)
     * */
    public void studentLogin() {
        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/Students/students.fxml").openStream());
            StudentsController studentsController = (StudentsController) loader.getController();

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student Dashboard");
            userStage.setResizable(true);
            userStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void adminLogin() {
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminloader = new FXMLLoader();
            Pane adminroot = (Pane) adminloader.load(getClass().getResource("/Admin/Admin.fxml").openStream());
            AdminController adminController = (AdminController) adminloader.getController();

            Scene scene = new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(true);
            adminStage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
