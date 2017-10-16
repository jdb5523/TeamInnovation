package Model;

public final class GuideText {
    
    private static final GuideText instance = new GuideText();
    
    private static String intro;
    private static String login;
    private static String reset;
    private static String main;
    private static String ocr;
    private static String importing;
    private static String decrypt;
    private static String save;
    
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

    public String getMain() {
        return main;
    }

    public String getOcr() {
        return ocr;
    }

    public String getImporting() {
        return importing;
    }

    public String getDecrypt() {
        return decrypt;
    }

    public String getSave() {
        return save;
    }
    
    public static GuideText getInstance() {
        return instance;
    }
    
}
