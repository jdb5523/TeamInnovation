package Controller;

import Cipher.Caesar;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MainController {
    
    AppController app;
    @FXML private TextArea inputArea;
    @FXML private TextArea outputArea;
    private String outputText = "";
    Caesar m = new Caesar();
    
    @FXML
    protected void handleDecryptButtonAction(ActionEvent event) throws IOException {
        outputText = m.decryptCaesar(inputArea.getText());
        outputArea.setText(outputText);
    }
    
    @FXML
    protected void handleImportButtonAction(ActionEvent event) throws IOException {
        app.getFile().setFileContents("");
        app.getFile().importText();
        clearText();
        inputArea.setText(app.getFile().getFileContents());
    }
    
    @FXML
    protected void handleClearButtonAction(ActionEvent event) throws IOException {
        clearText();
    }
    
    @FXML 
    protected void handleOcrButtonAction(ActionEvent event) throws IOException, SQLException {
        clearText();
        app.getFile().setFileContents("");
        app.getFile().ocr();
        inputArea.setText(app.getFile().getFileContents());
    }
    
    @FXML
    protected void handleSaveButtonAction(ActionEvent event) throws IOException {
        app.getFile().saveOutput();
    }
    
    public void setUp(AppController app) {
        this.app = app;
    }
    
    public void clearText() {
        inputArea.setText("");
        outputArea.setText("");
    }

    public String getOutputText() {
        return outputText;
    }

    public TextArea getInputArea() {
        return inputArea;
    }

    public void setInputArea(TextArea inputArea) {
        this.inputArea = inputArea;
    }
}
