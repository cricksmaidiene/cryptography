import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.*;

class DesEncrypter {
  
  Cipher enc_cipher;
  Cipher dec_cipher;

  DesEncrypter(SecretKey key) throws Exception {
    
    enc_cipher = Cipher.getInstance("DES");
    dec_cipher = Cipher.getInstance("DES");
    
    enc_cipher.init(Cipher.ENCRYPT_MODE, key);
    dec_cipher.init(Cipher.DECRYPT_MODE, key);
  
  }

  public String encrypt(String str) throws Exception {
    
    byte[] utf8 = str.getBytes("UTF8");
    byte[] enc = enc_cipher.doFinal(utf8);
    return new sun.misc.BASE64Encoder().encode(enc);
  
  }

  public String decrypt(String str) throws Exception {
    
    byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
    byte[] utf8 = dec_cipher.doFinal(dec);
    return new String(utf8, "UTF8");
  
  }
}

public class DESDriver {
  
  public static void main(String[] argv) throws Exception {
    
    SecretKey key = KeyGenerator.getInstance("DES").generateKey();
    DesEncrypter encrypter = new DesEncrypter(key);

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the Plaintext");
    String s = sc.nextLine();

    String encrypted = encrypter.encrypt(s);
    System.out.println("ENCRYPTED TEXT: "+encrypted);
    String decrypted = encrypter.decrypt(encrypted);
    System.out.println("DECRYPTED TEXT: "+decrypted);
  
  }
}