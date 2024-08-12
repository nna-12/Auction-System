package AuctionSystem;

import java.util.*;
import java.io.*;

class InvalidPasswordException extends Exception {
  public InvalidPasswordException(String m) {
    super(m);
  }
}

public class Signup {
  String username, password;
  private String dob, address,name;
  private int user;

  public Signup() {
    this.username = "";
    this.password = "";
  }

  public Signup(String un, String pw) {
    this.username = un;
    this.password = pw;
    checkPassword(pw);
  }

  public int checkIfUsernameExists() {
    try {
      // File f = new File ("/home/cseb2/OOPS Lab/MiniProject/logininfo.txt");
      File f = new File("logininfo.txt");
      Scanner sc = new Scanner(f);
      String line;
      while (sc.hasNextLine()) {
        line = sc.nextLine();
        for (int i = 0; i < line.length(); i++) {
          if (line.substring(0, line.indexOf(",")).equals(this.username)) {
            sc.close();
            return 1;
          }
        }
      }
      sc.close();
      return 0;
    } catch (Exception e) {
      System.out.println("File not found.");
      System.exit(0);
    }
    return 0;
  }

  static int pwtries = 0;
  public void checkPassword(String pw) {
    try {
      if (pw.length() < 8)
        throw new InvalidPasswordException("INVALID PASSWORD.");
      else {
        int capscount = 0, digitcount = 0, smallcount = 0;
        for (int i = 0; i < pw.length(); i++) {
          if (pw.charAt(i) >= 65 && pw.charAt(i) <= 90)
            capscount++;
          if (pw.charAt(i) >= 48 && pw.charAt(i) <= 57)
            digitcount++;
          if (pw.charAt(i) >= 97 && pw.charAt(i) <= 122)
            smallcount++;
        }
        if (capscount == 0 || digitcount == 0 || smallcount == 0) {
          throw new InvalidPasswordException("INVALID PASSWORD.");
        } else {
          //System.out.println ("\nValid password.\n");
          this.password = pw;
        }
      }
    } catch (InvalidPasswordException e) {
      pwtries++;
      System.out.println("Try: "+pwtries + "     ");
      System.out.println(e.getMessage());
      if (pwtries < 3) {
        if (pwtries == 2)
          System.out.println("\nLast try.");
        System.out.print("\nEnter a new password: ");
        Scanner in = new Scanner(System.in);
        String newpw = in.nextLine();
        checkPassword(newpw);
      } else {
        System.out.println("\n\nYou have tried too many times. Exiting...");
        System.exit(0);
        return;
      }
    }
  }

  public void getDetails() {
    System.out.println("Valid username and password ! Continue the signup process ...");
    Scanner sc = new Scanner(System.in);
    System.out.print("\n\nEnter Name: ");
    this.name = sc.nextLine();
    System.out.print("Enter Date of Birth: ");
    this.dob = sc.nextLine();
    System.out.print("Enter Address: ");
    this.address = sc.nextLine();
    System.out.print("Enter type of User (1-Buyer,2-Seller): ");
    this.user = sc.nextInt();
    sc.nextLine();
  }

  public void enterInFile() {
    try {
      // FileWriter in = new FileWriter ("/home/cseb2/OOPS
      // Lab/MiniProject/logininfo.txt",true);
      FileWriter in = new FileWriter("logininfo.txt", true);
      in.write(this.username);
      in.write(",");
      in.write(this.password);
      in.write("\n");
      in.close();
      FileWriter f2 = new FileWriter("details.txt", true);
      f2.write(Integer.toString(this.user));
      f2.write(",");
      f2.write(this.name);
      f2.write(",");
      f2.write(this.dob);
      f2.write(",");
      f2.write(this.address);
      f2.write (", , , , , , ");
      f2.write("\n");
      f2.close();
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }

    System.out.println("\nSignup completed !\n");
  }
}
