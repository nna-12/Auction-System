package AuctionSystem;

import Userlogin.Login;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

class InvalidBidException extends Exception 
{
  public InvalidBidException(String message) 
  {
    super(message);
  }
}

class NoSuchCategoryException extends Exception
  {
    public NoSuchCategoryException(String message)
    {
      super(message);
    }
  }

class ItemNotAvlblException extends Exception
  {
    public ItemNotAvlblException(String message)
    {
      super(message);
    }
  }

public class Buyer extends Login 
{
  Login ob;
  public String item, category, seller;
  public int rp, winbid, price;
  public int c = 0;
  public String line;
  String arr[];
  String a[];
  Scanner sc;
  String ans;
  Scanner in = new Scanner(System.in);
  private int user=1;
  private String userType="Buyer";

  public Buyer()
  {
    super();
  }
  
  public Buyer(String un, String pw,String name,String dob,String address) 
  {
    super(un, pw);
    
    setName(name);
    setDob(dob);
    setAddress(address);
    
    ob = new Login(un, pw);
    this.item = this.category = this.seller = "";
    this.rp = this.winbid = this.price = 0;
    int i = 0;
    try 
    {
      File f = new File("items.txt");
      sc = new Scanner(f);
      while (sc.hasNextLine()) 
      {
        c++;
        line = sc.nextLine();
      }
      arr = new String[c];
      sc = new Scanner(f);
      while (sc.hasNextLine())
        arr[i++] = sc.nextLine();
      sc.close();
    } 
    catch (Exception e) 
    {
      System.out.println("File not found.");
    }
  }

  public void updateItems() 
  {
    try 
    {
      File f = new File("items.txt");
      sc = new Scanner(f);
      int i = 0;

      while (sc.hasNextLine()) 
      {
        arr[i++] = sc.nextLine();
      }
      c = i;
      sc.close();
    } 
    catch (Exception e) 
    {
      System.out.println("Error updating items.");
    }
  }

  private void updateFile() 
  {
    try 
    {
      File file = new File("items.txt");
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));

