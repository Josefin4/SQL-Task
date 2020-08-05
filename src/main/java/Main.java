import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SQL_Task database = new SQL_Task();

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a customer ID: ");
        Customer customer;
        int customerID;

        if (scan.hasNextInt()) {
            customerID = scan.nextInt();
            customer = database.getCustomerByID(customerID);
        } else {
            System.out.println("Generating a random customer now...");
            customer = database.getRandomCustomerByID();
            customerID = Integer.parseInt(customer.getCustomerID());
        }
        ArrayList<String> collectGenres = database.getGenre(customerID);
            ArrayList<String> checkGenres = database.mostPopularGenre(collectGenres);
            database.displayCustomers(checkGenres, customer);

        }
    }





