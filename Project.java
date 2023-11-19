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
        //Scanner input = new Scanner(System.in);
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
        //Scanner input = new Scanner(System.in);
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
        //Scanner input = new Scanner(System.in);
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