      for (int i = 0; i < c; i++) 
      {
        writer.write(arr[i] + System.getProperty("line.separator"));
      }
      writer.close();
    } 
    catch (IOException e) 
    {
      System.out.println("Error updating file.");
    }
  }

  public void display() 
  {
    System.out.println ("\n\n\n");

    try 
    {
      File f = new File("items.txt");
      Scanner sc = new Scanner(f);

      System.out.println(String.format("%-7s", "S.NO") + String.format("%-40s", "ITEM NAME") + " "
          + String.format("%-15s", "CATEGORY") + " " + String.format("%-15s", "SELLER") + " "
          + String.format("%-10s", "PRICE") + " " + String.format("%-13s", "WINNING BID"));
      System.out.println ("\n");

      int i = 0;
      while (sc.hasNextLine()) 
      {
        String[] a = sc.nextLine().split(",");
        System.out.println(String.format("%-7s", Integer.toString(i + 1) + ".") + String.format("%-40s", a[0]) + " "
            + String.format("%-15s", a[1]) + " " + String.format("%-15s", a[2]) + " " + String.format("%-10s", a[3])
            + " "
            + String.format("%-13s", a[4]));
        i++;
      }
      System.out.println ("\n");

      c = i;
      sc.close();
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("File not found.");
    }
  }

  public void updateItemPrice(String itemName, int newPrice, String n) 
  {
    try 
    {
      File f = new File("items.txt");
      sc = new Scanner(f);
      List<String> lines = new ArrayList<>();
      while (sc.hasNextLine()) 
      {
        String line = sc.nextLine();
        String[] parts = line.split(",");
        if (parts[0].equalsIgnoreCase(itemName)) 
        {
          if (newPrice > Integer.parseInt(parts[4]) && newPrice > Integer.parseInt(parts[3])) 
          {
            parts[4] = "" + newPrice; // Update the price
            //System.out.println ("\n\nname === " + super.getName()+ "\n\n");
            parts[5] = ""+super.getName(); // Update the bidders
            line = String.join(",", parts);
          }
        }
        lines.add(line);
      }
      sc.close();

      FileWriter writer = new FileWriter(f);
      for (String updatedLine : lines) 
      {
        writer.write(updatedLine + "\n");
      }
      writer.close();
    } 
    catch (IOException e)
    {
      System.out.println(e.getMessage());
    }
  }

  public void payment (String item, String seller, int bid)
  {
    Scanner sc = new Scanner(System.in);
    System.out.print("\nDo you want to proceed to payment? (yes/no) ");
    String ans = sc.nextLine();
    if (ans.equalsIgnoreCase("yes"))
    {
      System.out.print("\nEnter your mobile number for payment: ");
      String mobno = sc.nextLine();
      System.out.print("\nEnter the payment amount: ");
      double amount = sc.nextDouble();
      sc.nextLine();
      if (amount==(double)bid)
      {
        System.out.print("\nConfirm payment of Rs." + amount + " for item " + item + " by " + seller + "? ");
        String ch = in.nextLine();
        if (ch.equalsIgnoreCase("yes")) 
        {
          LocalDateTime myDateObj = LocalDateTime.now();
          DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
          String formattedDate = myDateObj.format(myFormatObj);
          System.out.println("\n\n---------- PAYMENT RECEIPT ----------");
          System.out.println("\n" + formattedDate);
          System.out.println("Item: " + item);
          System.out.println("Seller: " + seller);
          System.out.println("Payment Amount: Rs." + amount);          
          System.out.println ("\n-------------------------------------");
          System.out.println("\nPayment successful. Thank you for your purchase.");
        }
      }
      else
      {
        System.out.print("\nPayment amount incorrect. Please try again.");
        payment (item,seller,bid);
      }
    }
  }

  public void printSaleCertificate (String a, String b, String c, String d, String ee)
  {
    System.out.println("\n\n\n");
    System.out.println("---------- CERTIFICATE OF SALE ----------");
    System.out.println ("\n" + a);
    System.out.println("\nITEM NAME: " + b);
    System.out.println("SELLER: " + c);
    System.out.println("WINNING BID: " + d);
    System.out.println("BUYER: " + ee);
    System.out.println("\n-----------------------------------------");
    System.out.println("\n\n\n");
    payment (b,c,Integer.parseInt(d));
  }

  public void checkMessage() 
  {
    try 
    {
      File detailsFile = new File("details.txt");
      Scanner sc = new Scanner(detailsFile);
      //System.out.println(name);
      if (!sc.hasNextLine()) 
      {
        System.out.println("No lines to read in the file.");
        return;
      }
      while (sc.hasNextLine()) 
      {
        String line = sc.nextLine();
        String[] parts = line.split(",");

        String buyerName=getName();
        String n = parts[1];
        
        if (buyerName.equalsIgnoreCase(n)) 
        {
          if (parts[4].equals(" ")==false)
          {
          String msg = parts[4];
          System.out.println("Message: " + msg);
          printSaleCertificate (parts[5], parts[6], parts[7], parts[8], n);
          System.out.println("\nA copy of this will be sent to you via post.");
          sc.close();
          return;
          }
        }
      }

      System.out.println("\nNo new messages for you.\n");
      sc.close();
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("Details file not found.");
    }
  }

  public void search(int startPrice, int endPrice) 
  {
    System.out.println ("\n\n");
      int k = 1;
      boolean found = false;

      for (int i = 0; i < c; i++) 
      {
          a = arr[i].split(",");
          int itemPrice = Integer.parseInt(a[3]); // Convert the price to an integer

          if (startPrice <= itemPrice && itemPrice <= endPrice) 
          {
              if (!found) 
              {
                  System.out.println(
                          String.format("%-7s", "S.NO") + String.format("%-40s", "ITEM NAME") + " "
                                  + String.format("%-15s", "SELLER") + " " + String.format("%-10s", "PRICE") + " "
                                  + String.format("%-13s", "WINNING BID"));
              }

              System.out.println(String.format("%-7s", Integer.toString(k++) + ".") + String.format("%-40s", a[0]) + " "
                      + String.format("%-15s", a[2]) + " " + String.format("%-10s", a[3]) + " "
                      + String.format("%-13s", a[4]));

              found = true;
          }
      }
    

      if (!found) 
      {
          try 
          {
              throw new NoSuchCategoryException("\nNo items found within the specified price range.");
          } 
          catch (NoSuchCategoryException e) 
          {
              System.out.println(e.getMessage() + "\n");
          }
      }
  }
  
  public void search(String cat) 
  {
      int k = 1;
      boolean found = false;
      System.out.println ("\n\n");

      for (int i = 0; i < c; i++) 
      {
          a = arr[i].split(",");
          if (a[1].equalsIgnoreCase(cat)) 
          {
              if (!found) 
              {
                  System.out.println(
                          String.format("%-7s", "S.NO") + String.format("%-40s", "ITEM NAME") + " "
                                  + String.format("%-15s", "SELLER") + " " + String.format("%-10s", "PRICE") + " "
                                  + String.format("%-13s", "WINNING BID"));
              }

              System.out.println(String.format("%-7s", Integer.toString(k++) + ".") + String.format("%-40s", a[0]) + " "
                      + String.format("%-15s", a[2]) + " " + String.format("%-10s", a[3]) + " "
                      + String.format("%-13s", a[4]));

              found = true;
          }
      }

      if (!found) 
      {
          try 
          {
              throw new NoSuchCategoryException("\nSorry, this category doesn't exist.");
          } 
          catch (NoSuchCategoryException e) 
          {
              System.out.println(e.getMessage() + "\n");
          }
      }
  }


  public void bidItem() 
  {

    System.out.print("Enter name of item you want to bid for: ");
    this.item = in.nextLine();
    //String n = super.getName();
    //System.out.println(n);
    for (int i = 0; i < c; i++) {
      if ((arr[i].substring(0, arr[i].indexOf(","))).equalsIgnoreCase(this.item)) 
      {
        String[] itemline = arr[i].split(",");
        System.out.print("This item is up for auction. Do you want to proceed? (yes/no): ");
        ans = in.nextLine();
        if (ans.equalsIgnoreCase("yes")) 
        {
          System.out.print("Enter your bid: ");
          this.price = in.nextInt();
          try 
          {
            if (this.price <= Integer.parseInt(itemline[3]))
              throw new InvalidBidException("\nInvalid bid. Bid must be higher than reserve price.");
            else if (this.price <= Integer.parseInt(itemline[4]))
              throw new InvalidBidException("\nInvalid bid. Bid must be higher than current highest bid.");
          }

          catch (InvalidBidException e) 
          {
            System.out.println(e.getMessage());
            System.out.print("\nLast chance to enter new bid: ");
            this.price = in.nextInt();
          }

          in.nextLine();
          System.out.print("Confirm your bid of Rs." + this.price + "? (yes/no): ");
          ans = in.nextLine();
          if (ans.equalsIgnoreCase("yes")) 
          {
            //System.out.println ("name of buyer ===== " + super.getName());
            updateItemPrice(this.item, this.price, super.getName());
            return;
          }
        }
        return;
      }      
    }
    try
      {
        throw new ItemNotAvlblException ("\nSorry, this item is not available for auction.\n");
      }
    catch (ItemNotAvlblException e)
      {
        System.out.println (e.getMessage()+ "\n");
      }
  }
}
