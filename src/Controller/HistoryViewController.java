package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HistoryViewController implements Initializable {

    AppController app;
    @FXML private Button detailsButton;
    @FXML private Button processButton;
    @FXML private Button guideButton;
    @FXML private ImageView helpIcon;
    @FXML private TableView<Model.Image> historyTable;
    @FXML private TableColumn<Model.Image, Integer> idColumn;
    @FXML private TableColumn<Model.Image, String> dateColumn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(new FileInputStream("images/helpicon.png"));
            helpIcon.setImage(image);
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }    
    
    @FXML
    protected void handleProcessButtonAction() throws IOException {
        app.showImageSelection();
    }
    
    @FXML 
    protected void handleDetailViewButtonAction() throws IOException {
        app.showDetail();
    }

    @FXML
    protected void handleHelpButtonAction() throws IOException {
        app.showUserGuide(6);
    }
    
    public void setUp(AppController app) throws SQLException {
        this.app = app;
        historyTable = new TableView<>();
        historyTable.setItems(app.getDb().createImageList().getImageList());
    }
    
    @FXML
    protected void handleKeyPressed(KeyEvent key) throws IOException {
        Boolean isEnter = key.getCode() == KeyCode.ENTER;
        if (isEnter && detailsButton.isFocused()) {
            app.showDetail();
        } else if (isEnter && processButton.isFocused()) {
            app.showImageSelection();
        } else if (isEnter && guideButton.isFocused()) {
            app.showUserGuide(6);
        }
    }
}
