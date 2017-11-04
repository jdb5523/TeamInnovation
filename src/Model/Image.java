package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Image {
    
    private int IMAGE_ID;
    private IntegerProperty CASE_ID;
    private String CAPTURE_DATE;
    private int PHOTOGRAPHER;
    private int PROCESSED_BY;
    private StringProperty FILE_PATH;
    private StringProperty ADDITIONAL_NOTES;
    
    public Image(int IMAGE_ID, int CASE_ID, String CAPTURE_DATE, int PHOTOGRAPHER,
            int PROCESSED_BY, String FILE_PATH, String ADDITIONAL_NOTES) {
        
        this.IMAGE_ID = IMAGE_ID;
        this.CASE_ID = new SimpleIntegerProperty(CASE_ID);
        this.CAPTURE_DATE = CAPTURE_DATE;
        this.PHOTOGRAPHER = PHOTOGRAPHER;
        this.PROCESSED_BY = PROCESSED_BY;
        this.FILE_PATH = new SimpleStringProperty(FILE_PATH);
        this.ADDITIONAL_NOTES = new SimpleStringProperty(ADDITIONAL_NOTES);
    }
    
    public int getIMAGE_ID() {
        return IMAGE_ID;
    }

    public IntegerProperty getCASE_ID() {
        return CASE_ID;
    }

    public String getCAPTURE_DATE() {
        return CAPTURE_DATE;
    }

    public int getPHOTOGRAPHER() {
        return PHOTOGRAPHER;
    }

    public int getPROCESSED_BY() {
        return PROCESSED_BY;
    }

    public StringProperty getFILE_PATH() {
        return FILE_PATH;
    }

    public StringProperty getADDITIONAL_NOTES() {
        return ADDITIONAL_NOTES;
    }
}
