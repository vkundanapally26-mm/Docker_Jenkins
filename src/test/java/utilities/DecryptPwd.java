package utilities;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DecryptPwd {
	
	public static String decrypt(String strToDecrypt){
		try{
			 final String SECRET_KEY = "Test123@#$%^&*()DX";
			 final String SALT= "TESTING!!!!!!!";
			 //Default byte array
			 byte[] iv={ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			 // Create IvparameterSpec object and assign with constructor
			 IvParameterSpec ivspec= new IvParameterSpec(iv);
			 //create SecretKeyFactory object
			 SecretKeyFactory factory= SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			 // Create KeySpec object and assign with constructor
			 KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(),SALT.getBytes(),65536, 256);
			 SecretKey tmp =factory.generateSecret(spec);
			 SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
			 Cipher cipher= Cipher.getInstance("AES/CBC/PKCS5Padding");
			 cipher.init(Cipher.DECRYPT_MODE, secretKey,ivspec);
			 //Return decrypted string 
			 return new String (cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		}catch (Exception e){
			System.out.println("Error while decryting :"+ e.toString());
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.print(decrypt("xoYO0rOyc3xlZpFF9d6JwA=="));
	}

}
