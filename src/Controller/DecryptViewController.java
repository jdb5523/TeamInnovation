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
import java.util.Locale;
import java.util.Map;
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
    @FXML private Button historyButton;
    @FXML private Label cipherWarning;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Decoder decoder = new Decoder();
    GoogleTranslate translator = new GoogleTranslate();
    ArrayList<String> results = new ArrayList();
    Map<String, String> toBeTranslated;
    ArrayList<String> untranslated;
    ArrayList<String> languages;
    Locale locale;
    String language;
    
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("The decryption process may take a while.  Do you wish to proceed?");
            Optional<ButtonType> confirm = alert.showAndWait();
            if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                String detectedLang;
                Dialog<?> dialog = new Dialog();
                Window window = dialog.getDialogPane().getScene().getWindow();
                String date = df.format(new Date());
                app.getDb().updateInput(inputArea.getText(), ocrId);
                untranslated = new ArrayList();
                languages = new ArrayList();
                
                if (affineBox.isSelected()) {
                    dialog.setContentText("Running Atbash Cipher");
                    dialog.show();
                    results = decoder.affineDecrypt(inputArea.getText());
                    for (String result : results) {
                        detectedLang = translator.detectLanguage(result);
                        app.getDb().insertDecryptRecord(1, result, detectedLang,
                                ocrId, date);
                        untranslated.add(result);
                        languages.add(detectedLang);
                    }
                    dialog.close();
                }
                
                if (atbashBox.isSelected()) {
                    dialog.setContentText("Running Atbash Cipher");
                    dialog.show();
                    results = decoder.atbashDecrypt(inputArea.getText());
                    for (String result : results) {
                        detectedLang = translator.detectLanguage(result);
                        app.getDb().insertDecryptRecord(2, result, detectedLang,
                                ocrId, date);
                        untranslated.add(result);
                        languages.add(detectedLang);
                    }
                }
                
                if (baconianBox.isSelected()) {
                    dialog.setContentText("Running Baconian Cipher");
                    dialog.show();
                    results = decoder.baconianDecrypt(inputArea.getText());
                    for (String result : results) {
                        System.out.println(result);
                        detectedLang = translator.detectLanguage(result);
                        app.getDb().insertDecryptRecord(3, result, 
                                detectedLang, ocrId, date);
                        untranslated.add(result);
                        languages.add(detectedLang);
                    }
                }

                if (caesarBox.isSelected()) {
                    int key = 1;
                    dialog.setContentText("Running Caesar Cipher");
                    dialog.show();
                    results = decoder.caesarDecrypt(inputArea.getText());
                    for (String result : results) {
                        detectedLang = translator.detectLanguage(result);
                        app.getDb().insertDecryptRecord(4, result, detectedLang,
                                ocrId, date);
                        untranslated.add(result);
                        languages.add(detectedLang);
                        key++;
                    }
                } 
                window.hide();
                disableFirstElementSet();
                translate();
            }      
        }
    }
     
    protected void translate() throws SQLException {
        Dialog<?> dialog = new Dialog();
        Window window = dialog.getDialogPane().getScene().getWindow();
        dialog.setContentText("Translating...");
        dialog.show();
        String output = "";
        String translated = "";
        String finalOutput = "";
        int index = 0;
        int startingId = app.getDb().getLastDecryptId() - untranslated.size() + 1;
        int lastId = startingId + untranslated.size() - 1;
        Boolean isBacon = true;
        for (int i = startingId; i <= lastId; i++) {
            if (!languages.get(index).equals("en")) {
                translated = translator.translateLanguage(untranslated.get(index), 
                        languages.get(index));
                output = "Original (" + languages.get(index) + "): " + untranslated.get(index) 
                        + "\nTranslated: " + translated;
            } else if (untranslated.get(index).equals("Not a Baconian Encryption")) {
                isBacon = false;
            } else {
                translated = untranslated.get(index);
                output = "Not Translated - English detected:\n" + untranslated.get(index);
            }
            locale = new Locale(languages.get(index));
            double ratio = translator.runProcess(languages.get(index), translated, "English");
            if (ratio >= 0.7 && isBacon) {
                app.getDb().insertTranslations(i, translated);
                finalOutput += output + "\nRatio: " + ratio + "\n\n";
            }
            index++;
            isBacon = true;
        }
        disableSecondElementSet();
        outputArea.setText(finalOutput);
        window.hide();
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
    
    private void disableFirstElementSet() {
        decryptButton.setDisable(true);
        decryptButton.setOpacity(.25);
        inputArea.setEditable(false);
        inputArea.setOpacity(.5);
        affineBox.setDisable(true);
        affineBox.setOpacity(.25);
        atbashBox.setDisable(true);
        atbashBox.setOpacity(.25);
        baconianBox.setDisable(true);
        baconianBox.setOpacity(.25);
        caesarBox.setDisable(true);
        caesarBox.setOpacity(.25);
    }
    
    private void disableSecondElementSet() {
        historyButton.setDisable(false);
        historyButton.setOpacity(1);
    }
}
