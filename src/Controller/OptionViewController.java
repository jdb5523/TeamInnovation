package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class OptionViewController implements Initializable {

    AppController app;
    @FXML private Button processButton;
    @FXML private Button historyButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    protected void handleProcessButtonAction() throws IOException {
        app.showProcessImage();
    }
    
    @FXML 
    protected void handleHistoryButtonAction() throws IOException {
        app.showHistory();
    }
    
    public void handleKeyPressed(KeyEvent key) throws IOException {
        if (key.getCode() == KeyCode.ENTER && processButton.isFocused()) {
            handleProcessButtonAction();
        } else if (key.getCode() == KeyCode.ENTER && historyButton.isFocused()) {
            handleHistoryButtonAction();
        }
    }
    
    public void setUp(AppController app) {
        this.app = app;
    }
}
