#AUCTION SYSTEM
The Auction System is a Java-based command-line application that simulates an auction environment. It includes three user types: Admin, Buyer, and Seller. The system allows users to add items, bid on items, and perform various auction-related activities.

##TABLE OF CONTENTS
1. Installation
2. Usage
3. Features
3. File Structure

##INSTALLATION
1. Ensure you have Java installed on your system.
2. Download the AuctionSystem Java files.
3. Download the Userlogin Java files
4. Download the AuctionDemo.java file

Compile the Java files using the following command:
javac AuctionSystem/*.java Userlogin/*.java AuctionDemo.java
Run the program using the following command:
java AuctionDemo

##USAGE
Upon running the program, you will be prompted to log in as Admin, Buyer, or Seller. For new users, you can sign up with a unique username and a password meeting certain criteria.
Once logged onto the system, the user can perform various actions, according to their usert type such as adding items, bidding, and managing auctions.

##FEATURES
User Types:
Admin: Manages the overall system, closes auctions, generate messages to buyers & sellers and displays buyer/seller/item lists.
Buyer: Views available items, search for items based on price and category, bids on items and receives purchase details.
Seller: Adds, removes, or edits items and receives sale details once a bidding is closed.

Auction System:
Sellers put up items to sale that are bidded by Buyers. The Admin can close the auctions and generate the sale reports.

##FILE STRUCTURE
AuctionSystem folder contains Java files organized by user types such as Admin.java, Buyer.java and Seller.java. It also contains Signup.java.
Userlogin folder contains Login.java.
AuctionDemo.java contains the main method of the program.

Files Used:
items.txt: Stores information about items in the auction.
sold.txt: Stores the specifics of the items sold in the auction.
details.txt: Stores details about buyers, sellers, admin and also their transactions.
logininfo.txt: Stores the username and password of the users
