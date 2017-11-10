package Cipher;

import java.util.Locale;
import javax.swing.JTextArea;

/**
 *
 * @author Stephanie
 */
public class Affine {

    int dy1 = 0, dy2 = 0, dy3 = 0;
    String message, decryptedMessage, finalResult, language, langName;
    char character, decryptCharacter;
    int valA[], valB[], valInvVarA[];
    StringBuilder result;
    GoogleTranslate trans;
    JTextArea allResults;
    int varA, varB, varbX, varM, inv, invVarA, c = 0;

    public String decryptAffine(String informedcode) {

        message = informedcode.toLowerCase();
        decryptedMessage = new String("");
        finalResult = new String("");
        language = new String("");
        langName = new String("");
        result = new StringBuilder();
        trans = new GoogleTranslate();
        allResults = new JTextArea("");
        valA = new int[]{1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        valInvVarA = new int[]{1, 9, 21, 15, 3, 19, 7, 23, 11, 5, 17, 25};

//x = a^-1 (c - b) (mod m)
        for (int h = 0; h < valA.length; ++h) {

            varA = valA[h];
            invVarA = valInvVarA[h];

            for (int j = 0; j < 26; j++) {
                varB = j;
                decryptedMessage = "";

                for (int k = 0; k < message.length(); k++) {
                    /*Applying decryption formula a^-1 ( c - b ) mod m 
          
                     */
                    character = message.charAt(k);
                    if (character == 'a') {
                        c = 0;
                    } else if (character == 'b') {
                        c = 1;
                    } else if (character == 'c') {
                        c = 2;
                    } else if (character == 'd') {
                        c = 3;
                    } else if (character == 'e') {
                        c = 4;
                    } else if (character == 'f') {
                        c = 5;
                    } else if (character == 'g') {
                        c = 6;
                    } else if (character == 'h') {
                        c = 7;
                    } else if (character == 'i') {
                        c = 8;
                    } else if (character == 'j') {
                        c = 9;
                    } else if (character == 'k') {
                        c = 10;
                    } else if (character == 'l') {
                        c = 11;
                    } else if (character == 'm') {
                        c = 12;
                    } else if (character == 'n') {
                        c = 13;
                    } else if (character == 'o') {
                        c = 14;
                    } else if (character == 'p') {
                        c = 15;
                    } else if (character == 'q') {
                        c = 16;
                    } else if (character == 'r') {
                        c = 17;
                    } else if (character == 's') {
                        c = 18;
                    } else if (character == 't') {
                        c = 19;
                    } else if (character == 'u') {
                        c = 20;
                    } else if (character == 'v') {
                        c = 21;
                    } else if (character == 'w') {
                        c = 22;
                    } else if (character == 'x') {
                        c = 23;
                    } else if (character == 'y') {
                        c = 24;
                    } else if (character == 'z') {
                        c = 25;
                    }

                    System.out.println(varA + " " + varB);
                    dy1 = (invVarA * (c - varB)) % 26;
                    System.out.println(dy1);
                    if (dy1 < 0) {
                        dy1 = dy1 + 26;
                    }
                    dy1 = dy1 + 'a';
                    decryptedMessage = decryptedMessage + (char) (dy1);
                }

                System.out.println(decryptedMessage);

                language = trans.detectLanguage(decryptedMessage);

                Locale loc = new Locale(language);
                langName = loc.getDisplayLanguage(loc);

                finalResult = trans.runProcess(language, decryptedMessage, langName);

                if ("LOW RATIO".equals(finalResult)) {
                    allResults.append("");
                } else {
                    allResults.append("Affine Cipher Key [" + varA + "," + varB + "]" + ":\n" + "Decrypted Message: " + decryptedMessage + "\n"
                            + "Language: " + langName + "\n" + " - FINAL RESULT: " + finalResult + "\n" + "\n" + "\n");
                }
            }
        }
        return allResults.getText();
    }
}
