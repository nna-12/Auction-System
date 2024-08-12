import AuctionSystem.*;
import Userlogin.*;
//items = item name, category, seller, price, winning bid, leading bidder
import java.util.*;
import java.io.*;

public class AuctionDemo 
{
  public static void main(String[] args) 
  {
    Scanner in = new Scanner(System.in);
    int choice,type;
    do
    {
      System.out.println(Colour.MAGENTA+"\t\t\t\t\n======= WELCOME TO N^2 AUCTION SYSTEM =======");
      System.out.println(Colour.BLUE+"\n---MENU---"+Colour.RESET);
      System.out.println("1. Login");
      System.out.println("2. Register");
      System.out.println("3. Exit portal");
      System.out.println (Colour.BLUE + "----------"+Colour.RESET);
      System.out.print(Colour.YELLOW+"Enter your choice: "+Colour.RESET);
      choice = in.nextInt();
      in.nextLine();
      System.out.println ("\n\n");

      switch (choice) {
        case 1: {
          String username, password;
          String name,dob,address,userType;
          System.out.println(Colour.CYAN+"\nLOGIN"+Colour.RESET);
          System.out.print("Enter username: ");
          username = in.nextLine();
          System.out.print("Enter password: ");
          password = in.nextLine();
          Login ob = new Login(username, password);
          if(ob.loginCheck()!=0){
            ob.readDetails();
            type=ob.getUser();
            userType=ob.getUserType();
            name=ob.getName();
            dob=ob.getDob();
            address=ob.getAddress();
            System.out.println(Colour.CYAN+"\nUSER DETAILS"+Colour.RESET);
            System.out.println("Name: "+name);
            System.out.println("DOB: "+dob);
            System.out.println("Address: "+address);
            System.out.println("User Type: "+userType);

            switch(type){
              case 1:{
                Buyer buyer = new Buyer(username, password,name,dob,address);
                buyerMenu(in, buyer);
                break;
              }
              case 2:{
                Seller seller = new Seller(username, password,name,dob,address);
                sellerMenu(in,seller);
                break;
              }
              case 0:{
                Admin admin = new Admin(username,password,name,dob,address);
                adminMenu(in, admin);
                break;
              } 
            }

          }
          else{
            System.out.println(Colour.RED+"Incorrect user details"+Colour.RESET);
          }

          break;
        }
        case 2: 
        {
          String user, pw = "";
          System.out.println(Colour.CYAN+"SIGNUP "+Colour.RESET);
          System.out.print("Enter username: ");
          user = in.nextLine();
          System.out.print("Enter password (minimum 8 characters with atleast one capital and digit): ");
          pw = in.nextLine();
          Signup ob = new Signup(user, pw);
          if (ob.checkIfUsernameExists() == 1) 
          {
            while (ob.checkIfUsernameExists() == 1) 
            {
              System.out.println(Colour.RED+"\nSorry, this username exists."+Colour.RESET);
              System.out.print("Please enter a new username: ");
              user = in.nextLine();
              System.out.print("Enter password (minimum 8 characters with atleast one capital and digit): ");
              pw = in.nextLine();
              ob = new Signup(user, pw);
            }
           ob = new Signup(user, pw);
          }
          ob = new Signup (user,pw);
          ob.getDetails();
          ob.enterInFile();
          break;
        }
        case 3: {
          System.out.println(Colour.GREEN+"\n\nTHANK YOU FOR VISITING US!!!\n"+Colour.RESET);
          System.exit(0);
          break;
        }
        default: {
          System.out.println(Colour.RED+"\nInvalid choice. Please enter a valid option\n\n"+Colour.RESET);
          break;
        }
      }

    }while (choice != 0);
  }

  private static void adminMenu(Scanner scanner, Admin admin) {
    while (true) {
      System.out.println(Colour.GREEN+"\n===== Admin Menu ====="+Colour.RESET);
      System.out.println("1. View Items");
      System.out.println("2. View Buyers");
      System.out.println("3. View Sellers");
      System.out.println("4. Close Auction");
      System.out.println("5. Logout");
      System.out.print(Colour.YELLOW+"Enter your choice: "+Colour.RESET);

      int choice = scanner.nextInt();
      scanner.nextLine();
      switch (choice) {
        case 1:
          admin.display();
          break;

        case 2:
          admin.displayBuyers();
          break;

        case 3:
          admin.displaySellers();
          break;

        case 4:
          System.out.print("Enter the item name to close the auction: ");
          String itemToClose = scanner.nextLine();
          admin.closeAuction(itemToClose);
          break;

        case 5:
          System.out.println("Logging out...\n");
          return;

        default:
          System.out.println(Colour.RED+"Invalid choice. Please enter a valid option."+Colour.RESET);
          break;
      }
    }
  }

  private static void buyerMenu(Scanner scanner, Buyer buyer) {
    while (true){
        System.out.println(Colour.GREEN+"\n===== Buyer Menu ====="+Colour.RESET);
        System.out.println("1. Display items");
        System.out.println("2. Search for items by category");
        System.out.println("3. Search for items with price range");
        System.out.println("4. Bid for item");
        System.out.println("5. Check messages");
        System.out.println("6. Logout");
        System.out.print(Colour.YELLOW+"Enter your choice: "+Colour.RESET);
        int choice = scanner.nextInt();
        scanner.nextLine();
        buyer.updateItems();

        switch (choice){
          case 1:{
            buyer.display();
            break;
          }
            case 2:{
              System.out.print("\n\nEnter Category to search by: ");
              String cat = scanner.nextLine();
              buyer.search(cat);
              break;
            }
            case 3:{
              int start,end;
              System.out.print("\n\nEnter the minimum price: ");
              start = scanner.nextInt();
              scanner.nextLine();
              System.out.print("Enter the maximum price: ");
              end = scanner.nextInt();
              scanner.nextLine();
              buyer.search(start,end);
              break;
            }
            
            case 4:{
              buyer.bidItem();
              break;
            }
          case 5:{
            buyer.checkMessage();
            break;
          }
          case 6:{
            System.out.println("Logging out...\n");
            return;
          }
          default:{
            System.out.println(Colour.RED+"Invalid choice. Please enter a valid option."+Colour.RESET);
            break;
          }  
        }
      }
  }

  private static void sellerMenu(Scanner scanner, Seller seller) {
    while (true) {
      System.out.println(Colour.GREEN+"\n===== Seller Menu ====="+Colour.RESET);
      System.out.println("1. Add Item");
      System.out.println("2. Remove Item");
      System.out.println("3. Edit Item");
      System.out.println("4. Check messages");
      System.out.println("5. Logout");
      System.out.print(Colour.YELLOW+"Enter your choice: "+Colour.RESET);

      int choice = scanner.nextInt();
      scanner.nextLine();
      switch (choice) {
        case 1:
          seller.addItem();
          break;
        case 2:
          System.out.print("Enter the item name to remove: ");
          String itemToRemove = scanner.nextLine();
          seller.removeItem(itemToRemove);
          break;
        case 3:
          System.out.print("Enter the item name to edit: ");
          String itemToEdit = scanner.nextLine();
          seller.editItem(itemToEdit);
          break;
        case 4:
          seller.checkMessage();
          break;
        case 5:
          System.out.println("Logging out...\n");
          return;
        default:
          System.out.println(Colour.RED+"Invalid choice. Please enter a valid option"+Colour.RESET);
          break;
      }
    }
  }
}

class Colour{
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
}
