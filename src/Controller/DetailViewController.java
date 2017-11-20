package Controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DetailViewController implements Initializable {

    AppController app;
    int imageId;
    private Image text;
    @FXML private Button closeButton;
    @FXML private ImageView helpIcon;
    @FXML private ImageView google;
    @FXML private ImageView textImage;
    @FXML private TextField dateField;
    @FXML private TextField decryptField;
    @FXML private TextField cipherField;
    @FXML private TextField ratingField;
    @FXML private TextArea resultsArea;
    @FXML private TextArea notesArea;
    @FXML private Label savedLabel;
    
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
    }    
    
    @FXML
    protected void handleSaveButtonAction() throws SQLException {
        String newNotes = notesArea.getText();
        app.getDb().saveNotes(newNotes, imageId);
        savedLabel.setVisible(true);
    }
    
    @FXML
    protected void handleHelpButtonAction() {
        try {
            app.showUserGuide(7);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    protected void handleKeyPressed(KeyEvent key) {
        Boolean isEnter = key.getCode() == KeyCode.ENTER;
        if (isEnter && closeButton.isFocused()) {
            handleCloseButtonAction();
        } 
    }
    
    @FXML
    protected void handleCloseButtonAction() {
        app.closeDetail();
    }
    
    public void setUp(AppController app, int imageId) throws SQLException, FileNotFoundException {
        this.app = app;
        this.imageId = imageId;
        String[] results = app.getDb().getRecordDetails(imageId);
        dateField.setText(results[0]);
        decryptField.setText(results[1]);
        cipherField.setText(results[2]);
        resultsArea.setText(results[3]);
        notesArea.setText(results[4]);
        text = new Image(new FileInputStream(results[5]));
        textImage.setImage(text);
    }
}
