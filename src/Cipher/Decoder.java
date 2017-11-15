package Cipher;

import java.util.ArrayList;

/**
 *
 * @author Stephanie
 */
public class Decoder implements Runnable {

    String decryptedMessage = "";
    ArrayList<String> result;
    ArrayList<ArrayList<String>> resultPool;
    Caesar caesarCipher;
    Atbash atbashCipher;
    Affine affineCipher;
    Baconian baconCipher;

    public ArrayList<ArrayList<String>> decryptMessage(String informedCode) {

        resultPool = new ArrayList();
        caesarCipher = new Caesar();
        atbashCipher = new Atbash();
        affineCipher = new Affine();
        baconCipher = new Baconian();

        result = caesarCipher.decryptCaesar(informedCode);
        resultPool.add(result);
        result = atbashCipher.decryptAtbash(informedCode);
        resultPool.add(result);
        //result = affineCipher.decryptAffine(informedCode);
        //resultPool.add(result);
        result = baconCipher.decryptBaconian(informedCode);
        resultPool.add(result);

        return resultPool;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
