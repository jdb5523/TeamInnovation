package Model;

public final class GuideText {
    
    private static final GuideText instance = new GuideText();
    
    private static String intro;
    private static String login;
    private static String reset;
    private static String option;
    private static String image;
    private static String ocr;
    private static String history;
    private static String decrypt;
    private static String detail;
    
    public GuideText() {
        initialize();
    }
    
    public void initialize() {
        intro = "Nautilus is an application designed to make the process of decrypting code easisimpler."
                + "  It streamlines the entire process from importing images to performing the decryption "
                + "algorithims.\n\nResults from the decryption process can be saved for convenient viewing"
                + " and analysis at a later time.\n\nSelect a help item on the list to the left to see"
                + " more detailed information on each topic.";
        login = "The login screen allows authorized users to access and utilize Nautilus.  Authorization"
                + " is grante upon the entering of valid credentials (username and password).\n\nIt should be noted "
                + "four invalid attempts of logging in will lock the account.  The account will remain locked for 24 hours"
                + " or until the sysadmin manually unlocks the account.\n\nIf you have forgotten your password, select the"
                + "'Reset Password' option to verify your identity and select a new password." ;
        reset = "If you have forgotten or would otherwise like to update your password, you may do so "
                + "from this screen.\n\nFirst, you will need to enter your valid username/"
                + "user_id combination.\n\nNext, you will be prompted to respond to your"
                + " previously defined challenge question with the correct answer.  "
                + "\n\nIf you respond correctly, you will be allowed to enter in a new password"
                + " which you must also confirm by reentering it.\n\nOnce the new password is"
                + " confirmed, you will be able to use it immediately after to login.";
        option = "The option menu allows you to either begin a new decryption process, or to view"
                + " decryption results from previous processes.";
        image = "The image processing/selection screen allows you to begin the process of decryption"
                + " by choosing the image file location and entering related data into the provided"
                + " fields.\n\nRequired fields:\n-File: You may manually type in the file"
                + " path to the .JPEG image or select the 'Browse' button to open the file selection"
                + " dialog.\n\n-Image Capture Date: Enter the date when the image was taken.  If "
                + "not known, the EXIF data of the image may contain the date information.\n\n"
                + "-Photographer User ID: Enter the User ID of the user who captured the picture.\n\n"
                + "Optional Field:\n-Reference Case ID: You may enter in the Case ID if this image"
                + " is associated with an open case.";
        ocr = "When the required fields on the image selection page are filled out, and the 'Process' "
                + "button is selected, Tesseract OCR will automatically begin processing the image.\n\n"
                + "Additionally, two database entries will be created:\n1. An image record is inserted "
                + "which contains the information entered on the image selection screen.\n2. An OCR record"
                + " is inserted which contains the results of the OCR process and other automatically"
                + " generated information.";
        history = "The Historical Log retrieves and displays past records from the database. Records are "
                + "pulled from the Decrypt table. You may enter in a search query to obtain specific "
                + "results.";
        detail = "The Detail page can also be accessed from here. The detail page provides " 
                + "more specific information on each record.\n\nFrom this screen, additional notes may be added "
                + "to the image record currently being viewed.";
        decrypt = "After an image file has been selected and successfully processed by Tesseract, the "
                + "coded text will be ready for decryption.  Optionally, the user may make adjustments "
                + "to the text in the Input window before decrypting.\n\nOnce the Decrypt button is selected "
                + ", the algorithms will begin to decode the text.  Output will be shown in the Output "
                + "window. The Google Language API can determine the language of the decrypted text.\n\n"
                + "Once decryption is finished, separate database records will be created for the result of "
                + "each cipher.\n\nThe User Guide and Historical Log can also be accessed from this screen.";
    }

    public String getIntro() {
        return intro;
    }

    public String getLogin() {
        return login;
    }

    public String getReset() {
        return reset;
    }

    public String getImage() {
        return image;
    }

    public String getOcr() {
        return ocr;
    }

    public String getHistory() {
        return history;
    }

    public String getDecrypt() {
        return decrypt;
    }

    public String getDetail() {
        return detail;
    }
    
    public static GuideText getInstance() {
        return instance;
    }

    public String getOption() {
        return option;
    }
    
}
