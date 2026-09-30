package jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;

public class EcommerceApplication {

    public static void main(String[] args) {

        try {

            Connection con =
                    ConnectionUtil.createConnection();

            String sql =
                    "insert into customers(customer_name,email,city,registration_date) values(?,?,?,curdate())";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, "Rambabu");
            ps.setString(2, "rambabu@gmail.com");
            ps.setString(3, "Hyderabad");

            int rows = ps.executeUpdate();

            System.out.println(rows + " Record Inserted");

            con.close();

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}