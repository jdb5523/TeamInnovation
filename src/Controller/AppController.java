package Controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {

    Parent root;
    private MainController main;
    private LoginViewController login;
    private ResetPasswordViewController reset;
    private FileController file;
    private DatabaseController db;
    private Stage stage;
    private String currentUser;
    private int currentUserID;

    /**
     *Default constructor for the AppController
     *@param stage Takes in the stage created in the Overidden start() method
     *@throws java.io.IOException Throws error if the fxml file is unable to 
     * be loaded
    */
    public AppController(Stage stage) throws IOException {
        this.db = new DatabaseController(this);
        db.connect();
        this.file = new FileController(this); 
        this.stage = stage;
        showLogin();
    }
    
    /**
     * This is the first screen presented to the user when the application is launched
     * It allows for the input and passing of values to authenticate user logins
     * @throws IOException 
     */
    public final void showLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"));
        root = loader.load();
        login = (LoginViewController) loader.getController();
        login.setUp(this);
        stage.setTitle("Login");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Upon successful authentication, the instance of the AppController object will
     * change the JavaFX stage from the login view to the main view
     * @throws java.io.IOException Throws error if the MainView fxml doc cannot
     * be loaded
    **/
    public void showMain() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
        root = loader.load();
        main = (MainController) loader.getController();
        main.setUp(this);
        stage.setTitle("Nautilus ALPHA BUILD -- Welcome, " + getCurrentUser());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * This screen allows users to reset their passwords after successful validation.
     * Validation requires the entry of a valid usernames and user_id as well
     * as the correct response to the challenge question
     * @throws IOException 
     */
    public void showReset() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ResetPasswordView.fxml"));
        root = loader.load();
        reset = (ResetPasswordViewController) loader.getController();
        reset.setUp(this);
        stage.setTitle("Reset Password");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public MainController getMain() {
        return main;
    }

    public void setMain(MainController main) {
        this.main = main;
    }

    public FileController getFile() {
        return file;
    }

    public void setFile(FileController file) {
        this.file = file;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public LoginViewController getLoginView() {
        return login;
    }

    public void setLoginView(LoginViewController loginView) {
        this.login = loginView;
    }

    public DatabaseController getDb() {
        return db;
    }

    public void setDb(DatabaseController db) {
        this.db = db;
    }

    public ResetPasswordViewController getReset() {
        return reset;
    }

    public void setReset(ResetPasswordViewController reset) {
        this.reset = reset;
    }
    
    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public int getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(int currentUserID) {
        this.currentUserID = currentUserID;
    }
}
