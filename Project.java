//package project;
import java.sql.*;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.text.SimpleDateFormat;

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
                            + "sAddress char(20), "
                            + "sPhoneNumber integer, "
                            + "sExperience integer, "
                            + "primary key(sID);";
                    
                    String transactionSql = "create table if not exists transaction ("
                            + "tID integer, "
                            + "pID integer, "
                            + "sID integer, "
                            + "tDate datetime, "
                            + "primary key(tID)), "
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
                        System.out.println("Done! Tables are created");
                    } catch(Exception e) {
                        System.out.println(e);
                    }
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
    
    public static void salesperson() {
        do{
            System.out.println("\n-----Operations for salesperson menu-----");
            System.out.println("1. Search for parts");
            System.out.println("2. Sell a part");
            System.out.println("3. Return to the main menu");
            System.out.print("Enter Your Choice: ");
            
            int option = input.nextInt();
            input.nextLine();
            //int option = Integer.parseInt(input.nextLine());
            
            switch(option) {
                case 1:
                    break;  
                case 2:
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
            // update
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