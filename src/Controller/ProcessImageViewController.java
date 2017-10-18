package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
public class ProcessImageViewController implements Initializable {

    AppController app;
    @FXML private Button browseButton;
    @FXML private Button processButton;
    @FXML private TextField pathField;
    @FXML private TextField monthField;
    @FXML private TextField dayField;
    @FXML private TextField yearField;
    @FXML private TextField photographerField;
    @FXML private TextField caseIdField;
    @FXML private Label errorLabel;
    private String imageParameters[] = new String[6];
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.setVisible(false);
    }    
    
    @FXML
    protected void handleBrowseButtonAction() throws IOException, SQLException {
        pathField.setText(app.getFile().processImage());
    }
    
    @FXML
    protected void handleProcessButtonAction() {
        if (pathField.getText().isEmpty() || monthField.getText().isEmpty() || 
                dayField.getText().isEmpty() || yearField.getText().isEmpty() ||
            photographerField.getText().isEmpty()) {
            errorLabel.setVisible(true);
        } else {
            imageParameters[0] = pathField.getText();
            imageParameters[1] = monthField.getText();
            imageParameters[2] = dayField.getText();
            imageParameters[3] = yearField.getText();
            imageParameters[4] = photographerField.getText();
            imageParameters[5] = caseIdField.getText();
            for (String s : imageParameters) {
                System.out.println(s);
            }
        }
    }
        
    @FXML 
    protected void handleKeyPressed(KeyEvent key) throws IOException, SQLException {
        KeyCode code = key.getCode();
        if (code != null) {
            errorLabel.setVisible(false);
        }
        else if (code == KeyCode.ENTER && browseButton.isFocused()) {
            handleBrowseButtonAction();
        } else if (code == KeyCode.ENTER && processButton.isFocused()) {
            handleProcessButtonAction();
        }
    }
    
    public void showError() {
        
    }
 
    public void setUp(AppController app) {
        this.app = app;
    }
}
