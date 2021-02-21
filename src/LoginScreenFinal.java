import java.io.File;
import java.security.MessageDigest;
import java.util.Scanner;

public class LoginScreenFinal{
     public static void main(String[] args) throws Exception{
     
            //Obtain and read user input
             Scanner userInput = new Scanner(System.in);
             
            //variable for tracking login attempts 
             
             int authAttempts = 0;
             
            //Prompt user until 3 failed attempts or user logs out
            
             while(true){
             
                 //Prompt for username and password, read user's input
                 
                 System.out.print("Enter username: ");
                 String userName = userInput.nextLine();
                 System.out.print("Enter password: ");  
                 
                 // Code for converting password input into SHA-512 hash
                 
                 String original = userInput.nextLine();
                 MessageDigest md = MessageDigest.getInstance("SHA-512");
                 md.update(original.getBytes());

        		byte[] hash = md.digest(original.getBytes());
        	
             StringBuffer sb = new StringBuffer();
             for(int i = 0; i < hash.length ; i++) {
            	 sb.append(Integer.toString((hash[i] &0xff) + 0x100, 16).substring(1));
             }
                 
                 //Tracking whether or not user login is confirmed
                 
                 boolean loginConfirm=false;
                 
                 //Open the credentials file to be scanned
                 
                 Scanner verLogin = new Scanner(new File("credentials.txt"));
                 
                 //Loop for reading the text file and finding user's entry
                 while(verLogin.hasNextLine()){
                      String credEntry = verLogin.nextLine();
                      
                      //establishing data in columns by separating each line with a tab
                      String credColumns[] = credEntry.split("\t");
      
                      //Check for user name match
                      if(credColumns[0].trim().equals(userName)){
                      
                           //Check for hashed password
                           if(credColumns[1].trim().equals(sb.toString())){
                                 
                                //if hash password matches, login is confirmed
                                loginConfirm=true;
                                
                                //Open the text file that corresponds to the user's role
                                Scanner userRole=new Scanner(new File(credColumns[3].trim()+".txt"));
                                
                                //Print text file for user
                                while(userRole.hasNextLine()){
                                     System.out.println(userRole.nextLine());
                                }
                                break;
                           }
                      }
                 }
                 //Loop if user is currently logged in
                 if(loginConfirm){
                      
                      //Does user want to log out?
                      System.out.println("Do you want to log out? (y/n): ");
                      String logoutPrompt = userInput.nextLine();
                      
                      //If user types y, log out
                      if(logoutPrompt.toLowerCase().charAt(0)=='y'){
                    	   userInput.close();
                           System.out.println("Logging out.");
                           break;
                      }
                      
                      //Otherwise repeat loop
                      else{
                           loginConfirm=false;
                      }
                 }
                 //If login attempt fails, add 1 to authAttempts
                 else{
                      authAttempts++;
                      
                      //After 3 failed attempts, quit program
                      if(authAttempts==3){
                    	   userInput.close();
                           System.out.println("Three Authentication Attempts Failed.\nQuitting program.");
                           break;
                      }
                      //otherwise, prompt to enter credentials again
                      else{
                           System.out.println("Please enter your username and password");     
                      }
                 }
             }
        }
}