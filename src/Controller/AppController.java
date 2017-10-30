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
    HistoryViewController history;
    DetailViewController detail;
    GuideViewController guide;
    AboutViewController about;
    OptionViewController option;
    ImageSelectionViewController image;
    private DecryptViewController decrypt;
    private FileController file;
    private DatabaseController db;
    private Stage stage;
    Stage guideStage;
    Stage aboutStage;
    Stage detailStage;
    private String currentUser;
    private int currentUserID;
    FXMLLoader loader;
    Scene scene;

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
        showLogin();
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
        scene = new Scene(root);
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
        scene = new Scene(root);
        stage.setTitle("Option Menu");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public final void showImageSelection() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/ImageSelectionView.fxml"));
        root = loader.load();
        image = (ImageSelectionViewController) loader.getController();
        image.setUp(this);
        scene = new Scene(root);
        stage.setTitle("Import Image");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    public void showDecrypt(String[] parameters) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/DecryptView.fxml"));
        root = loader.load();
        decrypt = (DecryptViewController) loader.getController();
        decrypt.setUp(this, parameters);
        scene = new Scene(root);
        stage.setTitle("Decryption");
        stage.setResizable(false);
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
        loader = new FXMLLoader(getClass().getResource("/View/ResetPasswordView.fxml"));
        root = loader.load();
        reset = (ResetPasswordViewController) loader.getController();
        reset.setUp(this);
        stage.setTitle("Reset Password");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public void showHistory() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/HistoryView.fxml"));
        root = loader.load();
        history = (HistoryViewController) loader.getController();
        history.setUp(this);
        stage.setTitle("History Log");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public void showDetail() throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/DetailView.fxml"));
        root = loader.load();
        detail = (DetailViewController) loader.getController();
        detail.setUp(this);
        scene = new Scene(root);
        detailStage = new Stage();
        detailStage.setX(1500);
        detailStage.setY(180);
        detailStage.setTitle("Detail View");
        detailStage.setScene(scene);
        detailStage.setResizable(false);
        detailStage.show();
    }
    
    public void showUserGuide(int i) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/GuideView.fxml"));
        root = loader.load();
        guide = (GuideViewController) loader.getController();
        guide.setUp(this, i);
        guideStage = new Stage();
        guideStage.setX(1500);
        guideStage.setY(180);
        guideStage.setTitle("User Guide");
        scene = new Scene(root);
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
        scene = new Scene(root);
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
    
    public void closeDetail() {
        detailStage.close();
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

    public DecryptViewController getDecrypt() {
        return decrypt;
    }
}
