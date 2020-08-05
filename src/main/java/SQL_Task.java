import java.sql.*;
import java.util.*;

public class SQL_Task {
    private static String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private static Connection conn = null;

    public static Customer getCustomerByID(int customerID) {
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);

            // Prepare Statement
            PreparedStatement ps =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName FROM customer WHERE CustomerId=?");
            ps.setInt(1, customerID);
            // Execute Statement
            ResultSet resultSet = ps.executeQuery();

            return new Customer(
                    resultSet.getString("CustomerId"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName")
            );

        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
        }
        return null;
    }

    public static Customer getRandomCustomerByID() {
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);

            // Prepare Statement
            PreparedStatement ps =
                    conn.prepareStatement("SELECT CustomerId, FirstName, LastName from Customer ORDER BY RANDOM() LIMIT 1");
            // Execute Statement
            ResultSet resultSet = ps.executeQuery();

            ArrayList<Customer> randomId = new ArrayList<>();
            while (resultSet.next()) {
                randomId.add(new Customer(
                        resultSet.getString("CustomerId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName")
                ));
            }

            int ran = new Random().nextInt(randomId.size());
            return randomId.get(ran);

        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
        }
        return null;
    }

    public static ArrayList<String> getGenre(int customerID) {
        try {
            // Open Connection
            conn = DriverManager.getConnection(URL);
            PreparedStatement checkInvoice =
                    conn.prepareStatement("SELECT Genre.Name FROM Genre " +
                            "INNER JOIN Track ON Track.GenreId = Genre.GenreId " +
                            "INNER JOIN InvoiceLine ON InvoiceLine.TrackId = Track.TrackId " +
                            "INNER JOIN Invoice ON InvoiceLine.InvoiceId = Invoice.InvoiceId " +
                            "WHERE CustomerId=?");
            checkInvoice.setInt(1, customerID);

            // Execute Statement
            ResultSet resultSet = checkInvoice.executeQuery();

            ArrayList<String> collectGenres = new ArrayList<>();
            while (resultSet.next()) {
                collectGenres.add(resultSet.getString("Name"));
            }
            return collectGenres;

        } catch (Exception ex) {
            System.out.println("Something went wrong...");
            System.out.println(ex.toString());
        } finally {
            try {
                // Close Connection
                conn.close();
            } catch (Exception ex) {
                System.out.println("Something went wrong while closing connection.");
                System.out.println(ex.toString());
            }
        }
        return null;
    }

    public static ArrayList<String> mostPopularGenre(ArrayList<String> allGenres) {
        Map<String, Integer> genresByPopularity = new HashMap<>();
        for (String genre : allGenres) {
            if (genresByPopularity.containsKey(genre))
                genresByPopularity.put(genre, genresByPopularity.get(genre) + 1);
            else
                genresByPopularity.put(genre, 1);
        }
        ArrayList<String> getGenres = new ArrayList<>();
        String largestGenre = "";
        int highestValue = 0;

        for (Map.Entry<String, Integer> entry : genresByPopularity.entrySet()) {
            String key = entry.getKey();
            Integer counter = entry.getValue();
            if (counter > highestValue) {
                highestValue = counter;
                largestGenre = key; }
            else if (counter == highestValue) {
                getGenres.add(key);
                getGenres.add(largestGenre);
            }
        }

        getGenres.add(largestGenre);
        return getGenres;
    }

    public static void displayCustomers(ArrayList<String> checkGenres, Customer customer) {
        System.out.println("Customer name: " + customer.getFirstName() + " " + customer.getLastName());
        if(checkGenres.size() == 1) {
            System.out.print("Favorite genre: ");
            for (String genre : checkGenres) {
                System.out.print(genre);
            }
        }
        else if(checkGenres.size() == 0) {
            System.out.println("This person does not have any favorite genres");
        }
        else if(checkGenres.size() > 1) {
            System.out.print("Favorite genres: ");
            for (int i = 0; i < checkGenres.size(); i++) {
                while (i < checkGenres.size()) {
                    System.out.print(checkGenres.get(i) + " & ");
                }
            }
        }
    }
}
