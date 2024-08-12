package Userlogin;

import java.util.*;
import java.io.*;

interface User {
  public int getUser();
  public String getUserType();
  public String getName();
  public String getDob();
  public String getAddress();
  public void setName(String name);
  public void setDob(String dob);
  public void setAddress(String address);
}

public class Login implements User {
  private String username, password;
  private String name = "", address = "", dob = "";
  private int user;
  private String userType;
  int lineindex = -1;
  String nl = "", line = "";
  String arr[];

  public Login(){
    this.username=null;
    this.password=null;
  }
  
  public Login(String un, String pw) {
    this.username = un;
    this.password = pw;
  }

  public int loginCheck() {
    try {
      File f = new File("logininfo.txt");
      Scanner sc = new Scanner(f);
      int i = 0;
      while (sc.hasNextLine()) {
        line = sc.nextLine();
        if (line.substring(0, line.indexOf(",")).equals(this.username)) {
          lineindex = i;
          nl = line;
          break;
        }
        i++;
      }
    } catch (Exception e) {
      System.out.println("File not found.");
    }
    if (lineindex == -1){
      System.out.println("Sorry, this username does not exist");
      return 0;
    }
    else {
      arr = nl.split(",");
      if (this.password.equals(arr[1]))
        return 1;
        //System.out.println("Login successful !");
      else
        return 0;
        //System.out.println("Incorrect password");
    }
  }

  public void readDetails() {
   try {
      File f = new File("details.txt");
      Scanner sc = new Scanner(f);
      int i = 0;
      while (sc.hasNextLine()) {
        line = sc.nextLine();
        if (i == lineindex) {
          //System.out.println(lineindex + " " + line);
          arr = line.split(",");
          break;
        }
        i++;
      }
    } catch (Exception e) {
      System.out.println("File not found.");
    }
    this.user = Integer.parseInt(arr[0]);
    if(user==1)
      userType="Buyer";
    else if(user==2)
      userType="Seller";
    else
      userType="Admin";
    
    this.name = arr[1];
    this.dob = arr[2];
    this.address = arr[3];
  }

  public int getUser() {
    return this.user;
  }
  
  public String getUserType() {
    return this.userType;
  }

  public String getName() {
    return this.name;
  }

  public String getDob() {
    return this.dob;
  }

  public String getAddress() {
    return this.address;
  }
  public void setName(String name) {
    this.name = name;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public void setAddress(String address) {
    this.address = address;
  }
  
  
}
