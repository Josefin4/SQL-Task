public class Customer {
    private String CustomerId;
    public String FirstName;
    public String LastName;

    public Customer(String customerID, String FirstName, String LastName) {
        this.CustomerId = customerID;
        this.FirstName = FirstName;
        this.LastName = LastName;
    }

    public String getCustomerID() {

        return CustomerId;
    }

    public String getFirstName() {

        return FirstName;
    }
    public void setFirstName(String firstName) {

        FirstName = firstName;
    }

    public String getLastName() {

        return LastName;
    }

    public void setLastName(String lastName) {

        LastName = lastName;
    }

}

