package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    private int lockedFlag = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameField.requestFocus();
        invalidLabel.setVisible(false);
    }    
    

    @FXML
    protected void handleLoginButtonAction() throws IOException, SQLException {
        if(app.getDb().authentication(usernameField.getText(), passwordField.getText())) {
            System.out.println("Login successful");
            app.setCurrentUser(usernameField.getText());
            app.setCurrentUserID(app.getDb().getUserID(usernameField.getText()));
            app.showMain();     
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
    protected void handleResetButtonAction() throws IOException {
        app.showReset();
    }
    public void setUp(AppController app) {
        this.app = app;
    }
    
    @FXML protected void handleKeyPressed(KeyEvent keyEvent) throws IOException, SQLException {
        if(keyEvent.getCode() == KeyCode.ENTER && (loginButton.isFocused() 
                || usernameField.isFocused() || passwordField.isFocused())) {
            handleLoginButtonAction();
        } else if (keyEvent.getCode() == KeyCode.ENTER && resetButton.isFocused()) {
            handleResetButtonAction();
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
