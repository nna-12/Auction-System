package AuctionSystem;

import Userlogin.Login;
//items = item name, category, seller, price, winning bid, leading bidder
import java.util.*;
import java.io.*;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

class NoSuchItemException extends Exception
  {
    public NoSuchItemException(String message)
    {
      super(message);
    }
  }

public class Admin extends Login 
{
  private List<String> items;
  private int user=0;
  private String userType="Admin";
  public Admin()
  {
    super();
  }
  public Admin(String un, String pw,String name,String dob,String address) 
  {
    super(un, pw);
    
    setName(name);
    setDob(dob);
    setAddress(address);
    readItemsFromFile();
  }

  private void readItemsFromFile() 
  {
    try 
    {
      File f = new File("items.txt");
      Scanner sc = new Scanner(f);
      items = new ArrayList<>();

      while (sc.hasNextLine())
      {
        items.add(sc.nextLine());
      }
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("File not found.");
    }
  }

  private void writeItemsToFile() 
  {
    try 
    {
      File file = new File("items.txt");
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));

      for (String item : items) 
      {
        writer.write(item + System.getProperty("line.separator"));
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
    readItemsFromFile();
    System.out.println("Items:");
    System.out.println(String.format("%-7s", "S.NO") + String.format("%-40s", "ITEM NAME") + " "
        + String.format("%-15s", "CATEGORY") + " " + String.format("%-15s", "SELLER") + " "
        + String.format("%-10s", "PRICE") + " " + String.format("%-13s", "WINNING BID"));

    int i = 0;
    for (String item : items) 
    {
      String[] a = item.split(",");
      System.out.println(String.format("%-7s", Integer.toString(i + 1) + ".") + String.format("%-40s", a[0]) + " "
          + String.format("%-15s", a[1]) + " " + String.format("%-15s", a[2]) + " " + String.format("%-10s", a[3])
          + " "
          + String.format("%-13s", a[4]));
      i++;
    }
  }

  public void sendWinnerMessage(String win, String ft, String item, String b, String c, String d) 
  {
    try 
    {
      File f = new File("details.txt");
      Scanner sc = new Scanner(f);
      List<String> lines = new ArrayList<>();
      while (sc.hasNextLine()) {
        String line = sc.nextLine();
        String[] parts = line.split(",");
        if (parts[1].equalsIgnoreCase(win)) {
          if (Integer.parseInt(parts[0])==1)
            parts[4] = "" + "Congratulations you have won " + item + "!";
          else if (Integer.parseInt(parts[0])==2)
            parts[4] = "" + "Congratulations you have sold " + item + "!";
          parts[5] = ft;
          parts[6] = item;
          parts[7] = b;
          parts[8] = c;
          parts[9] = d;
          line = String.join(",", parts);
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

  public void closeAuction(String itemName) 
  {
    try 
    {
      File soldFile = new File("sold.txt");
      BufferedWriter soldWriter = new BufferedWriter(new FileWriter(soldFile, true));

      for (Iterator<String> iterator = items.iterator(); iterator.hasNext();) 
      {
        String currentItem = iterator.next();
        String[] a = currentItem.split(",");
        /*System.out.println ("\n");
        for (int i = 0; i < a.length; i++)
          {
            System.out.println (a[i]);
          }
        System.out.println ("\n");*/
        String currentItemName = a[0];

        if (currentItemName.equalsIgnoreCase(itemName)) {
          String winner = a[5];
          LocalDateTime myDateObj = LocalDateTime.now();
          DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
          String formattedDate = myDateObj.format(myFormatObj);
          sendWinnerMessage(winner,formattedDate,itemName,a[2],a[4],a[5]);
          /*for (int x=0;x<a.length;x++)
            System.out.print ("   " + a[x]);*/
          sendWinnerMessage(a[2],formattedDate,itemName,a[2],a[4],a[5]);
          soldWriter.write(currentItem + System.getProperty("line.separator"));
          iterator.remove();
          writeItemsToFile();
          soldWriter.close();
          System.out.println("Auction closed successfully.");
          
          System.out.println("\n\n\n");
          System.out.println("---------- CERTIFICATE OF SALE ----------");
          System.out.println ("\n" + formattedDate);
          System.out.println("\nITEM NAME: " + itemName);
          System.out.println("SELLER: " + a[2]);
          System.out.println("WINNING BID: " + a[4]);
          System.out.println("BUYER: " + a[5]);
          System.out.println("\n-----------------------------------------");
          System.out.println("\n\n\n");
          //writeInDetailsFile (formattedDate, itemName, a[2], a[5], a[4]);
          soldWriter.close();
          return;
        }
      }

      try
        {
          throw new NoSuchItemException ("\n\nNo such item is present in the auction.\n\n");
        }
      catch (NoSuchItemException e)
        {
          System.out.println(e.getMessage());
          return;
        }
      finally
        {
          soldWriter.close();
        }
    } catch (IOException e) {
      System.out.println("\n\nError closing auction.\n\n");
    }
  }

 public void displayBuyers() 
  {
    try 
    {
      File f = new File("details.txt");
      Scanner sc = new Scanner(f);

      System.out.println("\nBUYER LIST");
      System.out.println(String.format(String.format("%-20s", "Name") + " "+ String.format("%-12s", "DOB") + " " + String.format("%-15s", "Location") ));

      while (sc.hasNextLine()) 
      {
        String line = sc.nextLine();
        String[] parts = line.split(",");
        if (Integer.parseInt(parts[0]) == 1) 
        {
          System.out.println(String.format( String.format("%-20s", parts[1]) + " "
              + String.format("%-12s", parts[2]) + " " + String.format("%-15s", parts[3])) );
        }
      }
    }
    catch (FileNotFoundException e) 
    {
      System.out.println("File not found.");
    }
  }

  public void displaySellers()
  {
    try 
    {
      File f = new File("details.txt");
      Scanner sc = new Scanner(f);

      System.out.println("\nSELLER LIST");;
      System.out.println(String.format("%-20s", "Name") + " "
          + String.format("%-12s", "DOB") + " " + String.format("%-15s", "Address"));

      while (sc.hasNextLine()) 
      {
        String line = sc.nextLine();
        String[] parts = line.split(",");
        if (Integer.parseInt(parts[0]) == 2) 
        {
          System.out.println(String.format( String.format("%-20s", parts[1]) + " " + String.format("%-12s", parts[2]) + " " + String.format("%-15s", parts[3])));
        }
      }
    } 
    catch (FileNotFoundException e) 
    {
      System.out.println("File not found.");
    }
  }
  
}
