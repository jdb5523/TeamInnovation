package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class HistoryViewController implements Initializable {

    AppController app;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
