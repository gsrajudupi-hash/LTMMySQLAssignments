import javax.swing.plaf.nimbus.State;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

/*
Author : 
Date: 
Project : 
*/
public class EcommerceDAO {

    Connection connection;

    public EcommerceDAO(Connection connection){
        this.connection = connection;
    }
    public void getOrdersByFirstName(BufferedReader reader) throws SQLException, IOException {
        System.out.println("Enter first name ");
        String fname = reader.readLine();
        String sql = "select o.id from users u inner join orders o on u.id = o.user_id where u.first_name = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,fname);
        try(ResultSet rs = ps.executeQuery()){

            System.out.println("order ids for user with first name : " + fname  );
            while(rs.next()){
                System.out.println("Order id : " + rs.getString(1) );
            }
        }

    }

    public void addOrderForUser(BufferedReader br) throws Exception {


        System.out.print("Enter User Id: ");
        int userId = Integer.parseInt(br.readLine());

        System.out.print("Enter Shipping Address: ");
        String shippingAddress = br.readLine();

        System.out.print("Enter City: ");
        String city = br.readLine();

        System.out.print("Enter Total Bill: ");
        double price = Double.parseDouble(br.readLine());

        String sql =
                "INSERT INTO Orders ( " +
                        "user_id, " +
                        "shipping_address, " +
                        "city, " +
                        "total_bill, " +
                        "status " +
                        ") VALUES ( ?, ?, ?, ?, 'pending')";

        PreparedStatement ps =
                connection.prepareStatement(sql);

        ps.setInt(1, userId);
        ps.setString(2, shippingAddress);
        ps.setString(3, city);
        ps.setDouble(4, price);

        int changed = ps.executeUpdate();

        if (changed > 0) {
            System.out.println(
                    "Records Inserted : " + changed);
            return;
        }

        throw new SQLException(
                "Failed to insert record");
    }

    public void updatePriceForProduct(BufferedReader br) throws Exception {


        System.out.print("Enter Product ID: ");
        int productId = Integer.parseInt(br.readLine());

        System.out.print("Enter New Price: ");
        int newPrice = Integer.parseInt(br.readLine());

        String sql =
                "UPDATE products " +
                        "SET price = ? " +
                        "WHERE product_id = ?";

        PreparedStatement ps =
                connection.prepareStatement(sql);

        ps.setInt(1, newPrice);
        ps.setInt(2, productId);

        int changed = ps.executeUpdate();

        if (changed > 0) {
            System.out.println(
                    "Records Updated : " + changed);
            return;
        }

        throw new SQLException(
                "Failed to Update Records");
    }

    public void deleteUser(BufferedReader br)
            throws Exception {

        System.out.print("Enter User ID: ");
        int userId = Integer.parseInt(br.readLine());

        String sql =
                "DELETE FROM users WHERE id = ?";

        PreparedStatement ps =
                connection.prepareStatement(sql);

        ps.setInt(1, userId);

        int changed = ps.executeUpdate();

        if (changed > 0) {
            System.out.println(
                    "Records Deleted : " + changed);
            return;
        }

        throw new SQLException(
                "Failed to Delete Record");
    }

    public void findMoreSpendingUsers(BufferedReader br) throws SQLException, IOException {
        System.out.println("Enter threshold amount above which you want users : ");
        String maxBill = br.readLine().trim();
        String sql = "select sum(oi.unit_price) total_bill , CONCAT(u.first_name, ' ', u.last_name) from order_items oi join orders o on oi.order_id = o.id join users u on u.id = o.user_id group by oi.order_id having total_bill>" +  maxBill;
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(sql);
        while(set.next()){
            System.out.println("Total Bill : " + set.getString(1) + "$ for user " + set.getString(2));
        }
    }

    public void getOrdersForUser(BufferedReader br) throws Exception {


        System.out.print("Enter User Email: ");
        String email = br.readLine();

        CallableStatement cs =
                connection.prepareCall(
                        "{call getOrdersForUser(?)}");

        cs.setString(1, email);

        ResultSet rs = cs.executeQuery();

        while (rs.next()) {

            System.out.println(
                    "Order Id : " +
                            rs.getInt("id"));

            System.out.println(
                    "User Id : " +
                            rs.getInt("user_id"));

            System.out.println(
                    "Address : " +
                            rs.getString("shipping_address"));

            System.out.println(
                    "City : " +
                            rs.getString("city"));

            System.out.println(
                    "Bill : " +
                            rs.getDouble("total_bill"));

            System.out.println(
                    "Status : " +
                            rs.getString("status"));

            System.out.println(
                    "--------------------");
        }
    }

    public void totalSpend(BufferedReader br) throws Exception {

        System.out.print("Enter User Id: ");
        int userId =
                Integer.parseInt(br.readLine());

        String sql =
                "SELECT total_spend(?) AS total";

        PreparedStatement ps =
                connection.prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs =
                ps.executeQuery();

        if (rs.next()) {

            System.out.println(
                    "Total Spend : " +
                            rs.getDouble("total"));
        }
    }

    public void getAllProducts() throws SQLException {
        String sql = "Select * from products";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        System.out.println("|Product Name | Price |");
        while(rs.next()){
            System.out.println(" " + rs.getString(2) + " | " + rs.getString(3) + "$");
        }

    }

    public void demonstrateTransaction() throws Exception {

        try {

            connection.setAutoCommit(false);

            String insertOrder =
                    "INSERT INTO Orders " +
                            "(user_id, shipping_address, city, total_bill, status) " +
                            "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps1 =
                    connection.prepareStatement(insertOrder);

            ps1.setInt(1, 1);
            ps1.setString(2, "Mumbai Address");
            ps1.setString(3, "Mumbai");
            ps1.setDouble(4, 2500);
            ps1.setString(5, "pending");

            ps1.executeUpdate();

            String updateProduct =
                    "UPDATE products " +
                            "SET price = ? " +
                            "WHERE product_id = ?";

            PreparedStatement ps2 =
                    connection.prepareStatement(updateProduct);

            ps2.setDouble(1, 5000);

            // Invalid product id to force failure
            ps2.setInt(2, 99999);

            int updated = ps2.executeUpdate();

            if (updated == 0) {
                throw new SQLException(
                        "Product not found");
            }

            connection.commit();

            System.out.println(
                    "Transaction committed");

        } catch (Exception e) {

            connection.rollback();

            System.out.println(
                    "Transaction rolled back");

            throw e;

        } finally {

            connection.setAutoCommit(true);
        }
    }

    public void addUser(BufferedReader br) throws IOException, SQLException {
        System.out.println("Enter user email");
        String email = br.readLine();
        System.out.println("Enter first name");
        String fname = br.readLine();
        System.out.println("Enter last name");
        String lname = br.readLine();

        String sql = "Insert into users (first_name, last_name, email) values (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, fname);
        ps.setString(2, lname);
        ps.setString(3, email);
//        int key = -1;
        int changed = ps.executeUpdate();

        if (changed > 0) {
            System.out.println(
                    "User created successfully");
            return;
        }

        throw new SQLException(
                "Failed to create user");


    }
}
