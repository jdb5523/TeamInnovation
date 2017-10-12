package Cipher;

public class Decoder {

    public String decryptMessage(String informedcode) {
        String message, decryptedMessage = "";
        String allResults;
        String result;
        StringBuilder resultPool;
        Caesar caesarCipher;
        Caesar caesarCipher2;
        
        allResults = new String("");
        
        message = informedcode;
        caesarCipher = new Caesar();
        caesarCipher2 = new Caesar();
        
        result = caesarCipher.decryptCaesar(message);
        resultPool = new StringBuilder(result);
        
        result = caesarCipher2.decryptCaesar(message);
        resultPool.append(result);
        
        allResults = resultPool.toString();
        
        
        
    return allResults;
}}
