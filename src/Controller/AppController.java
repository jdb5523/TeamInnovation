package Controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppController {

    Parent root;
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
    
    /**
     * Shows the main option menu where the user can either start a new OCR/
     * Decryption process or view the history log
     * @throws IOException 
     */
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
    
    /**
     * Displays the screen where the user can begin the OCR process. 
     * @throws IOException 
     */
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
    
    /**
     * Displays the screen where the resultant text from the OCR process can be
     * decrypted.
     * @param parameters The parameters are from the image selection screen and
     * they contain the file path, photographer, capture date, and case ID
     * @throws IOException 
     */
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
    
    /**
     * This displays the history log which is a TableView of all past processed
     * images.  Users can select an entry and view more details about it
     * @throws IOException
     * @throws SQLException 
     */
    public void showHistory() throws IOException, SQLException {
        loader = new FXMLLoader(getClass().getResource("/View/HistoryView.fxml"));
        root = loader.load();
        history = (HistoryViewController) loader.getController();
        history.setUp(this);
        stage.setTitle("History Log");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * The detail page pulls up more detailed information from the db about each 
     * image including results from the image's related OCR and decryption records.
     * @param imageId Takes in the IMAGE_ID PK of the image selected on the history
     * log screen
     * @throws IOException
     * @throws SQLException 
     */
    public void showDetail(int imageId) throws IOException, SQLException {
        loader = new FXMLLoader(getClass().getResource("/View/DetailView.fxml"));
        root = loader.load();
        detail = (DetailViewController) loader.getController();
        detail.setUp(this, imageId);
        scene = new Scene(root);
        detailStage = new Stage();
        detailStage.setX(1500);
        detailStage.setY(180);
        detailStage.setTitle("Detail View");
        detailStage.setScene(scene);
        detailStage.setResizable(false);
        detailStage.show();
    }
    
    /**
     * This displays the user guide which contains several tabs each containing
     * brief information and instructions on each screen of the application
     * @param i
     * @throws IOException 
     */
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
    
    /**
     * This displays the about page which contains copyright and license info
     * of all the software used to create Nautilus
     * @throws IOException 
     */
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
