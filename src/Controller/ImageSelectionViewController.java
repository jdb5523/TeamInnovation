package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
public class ImageSelectionViewController implements Initializable {

    AppController app;
    @FXML private Button browseButton;
    @FXML private Button processButton;
    @FXML private Button backButton;
    @FXML private TextField pathField;
    @FXML private DatePicker captureDate;
    @FXML private TextField photographerField;
    @FXML private TextField caseIdField;
    @FXML private Label errorLabel;
    @FXML private ImageView helpIcon;
    private final String imageParameters[] = new String[5];
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(new FileInputStream("images/helpicon.png"));
            helpIcon.setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        errorLabel.setVisible(false);
    }    
    
    @FXML
    protected void handleBrowseButtonAction() throws IOException, SQLException {
        pathField.setText(app.getFile().getPath());
    }
    
    @FXML
    protected void handleProcessButtonAction() throws IOException, SQLException {
        if (pathField.getText().isEmpty() || captureDate.getValue().toString().isEmpty() ||
            photographerField.getText().isEmpty()) {
            errorLabel.setVisible(true);
        } else {
            imageParameters[0] = String.valueOf(caseIdField.getText());
            imageParameters[1] = captureDate.getValue().toString();
            imageParameters[2] = photographerField.getText();
            imageParameters[3] = String.valueOf(app.getCurrentUserID());
            imageParameters[4] = pathField.getText();
            app.getFile().ocr(imageParameters);
        }
    }
    
    @FXML
    protected void handleHelpButtonAction() {
        try {
            app.showUserGuide(4);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    protected void handleBackButtonAction() throws IOException {
        app.showOption();
    }
        
    @FXML 
    protected void handleKeyPressed(KeyEvent key) throws IOException, SQLException {
        Boolean isEnter = key.getCode() == KeyCode.ENTER;
        if (key.getCode() != null) {
            errorLabel.setVisible(false);
        }
        if (isEnter && browseButton.isFocused()) {
            handleBrowseButtonAction();
        } else if (isEnter && processButton.isFocused()) {
            handleProcessButtonAction();
        } else if (isEnter && backButton.isFocused()) {
            handleBackButtonAction();
        }
    }
    
    public void setUp(AppController app) {
        this.app = app;
    }
}
