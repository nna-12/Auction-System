package AuctionSystem;

import Userlogin.Login;
import java.util.*;
import java.io.*;

class ItemNotFoundException extends Exception
  {
    public ItemNotFoundException(String s)
    {
      super(s);
    }
  }

public class Seller extends Login 
{
  public String item, category, seller;
  public int winbid, price;
  public int c = 0;
  public String arr[];
  Scanner in = new Scanner(System.in);
  private int user=2;
  private String userType="Seller";
  
  public Seller()
  {
    super();
  }
  
  public Seller(String un, String pw,String name,String dob,String address) 
  {
    super(un, pw);
    
    setName(name);
    setDob(dob);
    setAddress(address);
    
    try 
    {
      File file = new File("items.txt");
      Scanner sc = new Scanner(file);

      while (sc.hasNextLine()) 
      {
        c++;
        String line = sc.nextLine();
      }

      arr = new String[c];
      sc = new Scanner(file);
      int i = 0;
      while (sc.hasNextLine())
        arr[i++] = sc.nextLine();
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("File not found.");
    }
  }


  public void addItem() 
  {
    System.out.print("\nEnter Item Name: ");
    item = in.nextLine();
    System.out.print("Enter Category: ");
    category = in.nextLine();
    seller = super.getName();
    System.out.print("Enter Price: ");
    price = in.nextInt();
    winbid = 0;
    in.nextLine();

    String newItem = item + "," + category + "," + seller + "," + price + "," + winbid + ", ";

    c++;

    updateFile(newItem);
    System.out.println("Item added successfully.");
  }

  public void removeItem(String itemName) 
  {
    int index = findItemIndex(itemName);
    if (index != -1) 
    {
      try 
      {
        File inputFile = new File("items.txt");
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = arr[index];
        String currentLine;

        while ((currentLine = reader.readLine()) != null) 
        {
          if (currentLine.equals(lineToRemove)) 
          {
            continue;
          }
          writer.write(currentLine + System.getProperty("line.separator"));
        }

        writer.close();
        reader.close();

        boolean successful = tempFile.renameTo(inputFile);
        if (successful) 
        {
          System.out.println("Item removed successfully.");
        } 
        else 
        {
          System.out.println("Error renaming temp file.");
        }
      } 
      catch (IOException e) 
      {
        System.out.println("Error removing item.");
      }
    } 
    else 
    {
      try
        {
          throw new ItemNotFoundException ("\nSorry, this item cannot be found.\n");
        }
      catch (ItemNotFoundException e)
        {
          System.out.println (e.getMessage()+"\n");
        }
    }
  }

  public void editItem(String itemName) 
  {
    int found = 0;
    try 
    {
      File f = new File("items.txt");
      Scanner sc = new Scanner(f);
      List<String> lines = new ArrayList<>();
      while (sc.hasNextLine()) 
      {
        String line = sc.nextLine();
        String[] parts = line.split(",");
        if (parts[0].equalsIgnoreCase(itemName)) 
        {
          found = 1;
          System.out.print("Enter new item name: ");
          item = in.nextLine();
          System.out.print("Enter new category: ");
          category = in.nextLine();
          //System.out.print("Enter new seller: ");
          //seller = in.nextLine();
          seller = super.getName();
          System.out.print("Enter new price: ");
          price = in.nextInt();
          winbid = 0;
          in.nextLine(); // Consume the newline character

          line = item + "," + category + "," + seller + "," + price + "," + winbid + ", ";

        }
        lines.add(line);
      }
      sc.close();
      // write updated content back into file
      try 
      {
        FileWriter writer = new FileWriter(f);
        for (String updatedLine : lines) 
        {
          writer.write(updatedLine + "\n");
        }
        writer.close();
        if (found == 0)
          System.out.println("Sorry, this item cannot be found.");
        else
          System.out.println("Item updated successfully.");
        writer.close();
      } 
      catch (IOException e) 
      {
        System.out.println("Error updating item.");
      }
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("File not found.");
    }
  }

  private void updateArray() 
  {

    try 
    {
      File file = new File("items.txt");
      Scanner sc = new Scanner(file);

      int i = 0;
      while (sc.hasNextLine()) 
      {
        arr[i++] = sc.nextLine();
      }
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("File not found.");
    }
  }

  private void updateFile(String newitem) 
  {
    try 
    {
      FileWriter writer = new FileWriter("items.txt", true);
      writer.write(newitem + "\n");
      writer.close();
    } 
    catch (IOException e) 
    {
      System.out.println(e.getMessage());
    }
  }

  private int findItemIndex(String itemName) 
  {
    for (int i = 0; i < c; i++) 
    {
      String[] a = arr[i].split(",");
      if (a[0].equalsIgnoreCase(itemName))
        return i;
    }
    return -1;
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

        if (parts[7].equalsIgnoreCase(n)) 
        {
          if (parts[4].equals(" ")==false)
          {
          String msg = parts[4];
          System.out.println("Message: " + msg);
          printSaleCertificate (parts[5], parts[6], parts[7], parts[8], parts[9]);
          System.out.println("A copy of this will be sent to you via post.");
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
  
}
