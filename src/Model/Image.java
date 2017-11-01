package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Image {
    
    private IntegerProperty IMAGE_ID;
    private IntegerProperty CASE_ID;
    private StringProperty CAPTURE_DATE;
    private IntegerProperty PHOTOGRAPHER;
    private IntegerProperty PROCESSED_BY;
    private StringProperty FILE_PATH;
    private StringProperty ADDITIONAL_NOTES;
    
    public Image(int IMAGE_ID, int CASE_ID, String CAPTURE_DATE, int PHOTOGRAPHER,
            int PROCESSED_BY, String FILE_PATH, String ADDITIONAL_NOTES) {
        
        this.IMAGE_ID = new SimpleIntegerProperty(IMAGE_ID);
        this.CASE_ID = new SimpleIntegerProperty(CASE_ID);
        this.CAPTURE_DATE = new SimpleStringProperty(CAPTURE_DATE);
        this.PHOTOGRAPHER = new SimpleIntegerProperty(PHOTOGRAPHER);
        this.PROCESSED_BY = new SimpleIntegerProperty(PROCESSED_BY);
        this.FILE_PATH = new SimpleStringProperty(FILE_PATH);
        this.ADDITIONAL_NOTES = new SimpleStringProperty(ADDITIONAL_NOTES);
    }
    
    public IntegerProperty getIMAGE_ID() {
        return IMAGE_ID;
    }

    public IntegerProperty getCASE_ID() {
        return CASE_ID;
    }

    public StringProperty getCAPTURE_DATE() {
        return CAPTURE_DATE;
    }

    public IntegerProperty getPHOTOGRAPHER() {
        return PHOTOGRAPHER;
    }

    public IntegerProperty getPROCESSED_BY() {
        return PROCESSED_BY;
    }

    public StringProperty getFILE_PATH() {
        return FILE_PATH;
    }

    public StringProperty getADDITIONAL_NOTES() {
        return ADDITIONAL_NOTES;
    }
}
