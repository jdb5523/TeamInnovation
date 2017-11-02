package Cipher;

import java.util.Locale;
import javax.swing.*;

public class Caesar {

    public String decryptCaesar(String informedcode) {
        String message, decryptedMessage = "", transMessage, finalTrans, check;
        int key, k;
        double ratio, ratTotal, length;
        GoogleTranslate trans;
        String language;
        JTextArea allResults;

        message = informedcode;
        allResults = new JTextArea("");
        trans = new GoogleTranslate();
        language = new String("");
        transMessage = new String("");
        String langName = new String("");
        StringBuilder resultPool = new StringBuilder("");
        finalTrans = new String("");
        check = new String("");
        ratio = 0;
        k = 0;
        ratTotal = 0;
        length = 0;
        
        
        language = trans.detectLanguage(message);
            
            Locale loc = new Locale(language);
            langName = loc.getDisplayLanguage(loc);
            
           if ("English".equals(langName))
           {
               String[] splitMessage = message.split("\\s+");
                length = splitMessage.length;
                System.out.println(length);
            
                for (int i = 0; i < splitMessage.length; i++) {
          
                check = trans.checkEnglish(splitMessage[i]);
                System.out.println(check);
                
                    if("WORD FOUND".equals(check)){
                    ratTotal++;}
                
                resultPool.append(transMessage+" ");
                k++;
                }
               
               System.out.println(ratTotal);
               ratio = ratTotal/length;
               System.out.println(ratio);
               finalTrans = message.toUpperCase();
           }
           else
           {
            String[] splitMessage = message.split("\\s+");
            System.out.println(splitMessage.length);
            
            k = 0;
            
            for (int i = 0; i < splitMessage.length; i++) {
                
                transMessage = trans.translateLanguage(splitMessage[k], language);
                resultPool.append(transMessage+" ");
                k++;
                }
            
            finalTrans = resultPool.toString();
            finalTrans = finalTrans.toUpperCase();
            }

        allResults.append("Language: "+ langName +" - Message: "+finalTrans);
/*
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
                language = trans.detectLanguage(decryptedMessage);

            }
            allResults.append("Caesar Key " + key + ":\n" + decryptedMessage + "\n");
        }
//}
        message = allResults.getText();
       */ return allResults.getText();
    }
    
}
