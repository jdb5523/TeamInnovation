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
    GuideViewController guide;
    AboutViewController about;
    OptionViewController option;
    ProcessImageViewController image;
    private FileController file;
    private DatabaseController db;
    private Stage stage;
    Stage guideStage;
    Stage aboutStage;
    private String currentUser;
    private int currentUserID;
    FXMLLoader loader;

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
        //TODO
        //Change back to Login
        //showLogin();
        showProcessImage();
    }
    
    /**
     * This is the first screen presented to the user when the application is launched
     * It allows for the input and passing of values to authenticate user logins
     * @throws IOException 
     */
    public final void showLogin() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"));
        root = loader.load();
        login = (LoginViewController) loader.getController();
        login.setUp(this);
        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public final void showOption() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/OptionView.fxml"));
        root = loader.load();
        option = (OptionViewController) loader.getController();
        option.setUp(this);
        Scene scene = new Scene(root);
        stage.setTitle("Option Menu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public final void showProcessImage() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/ProcessImageView.fxml"));
        root = loader.load();
        image = (ProcessImageViewController) loader.getController();
        image.setUp(this);
        Scene scene = new Scene(root);
        stage.setTitle("Import Image");
        stage.setResizable(false);
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
        loader = new FXMLLoader(getClass().getResource("/View/MainView.fxml"));
        root = loader.load();
        main = (MainController) loader.getController();
        main.setUp(this);
        stage.setTitle("Nautilus ALPHA BUILD -- Welcome, " + getCurrentUser());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * This screen allows users to reset their passwords after successful validation.
     * Validation requires the entry of a valid usernames and user_id as well
     * as the correct response to the challenge question
     * @throws IOException 
     */
    public void showReset() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/ResetPasswordView.fxml"));
        root = loader.load();
        reset = (ResetPasswordViewController) loader.getController();
        reset.setUp(this);
        stage.setTitle("Reset Password");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public void showUserGuide() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/GuideView.fxml"));
        root = loader.load();
        guide = (GuideViewController) loader.getController();
        guide.setUp(this);
        guideStage = new Stage();
        guideStage.setX(1500);
        guideStage.setY(180);
        guideStage.setTitle("User Guide");
        Scene scene = new Scene(root);
        guideStage.setScene(scene);
        guideStage.setResizable(false);
        guideStage.show();
    }
    
    public void showAbout() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/AboutView.fxml"));
        root = loader.load();
        about = (AboutViewController) loader.getController();
        about.setUp(this);
        aboutStage = new Stage();
        aboutStage.setX(500);
        aboutStage.setY(180);
        aboutStage.setTitle("About Nautilus");
        Scene scene = new Scene(root);
        aboutStage.setScene(scene);
        aboutStage.setResizable(false);
        aboutStage.show();
    }
    
    public void closeGuide() {
        guideStage.close();
    }
   
    public void closeAbout() {
        aboutStage.close();
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
