package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, customer.getCuId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getNicNo());
        pstm.setObject(4, customer.getAddress());
        pstm.setObject(5, customer.getTel());

        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT*FROM customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String nicNo = resultSet.getString(3);
            String address = resultSet.getString(4);
            String tel = resultSet.getString(5);

            Customer customer = new Customer(id, name, nicNo, address, tel);
            cusList.add(customer);
        }
        return cusList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM customer WHERE cuId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name = ? , nicNo = ? , address = ? , tel = ? WHERE cuId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,customer.getName());
        pstm.setObject(2,customer.getNicNo());
        pstm.setObject(3,customer.getAddress());
        pstm.setObject(4,customer.getTel());
        pstm.setObject(5,customer.getCuId());

        return pstm.executeUpdate() > 0;
    }

    public static Customer searchByTel(String tel) throws SQLException {
        String sql = "SELECT*FROM customer WHERE tel = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,tel);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String cuId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String nicNo = resultSet.getString(3);
            String address = resultSet.getString(4);
            String tel1 = resultSet.getString(5);

            Customer customer = new Customer(cuId, name, nicNo, address, tel);

        return customer;
        }
        return null;
    }

    public static List<String> getTel() throws SQLException {
        String sql = "SELECT tel from customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> cusList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            cusList.add(id);
        }
        return cusList;
    }
}
