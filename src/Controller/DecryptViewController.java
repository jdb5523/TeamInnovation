package Controller;

import Cipher.Decoder;
import Cipher.GoogleTranslate;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
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
    @FXML private TextArea outputArea;
    @FXML private CheckBox affineBox;
    @FXML private CheckBox atbashBox;
    @FXML private CheckBox baconianBox;
    @FXML private CheckBox caesarBox;
    @FXML private ImageView helpIcon;
    @FXML private ImageView google;
    @FXML private Button decryptButton;
    @FXML private Button translateButton;
    @FXML private Button historyButton;
    @FXML private Label cipherWarning;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Decoder decoder = new Decoder();
    GoogleTranslate translator = new GoogleTranslate();
    ArrayList<String> results;
    
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
    protected void handleDecryptButtonAction() throws SQLException {
        if(!affineBox.isSelected() && !atbashBox.isSelected() && !baconianBox.isSelected()
                && !caesarBox.isSelected()) {
            cipherWarning.setVisible(true);
        }
        else {
            cipherWarning.setVisible(false);
            String output = "";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("The decryption process may take a while.  Do you wish to proceed?");
            Optional<ButtonType> confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                Dialog<?> dialog = new Dialog();
                Window window = dialog.getDialogPane().getScene().getWindow();
                String date = df.format(new Date());
                app.getDb().updateInput(inputArea.getText(), ocrId);
                if (affineBox.isSelected()) {
                    output += "--AFFINE--\n\n";
                    dialog.setContentText("Running Atbash Cipher");
                    dialog.show();
                    results = decoder.affineDecrypt(inputArea.getText());
                    for (String result : results) {
                        app.getDb().insertDecryptRecord(1, result, translator.
                                detectLanguage(result), ocrId, date);
                        output += result + "\n\n";
                    }
                    output += "--END AFFINE--\n\n";
                    dialog.close();
                }
                if (atbashBox.isSelected()) {
                    output += "----ATBASH----\n\n";
                    dialog.setContentText("Running Atbash Cipher");
                    results = decoder.atbashDecrypt(inputArea.getText());
                    for (String result : results) {
                        app.getDb().insertDecryptRecord(2, result, translator.
                                detectLanguage(result), ocrId, date);
                        output += result + "\n\n";
                    }
                    output += "\n----END ATBASH----\n\n";
                }
                if (baconianBox.isSelected()) {
                    output += "--BACONIAN--\n\n";
                    dialog.setContentText("Running Baconian Cipher");
                    results = decoder.baconianDecrypt(inputArea.getText());
                    for (String result : results) {
                        app.getDb().insertDecryptRecord(3, result, 
                                translator.detectLanguage(result), ocrId, date);
                        output += result + "\n\n";
                    }
                    output += "\n----END BACONIAN----\n\n";
                }
                if (caesarBox.isSelected()) {
                    output += "----CAESAR----\n\n";
                    dialog.setContentText("Running Caesar Cipher");
                    dialog.show();
                    results = decoder.caesarDecrypt(inputArea.getText());
                    for (String result : results) {
                        app.getDb().insertDecryptRecord(4, result, translator.
                                detectLanguage(result), ocrId, date);
                        output += result + "\n\n";
                    }
                    output += "\n--END CAESAR--";
                } 
                outputArea.setText(output);
                window.hide();
                translateButton.setDisable(false);
                translateButton.setOpacity(1);
                decryptButton.setDisable(true);
                decryptButton.setOpacity(.25);
                inputArea.setEditable(false);
                inputArea.setOpacity(.5);
            }      
        }
    }
    
    @FXML 
    protected void handleTranslateButtonAction() {
        historyButton.setDisable(false);
        historyButton.setOpacity(1);
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
        inputArea.setEditable(true);
        inputArea.setText(fileContents);
        this.ocrId = ocrId;
        decryptButton.setDisable(false);
        decryptButton.setOpacity(1);
    }
}
