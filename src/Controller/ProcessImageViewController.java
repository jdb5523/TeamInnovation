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
    private String imageParameters[] = new String[5];
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.setVisible(false);
    }    
    
    @FXML
    protected void handleBrowseButtonAction() throws IOException, SQLException {
        pathField.setText(app.getFile().getPath());
    }
    
    @FXML
    protected void handleProcessButtonAction() throws IOException, SQLException {
        if (pathField.getText().isEmpty() || monthField.getText().isEmpty() || 
                dayField.getText().isEmpty() || yearField.getText().isEmpty() ||
            photographerField.getText().isEmpty()) {
            errorLabel.setVisible(true);
        } else {
            String formattedDate = "'" + yearField.getText() + "-" + 
                    monthField.getText() + "-" + dayField.getText() + "'";
            imageParameters[0] = caseIdField.getText();
            imageParameters[1] = formattedDate;
            imageParameters[2] = photographerField.getText();
            imageParameters[3] = String.valueOf(app.getCurrentUserID());
            imageParameters[4] = pathField.getText();
            app.getFile().ocr(imageParameters);
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
