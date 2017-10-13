package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class ResetPasswordViewController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private TextField idField;
    @FXML private Button submitButton;
    @FXML private Label userLabel;
    @FXML private TextArea questionArea;
    @FXML private TextField responseField;
    @FXML private Button responseButton;
    @FXML private PasswordField newField;
    @FXML private PasswordField confirmField;
    @FXML private Button saveButton;
    @FXML private Label matchLabel;
    @FXML private Label responseLabel;
    @FXML private Label newLabel;
    @FXML private Label confirmLabel;
    @FXML private Button backButton;
    AppController app;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setUp(AppController app) {
        this.app = app;
    }
    
    public Boolean passwordsMatch() {
        return newField.getText().equals(confirmField.getText());
    }
    
    @FXML
    protected void handleSubmitButtonAction() throws SQLException {  
        if (app.getDb().isUserValid(usernameField.getText(), 
                Integer.parseInt(idField.getText()))) {
            secondSection();
            userLabel.setText("Username/ID combination found");
            userLabel.setTextFill(Color.GREEN);
            userLabel.setVisible(true);
            app.setCurrentUserID(Integer.parseInt(idField.getText()));
            questionArea.setText(app.getDb().getChallengeQuestion(
                    app.getCurrentUserID()));
            System.out.println(app.getDb().getChallengeQuestion(1));
        } else {
            userLabel.setVisible(true);
        }
    }
    
    @FXML
    protected void handleResponseButtonAction() throws SQLException {
        if (responseField.getText().equals(app.getDb().getChallengeResponse(
                app.getCurrentUserID()))) {
            responseLabel.setText("Reponse verified");
            responseLabel.setTextFill(Color.GREEN);
            responseLabel.setVisible(true);
            thirdSection();
        } else {
            responseLabel.setVisible(true);
        }
    }
    
    @FXML protected void handleSaveButtonAction() throws IOException, SQLException {
        if (passwordsMatch()) {
            matchLabel.setText("Password updated");
            matchLabel.setTextFill(Color.GREEN);
            passwordSaved();
            app.getDb().setNewPassword(confirmField.getText(), app.getCurrentUserID());
        } else {
            matchLabel.setVisible(true);
        }
    }
    
    @FXML protected void handleBackButtonAction() throws IOException {
        app.showLogin();
    }
    
    private void secondSection() {
        usernameField.setOpacity(.25);
        usernameField.setFocusTraversable(false);
        usernameField.setEditable(false);
        idField.setOpacity(.25);
        idField.setEditable(false);
        idField.setFocusTraversable(false);
        userLabel.setOpacity(.5);
        submitButton.setOpacity(.25);
        submitButton.setDisable(true);
        questionArea.setOpacity(1);
        responseField.setOpacity(1);
        responseField.setEditable(true);
        responseField.setFocusTraversable(true);
        responseField.requestFocus();
        responseButton.setVisible(true);
        responseButton.setFocusTraversable(true);
    }
    
    private void thirdSection() {
        questionArea.setOpacity(.25);
        responseField.setOpacity(.25);
        responseField.setFocusTraversable(false);
        responseField.setEditable(false);
        responseLabel.setOpacity(.5);
        submitButton.setOpacity(.25);
        responseButton.setDisable(true);
        newLabel.setOpacity(100);
        newField.setOpacity(100);
        newField.setEditable(true);
        newField.requestFocus();
        newField.setFocusTraversable(true);
        confirmLabel.setOpacity(100);
        confirmField.setOpacity(100);
        confirmField.setEditable(true);
        confirmField.setFocusTraversable(true);
        saveButton.setVisible(true);
        saveButton.setFocusTraversable(true);
    }
    
    private void passwordSaved() {
        newField.setEditable(false);
        newField.setOpacity(.25);
        confirmField.setEditable(false);
        confirmField.setOpacity(.25);
        saveButton.setDisable(true);
        matchLabel.setOpacity(.5);
    }
    
    @FXML protected void handleKeyPressed (KeyEvent keyEvent) throws IOException, SQLException {
        if (keyEvent.getCode() == KeyCode.ENTER && 
                (usernameField.isFocused() || idField.isFocused() || submitButton.isFocused())) {
            handleSubmitButtonAction();
        } else if(keyEvent.getCode() == KeyCode.ENTER && 
                (responseField.isFocused() || responseButton.isFocused())) {
            handleResponseButtonAction();
        } else if (keyEvent.getCode() == KeyCode.ENTER && 
                (newField.isFocused() || confirmField.isFocused() || saveButton.isFocused())) {
            handleSaveButtonAction();
        } else if (keyEvent.getCode() == KeyCode.ENTER && backButton.isFocused()) {
            handleBackButtonAction();
        } 
    }
}
