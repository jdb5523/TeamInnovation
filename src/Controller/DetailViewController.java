package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DetailViewController implements Initializable {

    AppController app;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    protected void handleCloseButtonAction() {
        app.closeDetail();
    }
    public void setUp(AppController app) {
        this.app = app;
    }
}
