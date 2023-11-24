import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Project {
    public static Scanner input = new Scanner(System.in);

    public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db38?autoReconnect=true&useSSL=false";
    public static String dbUsername = "Group38";
    public static String dbPassword = "CSCI3170";
    public static Connection connectToMySQL(){
            Connection con = null;
            try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
            } catch (ClassNotFoundException e){
                    System.out.println("[Error]: Java MySQL DB Driver not found!!");
                    System.exit(0);
            } catch (SQLException e){
                    System.out.println(e);
            }
            return con;
    }
        
    public static void main(String[] args) {
        do{
            System.out.println("Welcome to sales system!\n");
            System.out.println("-----Main menu-----");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");
            System.out.print("Enter Your Choice: ");
            int option = input.nextInt();
            
            switch(option) {
                case 1:
                    administrator();
                    break;
                case 2:
                    salesperson();
                    break;
                case 3:
                    manager();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("[ERROR] Invalid Input");
            }
        } while(true);
    }
    
    public static void administrator() {
        do{
            System.out.println("\n-----Operations for administrator menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Create all tables");
            System.out.println("2. Delete all tables");
            System.out.println("3. Load from datafiles");
            System.out.println("4. Show content of a table");
            System.out.println("5. Return to the main menu");
            System.out.print("Enter Your Choice: ");
            int option = input.nextInt();
            
            switch(option) {
                case 1:
                    String categorySql = "create table if not exists part_category ("
                            + "cID integer, "
                            + "cName char(20), "
                            + "primary key(cID));";
                    
                    String manufacturerSql = "create table if not exists manufacturer ("
                            + "mID integer, "
                            + "mName char(20), "
                            + "mAddress char(50), "
                            + "mPhoneNumber integer, "
                            + "primary key(mID));";
                    
                    String partSql = "create table if not exists computer_part ("
                            + "pID integer, "
                            + "pName char(20), "
                            + "pPrice integer, "
                            + "mID integer, "
                            + "cID integer,"
                            + "pWarrantyPeriod integer, "
                            + "pAvailableQuantity integer, "
                            + "primary key(pID), "
                            + "foreign key(cID) references part_category(cID) on update cascade on delete cascade, "
                            + "foreign key(mID) references manufacturer(mID) on update cascade on delete cascade);";
                    
                    String salespersonSql = "create table if not exists salesperson ("
                            + "sID integer, "
                            + "sName char(20), "
                            + "sAddress char(50), "
                            + "sPhoneNumber integer, "
                            + "sExperience integer, "
                            + "primary key(sID));";
                    
                    String transactionSql = "create table if not exists transaction ("
                            + "tID integer, "
                            + "pID integer, "
                            + "sID integer, "
                            + "tDate date, "
                            + "primary key(tID), "
                            + "foreign key(sID) references salesperson(sID) on update cascade on delete cascade, "
                            + "foreign key(pID) references computer_part(pID) on update cascade on delete cascade);";
                    
                    try {
                        System.out.print("Processing...");
                        Connection mysql = connectToMySQL();
                        Statement sql = mysql.createStatement();
                        sql.executeUpdate(categorySql);
                        sql.executeUpdate(manufacturerSql);
                        sql.executeUpdate(partSql);
                        sql.executeUpdate(salespersonSql);
                        sql.executeUpdate(transactionSql);
                        System.out.println("Done! Database is initialized");
                    } catch(Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    String disableFK  = "set foreign_key_checks = 0;";
                    String dropCategory = "drop table if exists part_category";
                    String dropManufacturer = "drop table if exists manufacturer";
                    String dropPart = "drop table if exists computer_part";
                    String dropSalesperson = "drop table if exists salesperson";
                    String dropTransaction = "drop table if exists transaction";
                    String enableFK = "set foreign_key_checks = 1";
                    try {
                        System.out.print("Processing...");
                        Connection mysql = connectToMySQL();
                        Statement sql = mysql.createStatement();
                        sql.executeUpdate(disableFK);
                        sql.executeUpdate(dropCategory);
                        sql.executeUpdate(dropManufacturer);
                        sql.executeUpdate(dropPart);
                        sql.executeUpdate(dropSalesperson);
                        sql.executeUpdate(dropTransaction);
                        sql.executeUpdate(enableFK);
                        System.out.print("Done! Database is removed\n");
                    } catch(Exception e) {
                        System.out.print(e);
                    }
                    break;
                case 3:
                    System.out.print("Type in the Source Data Folder Path: ");
                    String path = input.next();
                    input.nextLine();
                    System.out.print("Processing...");
                          
                    String[][] categoryInfo = new String [10000][2];
                    String[][] manufacturerInfo = new String [10000][4];
                    String[][] partInfo = new String [10000][7];
                    String[][] salespersonInfo = new String [10000][5];
                    String[][] transactionInfo = new String [10000][4];

                    try {
                        File file = new File(path + "/category.txt");
                        BufferedReader br = new BufferedReader(new FileReader(file)); 
                        String st;
                        int count = 0;
                        while ((st = br.readLine()) != null) {
                            categoryInfo[count] = st.split("\t");
                            count++;
                        }
                        br.close();
                    } catch(Exception e) {
                        System.out.print(e);
                    }

                    try {
                        File file = new File(path + "/manufacturer.txt");
                        BufferedReader br = new BufferedReader(new FileReader(file)); 
                        String st;
                        int count = 0;
                        while ((st = br.readLine()) != null) {
                            manufacturerInfo[count] = st.split("\t");
                            count++;
                        }
                        br.close();
                    } catch(Exception e) {
                        System.out.print(e);
                    }

                    try {
                        File file = new File(path + "/part.txt");
                        BufferedReader br = new BufferedReader(new FileReader(file)); 
                        String st;
                        int count = 0;
                        while ((st = br.readLine()) != null) {
                            partInfo[count] = st.split("\t");
                            count++;
                        }
                        br.close();
                    } catch(Exception e) {
                        System.out.print(e);
                    }

                    try {
                        File file = new File(path + "/salesperson.txt");
                        BufferedReader br = new BufferedReader(new FileReader(file)); 
                        String st;
                        int count = 0;
                        while ((st = br.readLine()) != null) {
                            salespersonInfo[count] = st.split("\t");
                            count++;
                        }
                        br.close();
                    } catch(Exception e) {
                        System.out.print(e);
                    }

                    try {
                        File file = new File(path + "/transaction.txt");
                        BufferedReader br = new BufferedReader(new FileReader(file)); 
                        String st;
                        int count = 0;
                        while ((st = br.readLine()) != null) {
                            transactionInfo[count] = st.split("\t");
                            count++;
                        }
                        br.close();
                    } catch(Exception e) {
                        System.out.print(e);
                    }

                    String categoryInsert = "insert into part_category values(?, ?)";
                    String manufacturerInsert = "insert into manufacturer values(?, ?, ?, ?)";
                    String partInsert = "insert into computer_part values(?, ?, ?, ?, ?, ?, ?)";
                    String salespersonInsert = "insert into salesperson values(?, ?, ?, ?, ?)";
                    String transactionInsert = "insert into transaction values(?, ?, ?, ?)";

                    try{
                        Connection mysql = connectToMySQL();
                        PreparedStatement categoryPS = mysql.prepareStatement(categoryInsert);
                        PreparedStatement manufacturerPS = mysql.prepareStatement(manufacturerInsert);
                        PreparedStatement partPS = mysql.prepareStatement(partInsert);
                        PreparedStatement salespersonPS = mysql.prepareStatement(salespersonInsert);
                        PreparedStatement transactionPS = mysql.prepareStatement(transactionInsert);
                        
                        for(int i = 0; categoryInfo[i][0] != null; i++) {
                            categoryPS.setInt(1, Integer.parseInt(categoryInfo[i][0]));
                            categoryPS.setString(2, categoryInfo[i][1]);
                            categoryPS.executeUpdate();
                        }
                        
                        for(int i = 0; manufacturerInfo[i][0] != null; i++) {
                            manufacturerPS.setInt(1, Integer.parseInt(manufacturerInfo[i][0]));
                            manufacturerPS.setString(2, manufacturerInfo[i][1]);
                            manufacturerPS.setString(3, manufacturerInfo[i][2]);
                            manufacturerPS.setInt(4, Integer.parseInt(manufacturerInfo[i][3]));
                            manufacturerPS.executeUpdate();
                        }

                        for(int i = 0; partInfo[i][0] != null; i++) {
                            partPS.setInt(1, Integer.parseInt(partInfo[i][0]));
                            partPS.setString(2, partInfo[i][1]);
                            partPS.setInt(3, Integer.parseInt(partInfo[i][2]));
                            partPS.setInt(4, Integer.parseInt(partInfo[i][3]));
                            partPS.setInt(5, Integer.parseInt(partInfo[i][4]));
                            partPS.setInt(6, Integer.parseInt(partInfo[i][5]));
                            partPS.setInt(7, Integer.parseInt(partInfo[i][6]));
                            partPS.executeUpdate();
                        }
                        
                        for(int i = 0; salespersonInfo[i][0] != null; i++) {
                            salespersonPS.setInt(1, Integer.parseInt(salespersonInfo[i][0]));
                            salespersonPS.setString(2, salespersonInfo[i][1]);
                            salespersonPS.setString(3, salespersonInfo[i][2]);
                            salespersonPS.setInt(4, Integer.parseInt(salespersonInfo[i][3]));
                            salespersonPS.setInt(5, Integer.parseInt(salespersonInfo[i][4]));
                            salespersonPS.executeUpdate();
                        }

                        for(int i = 0; transactionInfo[i][0] != null; i++) {
                            SimpleDateFormat inputDate = new SimpleDateFormat ("dd/MM/yyyy");
                            transactionPS.setInt(1, Integer.parseInt(transactionInfo[i][0]));
                            transactionPS.setInt(2, Integer.parseInt(transactionInfo[i][1]));
                            transactionPS.setInt(3, Integer.parseInt(transactionInfo[i][2]));
                            Date date = inputDate.parse(transactionInfo[i][3]);
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            transactionPS.setDate(4,sqlDate);
                            transactionPS.executeUpdate();
                        }
                        System.out.println("Data is inputted to the database!");
                    } catch(Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 4:
                    System.out.print("Which table would you like to show: ");
                    String table = input.next();
                    input.nextLine();
                    

                    switch(table) {
                    case "category":
                        try {
                            System.out.println("Content of table category: ");
                            String categoryContent = "select * from part_category;";
                            Connection mysql = connectToMySQL();
                            Statement sql = mysql.createStatement();
                            ResultSet categoryRS = sql.executeQuery(categoryContent);
                            ResultSetMetaData metaData = categoryRS.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            System.out.print("| ");
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(metaData.getColumnName(i) + " | ");
                            }
                            System.out.println();
                            while (categoryRS.next()) {
                                System.out.print("| ");
                                for (int i = 1; i <= columnCount; i++) {
                                    Object value = categoryRS.getObject(i);
                                    System.out.print(value + " | ");
                                }
                                System.out.println();
                            }
                        } catch(Exception e) {
                            System.out.println(e);
                        }
                        break;  
                    case "manufacturer":
                        try {
                            System.out.println("Content of table manufacturer: ");
                            String manufacturerContent = "select * from manufacturer;";
                            Connection mysql = connectToMySQL();
                            Statement sql = mysql.createStatement();
                            ResultSet manufacturerRS = sql.executeQuery(manufacturerContent);
                            ResultSetMetaData metaData = manufacturerRS.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            System.out.print("| ");
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(metaData.getColumnName(i) + " | ");
                            }
                            System.out.println();
                            while (manufacturerRS.next()) {
                                System.out.print("| ");
                                for (int i = 1; i <= columnCount; i++) {
                                    Object value = manufacturerRS.getObject(i);
                                    System.out.print(value + " | ");
                                }
                                System.out.println();
                            }
                        } catch(Exception e) {
                            System.out.println(e);
                        }
                        break;
                    case "part":
                        try {
                            System.out.println("Content of table part: ");
                            String partContent = "select * from computer_part;";
                            Connection mysql = connectToMySQL();
                            Statement sql = mysql.createStatement();
                            ResultSet partRS = sql.executeQuery(partContent);
                            ResultSetMetaData metaData = partRS.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            System.out.print("| ");
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(metaData.getColumnName(i) + " | ");
                            }
                            System.out.println();
                            while (partRS.next()) {
                                System.out.print("| ");
                                for (int i = 1; i <= columnCount; i++) {
                                    Object value = partRS.getObject(i);
                                    System.out.print(value + " | ");
                                }
                                System.out.println();
                            }
                        }  catch(Exception e) {
                            System.out.println(e);
                        }
                        break;
                    case "salesperson":
                        try {
                            System.out.println("Content of table salesperson: ");
                            String salespersonContent = "select * from salesperson;";
                            Connection mysql = connectToMySQL();
                            Statement sql = mysql.createStatement();
                            ResultSet salespersonRS = sql.executeQuery(salespersonContent);
                            ResultSetMetaData metaData = salespersonRS.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            System.out.print("| ");
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(metaData.getColumnName(i) + " | ");
                            }
                            System.out.println();
                            while (salespersonRS.next()) {
                                System.out.print("| ");
                                for (int i = 1; i <= columnCount; i++) {
                                    Object value = salespersonRS.getObject(i);
                                    System.out.print(value + " | ");
                                }
                                System.out.println();
                            }
                        }  catch(Exception e) {
                            System.out.println(e);
                        }
                        break;
                    case "transaction":
                        try {
                            System.out.println("Content of table transaction: ");
                            String transactionContent = "select * from transaction;";
                            Connection mysql = connectToMySQL();
                            Statement sql = mysql.createStatement();
                            ResultSet transactionRS = sql.executeQuery(transactionContent);
                            ResultSetMetaData metaData = transactionRS.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            System.out.print("| ");
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(metaData.getColumnName(i) + " | ");
                            }
                            System.out.println();
                            while (transactionRS.next()) {
                                System.out.print("| ");
                                for (int i = 1; i <= columnCount; i++) {
                                    if(i == 4) {
                                        SimpleDateFormat outputDate = new SimpleDateFormat ("dd/MM/yyyy");
                                        Date date = transactionRS.getDate(i);
                                        String value = outputDate.format(date);
                                        System.out.print(value + " | ");
                                        continue;
                                    }
                                    Object value = transactionRS.getObject(i);
                                    System.out.print(value + " | ");
                                }
                                System.out.println();
                            }
                        }  catch(Exception e) {
                            System.out.println(e);
                        }
                        break;
                    default:
                        System.out.println("[ERROR] Invalid Input");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("[ERROR] Invalid Input");
            }
        } while(true);
    }
    
    public static void salesperson() {
        do{
            System.out.println("\n-----Operations for salesperson menu-----");
            System.out.println("1. Search for parts");
            System.out.println("2. Sell a part");
            System.out.println("3. Return to the main menu");
            System.out.print("Enter Your Choice: ");
            
            int option = input.nextInt();
            input.nextLine();
            
            switch(option) {
                case 1:
                    System.out.println("Choose the Search criterion: ");
                    System.out.println("1. Part Name");
                    System.out.println("2. Manufacturer Name");
                    System.out.print("Choose the Search criterion: ");
                    int criterion = input.nextInt();
                    input.nextLine();
                    System.out.print("Type in the Search Keyword: ");
                    String keyword = input.nextLine();
                    System.out.println("Choose ordering: ");
                    System.out.println("1. By price, ascending order ");
                    System.out.println("2. By price, descending order");
                    System.out.print("Choose ordering: ");
                    int ordering = input.nextInt();
                    input.nextLine();
                    String order = "";
                    if (ordering == 1) order = "asc";
                    else if (ordering == 2) order = "desc";
                    String searchPart = "";
                    if (criterion == 1) searchPart = "select p.pID, p.pName, m.mName, c.cName, p.pAvailableQuantity, p.pWarrantyPeriod, p.pPrice from computer_part p, manufacturer m, part_category c where p.pName = '" + keyword + "' and m.mID = p.mID and p.cID = c.cID order by p.pPrice " + order + ";";
                    else if (criterion == 2) searchPart = "select p.pID, p.pName, m.mName, c.cName, p.pAvailableQuantity, p.pWarrantyPeriod, p.pPrice from computer_part p, manufacturer m, part_category c where m.mName = '" + keyword + "' and m.mID = p.mID and p.cID = c.cID order by p.pPrice " + order + ";";
                    try {
                        Connection mysql = connectToMySQL();
                        Statement sql = mysql.createStatement();
                        ResultSet partRS;
                        partRS = sql.executeQuery(searchPart);
                        ResultSetMetaData metaData = partRS.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
                        while (partRS.next()) {
                            System.out.print("| ");
                            for (int i = 1; i <= columnCount; i++) {
                                Object value = partRS.getObject(i);
                                System.out.print(value + " | ");
                            }
                            System.out.println();
                        }
                        System.out.println("End of Query");
                    } catch(Exception e) {
                        System.out.println(e);
                    }
                    break;  
                case 2:
                    System.out.print("Enter The Part ID: ");
                    int partID = input.nextInt();
                    input.nextLine();
                    System.out.print("Enter The Salesperson ID: ");
                    int salespersonID = input.nextInt();
                    input.nextLine();
                    String getPartData = "select p.pAvailableQuantity, p.pName from computer_part p where p.pID = " + partID + ";";
                    String updateQuantity = "update computer_part set pAvailableQuantity = pAvailableQuantity - 1 where pID = " + partID + ";";
                    String counttID = "select count(*) from transaction";
                    LocalDate localDate = LocalDate.now();
                    try {
                        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                        Connection mysql = connectToMySQL();
                        Statement sql = mysql.createStatement();
                        ResultSet partDataRS = sql.executeQuery(getPartData);
                        partDataRS.next();
                        int partQuantity = partDataRS.getInt(1);
                        String partName = partDataRS.getString(2);
                        if(partQuantity == 0) {
                            System.out.println("[ERROR] Product: " + partName + "(id: " + partID + ") is out of stock");
                            break;
                        }
                        sql.executeUpdate(updateQuantity);  
                        ResultSet transactionRS = sql.executeQuery(counttID);
                        transactionRS.next();
                        int transactionCount = transactionRS.getInt(1) + 1;
                        String transactionInsert = "insert into transaction values(?, ?, ?, ?)";
                        PreparedStatement transactionPS = mysql.prepareStatement(transactionInsert);
                        transactionPS.setInt(1, transactionCount);
                        transactionPS.setInt(2, partID);
                        transactionPS.setInt(3, salespersonID);
                        transactionPS.setDate(4,sqlDate);
                        transactionPS.executeUpdate();
                        System.out.println("Product: " + partName + "(id: " + partID + ") Remaining Quantity: " + partQuantity);
                    } catch(Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("[ERROR] Invalid Input");
            }
        } while(true);
    }
    
    public static void manager() {
        do{
            System.out.println("\n-----Operations for manager menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. List all salesperson");
            System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience");
            System.out.println("3. Show the total sales value of each manufacturer");
            System.out.println("4. Show the N most popular part");
            System.out.println("5. Return to the main menu");
            System.out.print("Enter Your Choice: ");
            
            int option = input.nextInt();
            input.nextLine();
            
            
            switch(option) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    return;
                default:
                    System.out.println("[ERROR] Invalid Input");
            }
        } while(true);
    }
    
}