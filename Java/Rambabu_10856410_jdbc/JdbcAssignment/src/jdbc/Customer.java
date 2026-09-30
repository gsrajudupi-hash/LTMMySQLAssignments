package jdbc;

public class Customer {

    private int customerId;
    private String customerName;
    private String email;
    private String city;

    public Customer() {
    }

    public Customer(int customerId,
            String customerName,
            String email,
            String city) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.city = city;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId
                + ", customerName=" + customerName
                + ", email=" + email
                + ", city=" + city + "]";
    }
}