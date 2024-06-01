package utils;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptUtils {

  public static String encryptValue(String encryptedValue) {
    return BCrypt.hashpw(encryptedValue, BCrypt.gensalt());
  }

  public static boolean isValueEqualToEncrypted(
    String comparedValue,
    String encryptedValue
  ) {
    return BCrypt.checkpw(comparedValue, encryptedValue);
  }
}
