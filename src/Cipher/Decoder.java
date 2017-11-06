package Cipher;
/**
 *
 * @author Stephanie
 */
public class Decoder {

    public String decryptMessage(String informedcode) {
        String message, decryptedMessage = "";
        String allResults;
        String result;
        StringBuilder resultPool;
        Caesar caesarCipher;
        Atbash atbashCipher;
        
        allResults = new String("");
        
        message = informedcode;
        caesarCipher = new Caesar();
        atbashCipher = new Atbash();
        
        result = caesarCipher.decryptCaesar(message);
        resultPool = new StringBuilder(result);
        result = atbashCipher.decryptAtbash(informedcode);
        resultPool.append(result);
        
        
        allResults = resultPool.toString();
        
        
        
    return allResults;
}}
