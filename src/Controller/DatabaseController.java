package Controller;

import Model.Image;
import Model.ImageList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseController {

    private AppController app;
    private final String url = "jdbc:sqlserver://DESKTOP-TF4DGV3;"
            + "integratedSecurity=true;databaseName=Nautilus";
    private String sql = "";
    ResultSet result;
    Statement state;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Default constructor for DatabaseController
     *
     * @param app Takes in AppController object as an argument for two-way
     * communication
     */
    public DatabaseController(AppController app) {
        this.app = app;
    }

    /**
     * Attempts to connect to the database defined in the String object: url
     */
    public void connect() {
        try {
            Connection connect = DriverManager.getConnection(url);
            System.out.println("Connected to database");
            state = connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Handles authentication for logging in
     *
     * @param user Username of the person attempting to login
     * @param password Password being tested against the password stored in the
     * database
     * @return Returns a ResultSet object containing the user data
     * @throws java.sql.SQLException If SQL query is invalid
     */
    public ResultSet getUser(String user, String password) throws SQLException {
        sql = "SELECT * FROM [USER] WHERE USER_NAME = '" + user + "'";
        result = state.executeQuery(sql);
        return result;
    }
    
    /**
     * Retrieves the USER_ID of the username given as a parameter. Used in the 
     * password reset process
     * @param user The username for which the database will be searched for
     * @return
     * @throws SQLException 
     */
    public int getUserId(String user) throws SQLException {
        sql = "SELECT USER_ID FROM [USER] WHERE USER_NAME='" + user + "'";
        result = state.executeQuery(sql);
        while (result.next()) {
            return result.getInt("USER_ID");
        }
        return 0;
    }
    
    /**
     * This method increments the user's LOGIN_ATTEMPT's field by 1 whenever an
     * incorrect password is submitted.  If the counter is already at 3, the LOCKED 
     * flag is set to true, instead.
     * @param user Username of the account whose attempts is being incremented
     * @throws SQLException 
     */
    public void addLoginAttempt(String user) throws SQLException {
        sql = "UPDATE [USER] SET LOGIN_ATTEMPT=LOGIN_ATTEMPT+1 "
                + "WHERE USER_NAME = '" + user + "'";
        state.executeUpdate(sql);     
    }
    
    /**
     * Resets the LOGIN_ATTEMPTS attribute of the user. Called when the user successfully
     * logs in
     * @param user The user for whom the attempts are being reset
     * @throws SQLException 
     */
    public void resetLoginAttempts(String user) throws SQLException {
        sql = "UPDATE [USER] SET LOGIN_ATTEMPT=0 "
                + "WHERE USER_NAME = '" + user + "'";
        state.executeUpdate(sql);
    }
    
    /**
     * Removes the account lock on a user.  This method is called after the 
     * predetermined amount of time has passed
     * @param user The USERNAME of the user for whom the lock is being removed
     * @throws SQLException 
     */
    public void removeLock(String user) throws SQLException {
        sql = "UPDATE [USER] SET LOGIN_ATTEMPT=0,LOCKED=0, LOCKOUT_DATE=null "
                + "WHERE USER_NAME = '" + user + "'";
        state.executeUpdate(sql);
    }
        
    /**
     * Locks the user account and prevents subsequent login attempts. Called when 
     * the user commits too many incorrect login attempts
     * @param username The username for the user USER for whom the lock is being applied 
     * @throws SQLException 
     */
    public void lockAccount(String username) throws SQLException {
        Date date = new Date();
        sql = "UPDATE [USER] SET LOCKED=1, LOCKOUT_DATE ='" + df.format(date) 
                + "'" + " WHERE USER_NAME='" + username + "'";
        state.executeUpdate(sql);
        app.getLoginView().setLockedFlag(1);
    }
    
    /**
     * Performs the validation for the password reset page
     * @param username The USERNAME of the user attempting to reset password
     * @param userID The USER_ID of the user attempting to reset password
     * @return Returns true if the USERNAME/USER_ID combination is found in DB
     * @throws SQLException Throws error if SQL query is invalid
     */
    public Boolean isUserValid(String username, int userID) throws SQLException {
        Boolean isComboValid = false;
        sql = "SELECT * FROM [USER] WHERE USER_ID=" + userID;
        ResultSet result = state.executeQuery(sql);
        while (result.next()) {
            if (result.getString("USER_NAME").equals(username) && result.getInt("Locked") == 0) {
                isComboValid = true;
            } 
        }
        return isComboValid;
    }
    
    /**
     * Method for obtaining the challenge question associated with the User entity
     * @param id The USER_ID of the user for whom the question is being requested
     * @return Returns a String object of the challenge question
     * @throws SQLException Throws error if SQL query is invalid
     */
    public String getChallengeQuestion(int id) throws SQLException {
        String question = "";
        sql = "SELECT CHALLENGE_QUESTION FROM [USER] WHERE"
                + " USER_ID=" + id;
        ResultSet result = state.executeQuery(sql);
        while (result.next()) {
            question = result.getString("CHALLENGE_QUESTION");
        }
        return question;
    }
    
    /**
     * Method for obtaining the corresponding answer to the challenge question
     * @param id The user_id of the user whose challenge response is being requested
     * @return Returns a String object of the valid challenge response
     * @throws SQLException Throws error if SQL query is invalid
     */
    public String getChallengeResponse(int id) throws SQLException {
        String response = "";
        sql = "SELECT CHALLENGE_ANSWER FROM [USER] WHERE USER_ID=" + id;
        ResultSet result = state.executeQuery(sql);
        while (result.next()) {
            response = result.getString("CHALLENGE_ANSWER");
        }
        return response;
    }
    
    /**
     * Updates the password for a user
     * @param password The new password to be used
     * @param id The user_id of the user whose password is being updated
     * @throws SQLException Throws error is SQL update is invalid
     */
    public void setNewPassword(String password, int id) throws SQLException {
        sql = "UPDATE [USER] SET PASSWORD='" + password + "'" +" WHERE USER_ID="
                + id;
        state.executeUpdate(sql);
    }
    
    /**
     * Creates Image table entry in SQL Server database
     *
     * @param caseId The related CASE_ID for the image being processed
     * @param captureDate The date on which the image was captured
     * @param photographer The USER_ID of the user who captured the image
     * @param processor The USER_ID of the currently logged-in user
     * @param filePath The absolute filepath of the image file
     * @return Returns the next IMAGE_ID seed value
     * @throws java.sql.SQLException Throws exception if the SQL INSERT
     * statement fails
     */
    public int imageEntry(int caseId, String captureDate, int photographer, 
            int processor, String filePath) throws SQLException {
        sql = "INSERT INTO Image (CASE_ID, CAPTURE_DATE, PHOTOGRAPHER, PROCESSED_BY"
                + ", FILE_PATH) "
                + "VALUES (" + caseId + ", '"
                + captureDate + "', " + photographer + ", " + processor
                + ", " + "'" + filePath + "')";
        state.executeUpdate(sql);
        sql = "SELECT MAX(IMAGE_ID) FROM Image";
        result = state.executeQuery(sql);
        while (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
    
    /**
     * Creates Image table entry in the database.  Used when the case id is null
     * @param captureDate The date on which the image was captured
     * @param photographer The USER_ID of the user who captured the image
     * @param processor The USER_ID of the currently logged-in user
     * @param filePath The absolute filepath of the image file
     * @return Returns the next IMAGE_ID seed value
     * @throws java.sql.SQLException Throws exception if the SQL INSERT
     */
    public int imageEntry(String captureDate, int photographer, 
            int processor, String filePath) throws SQLException {
        sql = "INSERT INTO Image (CAPTURE_DATE, PHOTOGRAPHER, PROCESSED_BY"
                + ", FILE_PATH) "
                + "VALUES ('" + captureDate + "', " + photographer + ", " + processor
                + ", " + "'" + filePath + "')";
        state.executeUpdate(sql);
        sql = "SELECT MAX(IMAGE_ID) FROM Image";
        result = state.executeQuery(sql);
        while (result.next()) {
            return result.getInt(1);
        }
        return 0;
    }
    
    /**
     * Used to create the image list which populates the the history log table
     * @return
     * @throws SQLException 
     */
    public ImageList createImageList() throws SQLException {
        ImageList images = new ImageList();
        sql = "SELECT * FROM Image";
        result = state.executeQuery(sql);
        while (result.next()) {
            Image image = new Image(result.getInt(1), result.getInt(2), 
                    result.getString(3), result.getInt(4), result.getInt(5), 
                    result.getString(6), result.getString(7));
            images.addImage(image);
        }
        return images;
    }

    /**
     * Creates OCR entry in the database
     *
     * @param imageId The FK of the Image entry
     * @param result The result of the OCR process
     * @param date The date on which the OCR was completed
     * @param processor The USER_ID of the currently logged-in user
     * @param error Bit field used to indicate if an error occurred in the OCR
     * process
     * @throws SQLException Throws exception if the SQL INSERT statement fails
     */
    public void ocrEntry(int imageId, String result, String date, 
            int processor, int error) throws SQLException {
        sql = "INSERT INTO OCR (IMAGE_ID, OCR_RESULT, OCR_DATE, PROCESSED_BY, OCR_ERROR) "
                + "VALUES (" + imageId + ", '" + result + "', '" + date + "', " + processor + ", " + error + ")";
        state.executeUpdate(sql);
    }
    
    /**
     * Gets the next OCR_ID. Used for creating the .txt filenames for the OCR
     * results
     * @return Returns the number of the next OCR_ID
     * @throws SQLException 
     */
    public int getNextOcrId() throws SQLException {
        sql = "SELECT MAX(OCR_ID) FROM OCR";
        ResultSet ocrID = state.executeQuery(sql);
        while (ocrID.next()) {
            return ocrID.getInt(1) + 1;
        }
        return 1;
    }
    
    /**
     * Used to populate the fields in the detail screen.  
     * @param imageId The IMAGE_ID for which the details are being requested
     * @return Returns a String array containing all the needed results
     * @throws SQLException 
     */
    public String[] getRecordDetails(int imageId) throws SQLException {
        String[] results = new String[6];
        sql = "SELECT Image.CAPTURE_DATE, Decrypt.DECRYPT_DATE, Cipher.CIPHER_NAME, " +
                "Decrypt.RATING, DECRYPT.RESULT, Image.ADDITIONAL_NOTES " 
                + "FROM Image JOIN OCR ON Image.IMAGE_ID = OCR.IMAGE_ID "
                + "JOIN DECRYPT ON OCR.OCR_ID = DECRYPT.OCR_ID " 
                + "JOIN CIPHER ON DECRYPT.CIPHER = CIPHER.CIPHER_ID WHERE "
                + "Image.IMAGE_ID = " + imageId;
        result = state.executeQuery(sql);
        while (result.next()) {
            results[0] = result.getString(1);
            results[1] = result.getString(2);
            results[2] = result.getString(3);
            results[3] = result.getString(4);
            results[4] = result.getString(5);
            results[5] = result.getString(6);
        }
        return results;
    }
    
    /**
     * Method to save notes to the ADDITIONAL_NOTES attribute of images being
     * viewed in the detail screen
     * @param notes The notes being saved to the image entry
     * @param imageId The IMAGE_ID of the image currently being viewed
     * @return Returns true if the notes are successfully saved
     * @throws SQLException 
     */
    public Boolean saveNotes(String notes, int imageId) throws SQLException {
        Boolean notesSaved = false;
        sql = "UPDATE IMAGE SET ADDITIONAL_NOTES='" + notes + "' WHERE IMAGE_ID"
                + "=" + imageId;
        if (state.executeUpdate(sql) != 0) {
            notesSaved = true;
        }
        return notesSaved;
    }
    
    public void writeDecryptResult(String result, String language, int ocrId) {
        
    } 
}
