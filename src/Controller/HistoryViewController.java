package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HistoryViewController implements Initializable {

    AppController app;
    @FXML private Button detailsButton;
    @FXML private Button processButton;
    @FXML private Button guideButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    protected void handleProcessButtonAction() throws IOException {
        app.showProcessImage();
    }
    
    @FXML 
    protected void handleDetailViewButtonAction() throws IOException {
        app.showDetail();
    }

    @FXML
    protected void handleGuideButtonAction() throws IOException {
        app.showUserGuide();
    }
    void setUp(AppController app) {
        this.app = app;
    }
    
    @FXML
    protected void handleKeyPressed(KeyEvent key) throws IOException {
        Boolean isEnter = key.getCode() == KeyCode.ENTER;
        if (isEnter && detailsButton.isFocused()) {
            app.showDetail();
        } else if (isEnter && processButton.isFocused()) {
            app.showProcessImage();
        } else if (isEnter && guideButton.isFocused()) {
            app.showUserGuide();
        }
    }
}
