/**ZooAuthenticationSystem.java**/

import java.io.File;
import java.security.MessageDigest;

import java.util.Scanner;

public class ZooAuthenticationSystem

{

     public static void main(String[] args) throws Exception

        {

         //Create a scanner object to read input from the console

             Scanner readInput=new Scanner(System.in);

             //Declare a variable to keep track the number of attempts

             int attempts=0;

             /*Repeat until a successful attempt has been made or

             three unsuccessful attempts have been made or

             a user chooses to exit*/

             while(true)

             {

                 //Ask the user for a username

                 System.out.print("Enter user name: ");

                 String uName=readInput.nextLine();

                 //Ask the user for a password

                 System.out.print("Enter password: ");  

                 String original = readInput.nextLine();

                 //Convert the password using a SHA-512 hash

                 MessageDigest md = MessageDigest.getInstance("SHA-512");

        		md.update(original.getBytes());
        		byte[] hash = md.digest(original.getBytes());
        	
             StringBuffer sb = new StringBuffer();
             for(int i=0; i < hash.length ; i++) {
            	 sb.append(Integer.toString((hash[i] &0xff) + 0x100, 16).substring(1));
             }
         		

                 //Declare a boolean variable to keep track whether

                 //a login is successful or not

                 boolean authenticationSuccess=false;

                 //Open credentials file

                 Scanner readCred=new Scanner(new File("Credentials.txt"));

                 //Search for the user credentials in the credentials file

                 while(readCred.hasNextLine())

                 {

                      String record=readCred.nextLine();//Read a record

                      String columns[]=record.split("\t");//Split the record into individual fields

                      /*Check the credentials against the valid credentials

                      provided in the credentials file*/

                      //Check user name.

                      if(columns[0].trim().equals(uName))

                      {

                           /*If user name is matched, check whether the Converted password using

                           a message digest five password with hashed password in the second column*/

                           if(columns[1].trim().equals(sb.toString()))//Check password

                           {

                                //If the passwords are same, set the boolean value

                                //AuthenticationSuccess to true

                                authenticationSuccess=true;//Login success

                                //Open the role file

                                Scanner readRole=new Scanner(new File(columns[3].trim()+".txt"));

                                //Display the information stored in the role file

                                while(readRole.hasNextLine())

                                {

                                     System.out.println(readRole.nextLine());

                                }

                                break;

                           }

                      }

                 }

                 //If login successful, ask the user whether the user wants to log out or not

                 if(authenticationSuccess)

                 {

                      System.out.println("Do you want to log out(y/n): ");

                      String choice=readInput.nextLine();

                      //If user wants to log out, exit the system.

                      if(choice.toLowerCase().charAt(0)=='y')

                      {
                    	   readInput.close();
                           System.out.println("Successfully logged out.");

                           break;

                      }

                      //If user wants to continue, set the boolean value

                      //uthenticationSuccess to true for new login

                      else

                      {

                           authenticationSuccess=false;

                      }

                 }

                 //If login is not successful, update the number of attempts

                 else

                 {

                      attempts++;

                      //If maximum attempts reached, notify the user and exit the program

                      if(attempts==3)

                      {
                    	   readInput.close();
                           System.out.println("Maximum attempts reached!\nExiting...");
                           break;

                      }

                      //otherwise, prompt to enter credentials again

                      else

                      {

                           System.out.println("Please enter correct credentials!");     

                      }

                 }

             }

        }

}