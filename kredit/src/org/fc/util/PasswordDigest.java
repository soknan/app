package org.fc.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordDigest
{
  public static byte[] getDigest(byte[] password, byte[] salt, int iterationNo)
    throws Exception
  {
    byte[] hash = null;
    MessageDigest digester = MessageDigest.getInstance("SHA-512");
    for (int i = 0; i < iterationNo; i++)
    {
      digester.reset();
      digester.update(password);
      digester.update(salt);
      
      hash = digester.digest();
    }
    return hash;
  }
  
  public static byte[] generateSalt()
    throws Exception
  {
    byte[] salt = null;
    
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    salt = new byte[16];
    random.nextBytes(salt);
    
    return salt;
  }
}
