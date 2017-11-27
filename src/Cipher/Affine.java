package Cipher;

import java.util.ArrayList;
import java.util.Locale;
/**
 *
 * @author Stephanie
 */
public class Affine {

    int dy1 = 0, dy2 = 0, dy3 = 0;
    String message, decryptedMessage, finalResult, language, langName;
    char character, decryptCharacter;
    int valA[], valB[], valInvVarA[];
    String result;
    GoogleTranslate trans;
    ArrayList<String> allResults;
    int varA, varB, varbX, varM, inv, invVarA, c = 0;

    public Affine() {
        
        decryptedMessage = "";
        finalResult = "";
        language = "";
        langName = "";
        result = "";
        trans = new GoogleTranslate();
        allResults = new ArrayList();
        valA = new int[]{1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        valInvVarA = new int[]{1, 9, 21, 15, 3, 19, 7, 23, 11, 5, 17, 25};
    }
    
    public ArrayList<String> decryptAffine(String informedcode) {
        message = informedcode.toLowerCase();
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
                    switch (character) {
                        case 'a':
                            c = 0;
                            break;
                        case 'b':
                            c = 1;
                            break;
                        case 'c':
                            c = 2;
                            break;
                        case 'd':
                            c = 3;
                            break;
                        case 'e':
                            c = 4;
                            break;
                        case 'f':
                            c = 5;
                            break;
                        case 'g':
                            c = 6;
                            break;
                        case 'h':
                            c = 7;
                            break;
                        case 'i':
                            c = 8;
                            break;
                        case 'j':
                            c = 9;
                            break;
                        case 'k':
                            c = 10;
                            break;
                        case 'l':
                            c = 11;
                            break;
                        case 'm':
                            c = 12;
                            break;
                        case 'n':
                            c = 13;
                            break;
                        case 'o':
                            c = 14;
                            break;
                        case 'p':
                            c = 15;
                            break;
                        case 'q':
                            c = 16;
                            break;
                        case 'r':
                            c = 17;
                            break;
                        case 's':
                            c = 18;
                            break;
                        case 't':
                            c = 19;
                            break;
                        case 'u':
                            c = 20;
                            break;
                        case 'v':
                            c = 21;
                            break;
                        case 'w':
                            c = 22;
                            break;
                        case 'x':
                            c = 23;
                            break;
                        case 'y':
                            c = 24;
                            break;
                        case 'z':
                            c = 25;
                            break;
                        default:
                            break;
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
                allResults.add(decryptedMessage);
                /*language = trans.detectLanguage(decryptedMessage);

                Locale loc = new Locale(language);
                langName = loc.getDisplayLanguage(loc);

                finalResult = trans.runProcess(language, decryptedMessage, langName);

                if ("LOW RATIO".equals(finalResult)) {
                    allResults.add("");
                } else {
                    allResults.add("Affine Cipher Key [" + varA + "," + varB + "]" + ":\n" + "Decrypted Message: " + decryptedMessage + "\n"
                            + "Language: " + langName + "\n" + " - FINAL RESULT: " + finalResult + "\n" + "\n" + "\n");
                }*/
            }
        }
        return allResults;
    }
}
