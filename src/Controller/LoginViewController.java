package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginViewController implements Initializable {

    AppController app;
    ResetPasswordViewController reset;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label invalidLabel;
    @FXML private Button loginButton;
    @FXML private Button resetButton;
    @FXML private Button aboutButton;
    @FXML private Button guideButton;
    @FXML private ImageView logo;
    private int lockedFlag = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameField.requestFocus();
        invalidLabel.setVisible(false);
        try {
            Image image;
            image = new Image(new FileInputStream("images/logo.jpg"));
            logo.setImage(image);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }    
    

    @FXML
    protected void handleLoginButtonAction() throws IOException, SQLException {
        if(authentication(usernameField.getText(), passwordField.getText())) {
            System.out.println("Login successful");
            app.setCurrentUser(usernameField.getText());
            app.setCurrentUserID(app.getDb().getUserId(app.getCurrentUser()));
            app.showOption();
        } else {
            if (lockedFlag == 0) {
                invalidLabel.setText("Invalid login credentials");
                invalidLabel.setVisible(true);
            } else {
                invalidLabel.setText("Your account is locked");
                invalidLabel.setVisible(true);
                lockedFlag = 0;
            }
        }
    }
    
    @FXML
    protected void handleAboutButtonAction() throws IOException {
        app.showAbout();
    }
    
    public Boolean authentication(String user, String password) throws SQLException {
        ResultSet result = app.getDb().getUser(user, password);
        while (result.next()) {
            String validPw = result.getString("password");
            int attempts = result.getInt("LOGIN_ATTEMPT");
            int locked = result.getInt("LOCKED");
            Timestamp unlockDate = result.getTimestamp("LOCKOUT_DATE");
            if (unlockDate != null) {
                unlockDate.setDate(unlockDate.getDate() + 1);
                Date currentDate = new Date();
                if (currentDate.after(unlockDate)) {
                    app.getDb().removeLock(user);
                    locked = 0;
                    attempts = 0;
                }
            }
            if (locked == 1) {
                app.getLoginView().setLockedFlag(1);
                return false;
            }
            if (validPw.equals(password) && locked == 0) {
                app.getDb().resetLoginAttempts(user);
                return true;
            } else if (!validPw.equals(password) && attempts == 2) {
                app.getDb().addLoginAttempt(user);
                app.getDb().lockAccount(user);
                return false;
            } else if (!validPw.equals(password) && attempts < 3) {
                app.getDb().addLoginAttempt(user);
                return false;
            }
        }   
        return false;
    }
    
    @FXML
    protected void handleResetButtonAction() throws IOException {
        app.showReset();
    }
    public void setUp(AppController app) {
        this.app = app;
    }
    
    @FXML
    protected void handleGuideButtonAction() throws IOException {
        app.showUserGuide(1);
    }
    
    @FXML protected void handleKeyPressed(KeyEvent keyEvent) throws IOException, SQLException {
        if(keyEvent.getCode() == KeyCode.ENTER && (loginButton.isFocused() 
                || usernameField.isFocused() || passwordField.isFocused())) {
            handleLoginButtonAction();
        } else if (keyEvent.getCode() == KeyCode.ENTER && resetButton.isFocused()) {
            handleResetButtonAction();
        } else if (keyEvent.getCode() == KeyCode.ENTER && aboutButton.isFocused()) {
            handleAboutButtonAction();
        } else if (keyEvent.getCode() == KeyCode.ENTER && guideButton.isFocused()) {
            handleGuideButtonAction();
        } else if (keyEvent.getCode() != KeyCode.ENTER) {
            invalidLabel.setVisible(false);
        }
    }

    public int getLockedFlag() {
        return lockedFlag;
    }

    public void setLockedFlag(int lockedFlag) {
        this.lockedFlag = lockedFlag;
    }
}
