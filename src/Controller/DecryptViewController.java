package Controller;

import Cipher.Decoder;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

public class DecryptViewController implements Initializable {

    AppController app;
    private int ocrId;
    @FXML private TextField dateField;
    @FXML private TextField photogField;
    @FXML private TextField caseField;
    @FXML private TextArea inputArea;
    @FXML private CheckBox affineBox;
    @FXML private CheckBox atbashBox;
    @FXML private CheckBox baconianBox;
    @FXML private CheckBox caesarBox;
    @FXML private ImageView helpIcon;
    @FXML private ImageView google;
    private String cipherName = "";
    Decoder decoder = new Decoder();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(new FileInputStream("images/helpicon.png"));
            helpIcon.setImage(image);
            Image logo = new Image(new FileInputStream("images/google.png"));
            google.setImage(logo);
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
            Dialog<?> dialog = new Dialog();
            Window window = dialog.getDialogPane().getScene().getWindow();
            dialog.show();
            if (affineBox.isSelected()) {
                decoder.affineDecrypt("abc");
                dialog.setContentText("Running Affine Cipher");
            }
            if (atbashBox.isSelected()) {
                decoder.atbashDecrypt("abc");
                dialog.setContentText("Running Atbash Cipher");
            }
            if (baconianBox.isSelected()) {
                decoder.baconianDecrypt("abc");
                dialog.setContentText("Running Baconian Cipher");
            }
            if (caesarBox.isSelected()) {
                decoder.caesarDecrypt("abc");
                dialog.setContentText("Running Caesar Cipher");
            } 
            window.hide();
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
    
    public void finishSetUp(String fileContents, int ocrId) {
        inputArea.setText(fileContents);
        this.ocrId = ocrId;
    }

    /**
     * @return the cipherName
     */
    public String getCipherName() {
        return cipherName;
    }
}
