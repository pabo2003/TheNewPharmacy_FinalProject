package lk.ijse.gdse.repository;


import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static boolean save(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier VALUES(?,?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, supplier.getSupplierId());
        pstm.setObject(2, supplier.getName());
        pstm.setObject(3, supplier.getDescription());
        pstm.setObject(4, supplier.getAddress());
        pstm.setObject(5, supplier.getTel());

        return pstm.executeUpdate() > 0;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT*FROM supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Supplier> supplierList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            String address = resultSet.getString(4);
            String tel = resultSet.getString(5);

            Supplier supplier = new Supplier(id, name, description, address, tel);
            supplierList.add(supplier);
        }
        return supplierList;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplierId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET name = ? , description = ? , address = ? , tel = ? WHERE supplierId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplier.getName());
        pstm.setObject(2, supplier.getDescription());
        pstm.setObject(3, supplier.getAddress());
        pstm.setObject(4, supplier.getTel());
        pstm.setObject(5, supplier.getSupplierId());

        return pstm.executeUpdate() > 0;
    }

    public static Supplier searchById(String id) throws SQLException {
        String sql = "SELECT*FROM supplier WHERE supplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String supplierId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String description = resultSet.getString(3);
            String address = resultSet.getString(4);
            String tel = resultSet.getString(5);

            Supplier supplier = new Supplier(supplierId, name, description, address, tel);

            return supplier;
        }
        return null;
    }

    public static List<String> getId() throws SQLException {
        String sql = "SELECT supplierId from supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        ArrayList<String> supplierList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            supplierList.add(id);
        }
        return supplierList;
    }


}