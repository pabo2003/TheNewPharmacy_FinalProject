package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES(?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, payment.getPayId());
        pstm.setObject(2, payment.getMethod());
        pstm.setObject(3, payment.getAmount());
        pstm.setObject(4, payment.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payment";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Payment> paymentsList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String method = resultSet.getString(2);
            double amount = Double.parseDouble(resultSet.getString(3));
            Date date = resultSet.getDate("4");


            Payment payment = new Payment(id, method, amount, date);
            paymentsList.add(payment);
        }
        return paymentsList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM payment WHERE payId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET method = ? , amount = ? , date ? WHERE payId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,payment.getMethod());
        pstm.setObject(3,payment.getAmount());
        pstm.setObject(2,payment.getDate());
        pstm.setObject(4,payment.getPayId());

        return pstm.executeUpdate() > 0;
    }

    public static Payment searchById(String id) throws SQLException {
        String sql = "SELECT*FROM payment WHERE payId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String payId = resultSet.getString(1);
            String method = resultSet.getString(2);
            double amount = Double.parseDouble(resultSet.getString(3));
            Date date = resultSet.getDate(4);

            Payment payment = new Payment(payId, method, amount, date);

            return payment;
        }
        return null;
    }

    public static List<String> getId() throws SQLException {
        String sql = "SELECT payId from payment";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> paymentList = new ArrayList<>();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            paymentList.add(id);
        }
        return paymentList;
    }
    public static String getPayCurrentId() throws SQLException {
        String sql = "SELECT payId FROM payment ORDER BY payId DESC LIMIT 1";
        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("payId");
            }
        }
        return null;
    }
}
