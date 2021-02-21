import java.security.MessageDigest;


public class SHA512 {

	public static void main(String[] args) throws Exception {
      
      //Copy and paste this section of code
		String original = "letmein";  //Replace "password" with the actual password input by the user
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(original.getBytes());
		byte[] hash = md.digest(original.getBytes());
	
		
	
     StringBuffer sb = new StringBuffer();
     for(int i=0; i < hash.length ; i++) {
    	 sb.append(Integer.toString((hash[i] &0xff) + 0x100, 16).substring(1));
     }

		System.out.println("original:" + original);
		System.out.println("digested:" + sb.toString()); //sb.toString() is what you'll need to compare password strings

	}

}