package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DecryptViewController implements Initializable {

    AppController app;
    @FXML private TextField dateField;
    @FXML private TextField photogField;
    @FXML private TextField caseField;
    @FXML private TextArea inputArea;
    @FXML private ImageView helpIcon;
    private String cipherName = "";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(new FileInputStream("images/helpicon.png"));
            helpIcon.setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        inputArea.setText("Processing image...");
    }    
    
    @FXML 
    protected void handleDecryptButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("The decryption process may take a while.  Do you wish to proceed?");
        Optional<ButtonType> confirm = alert.showAndWait();
        if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
            System.out.println("DECRYPT");
            Alert decrypt = new Alert(Alert.AlertType.NONE);
            decrypt.setContentText("Running " + getCipherName() + " Cipher");
            decrypt.show();
        }
        
        
    }
    
    @FXML
    protected void handleHelpButtonAction() {
        try {
            app.showUserGuide(8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    protected void handleHistoryButtonAction() throws SQLException {
        try {
            app.showHistory();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
  
    public void setUp(AppController app, String[] parameters) {
        this.app = app;
        dateField.setText(parameters[1]);
        photogField.setText(parameters[2]);
        caseField.setText(parameters[0]);
    }
    
    public void finishSetUp(String fileContents) {
        inputArea.setText(fileContents);
    }

    /**
     * @return the cipherName
     */
    public String getCipherName() {
        return cipherName;
    }
}
