package Model;

public final class AboutText {
    
    private static final AboutText INSTANCE = new AboutText();
    private static String about;
    
    private AboutText() {
        initialize();
    }
    
    public void initialize() {
        about = "Nautilus Decryption Application ver. 0.1a"
                + "\nDeveloped by Team Innovation (2017)\n\n"
                + "NetBeansIDE and NetBeans platform is dual licensed under the Common "
                + "Development and Distribution License (CDDL) and the GNU General Public "
                + "License 2 with Classpath exception"
                + "\n\nTesseract OCR is licensed under the Apache License, "
                + "Version 2.0\nhttp://www.apache.org/licenses/LICENSE-2.0"
                + "\nRepository: https://github.com/tesseract-ocr/tesseract\n\n"
                + "SQL Server 2016 by Microsoft © 2017 Microsoft Corporation\nLicensing "
                + "provided by Kivuto Solutions";
    }
    
    public String getAbout() {
        return about;
    }
    public static AboutText getInstance() {
        return INSTANCE;
    }
}
