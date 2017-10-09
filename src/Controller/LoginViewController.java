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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        usernameField.requestFocus();
        invalidLabel.setVisible(false);
    }    
    

    @FXML
    protected void handleLoginButtonAction() throws IOException, SQLException {
        if(app.getDb().authentication(usernameField.getText(), passwordField.getText())) {
            System.out.println("Login successful");
            app.showMain();
            app.setCurrentUser(usernameField.getText());
            app.setCurrentUserID(app.getDb().getUserID(usernameField.getText()));
        } else {
            System.out.println("Login Failed");
            invalidLabel.setVisible(true);
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
        }
    }
}
