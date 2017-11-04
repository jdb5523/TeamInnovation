package Cipher;

import java.util.Locale;
import javax.swing.*;


public class Caesar {

    public String decryptCaesar(String informedcode) {
        String message, decryptedMessage = "", finalResult, loopInfo;
        int key;
        char character;
        GoogleTranslate trans;
        String language;
        JTextArea allResults;


        message = informedcode;
        allResults = new JTextArea("");
        trans = new GoogleTranslate();
        language = new String("");
        String langName = new String("");
        StringBuilder resultPool = new StringBuilder("");
        finalResult = new String("");

        
        for (key = 1; key < 27; ++key) {
            decryptedMessage = "";
            for (int i = 0; i < message.length(); ++i) {
                
                character = message.charAt(i);
                if (character >= 'a' && character <= 'z') {
                    character = (char) (character - key);
                    if (character < 'a') {
                        character = (char) (character + 'z' - 'a' + 1);
                    }

                    decryptedMessage += character;
                } else if (character >= 'A' && character <= 'Z') {
                    character = (char) (character - key);

                    if (character < 'A') {
                        character = (char) (character + 'Z' - 'A' + 1);
                    }

                    decryptedMessage += character;
                } else {
                    decryptedMessage += character;
                } 

            }          
            
            language = trans.detectLanguage(decryptedMessage);
            
            Locale loc = new Locale(language);
            langName = loc.getDisplayLanguage(loc);
            
            finalResult = trans.runProcess(language, decryptedMessage, langName);

            if ("LOW RATIO".equals(finalResult))
            {
                allResults.append("");
            }
            else{
                allResults.append("Caesar Cipher Key " + key + ":\n"+"Decrypted Message: " + decryptedMessage + "\n"
                    + "Language: "+ langName +"\n"+" - FINAL RESULT: "+ finalResult+"\n"+"\n"+"\n");
            }
            
    }
       return allResults.getText();
    }
    
}
