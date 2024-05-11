package lk.ijse.gdse.repository;

import lk.ijse.gdse.DB.DbConnection;
import lk.ijse.gdse.model.Item;
import lk.ijse.gdse.model.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static boolean save(Item item) throws SQLException {
        String sql = "INSERT INTO item VALUES(?,?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, item.getItemId());
        pstm.setObject(2, item.getDescription());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(4, item.getQtyOnHand());
        pstm.setObject(5, item.getStockId());

        return pstm.executeUpdate() > 0;
    }


    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT*FROM item";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        ArrayList<Item> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String description = resultSet.getString(2);
            double unitePrice = Double.parseDouble(resultSet.getString(3));
            int qtyOnHand = Integer.parseInt(resultSet.getString(4));
            String stockId = resultSet.getString(5);

            Item item = new Item(id, description, unitePrice,qtyOnHand, stockId);
            itemList.add(item);
        }
        return itemList;
    }


    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM item WHERE itemId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE item SET description = ? , qtyOnHand = ? , unitPrice = ? , stockId = ? WHERE itemId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,item.getDescription());
        pstm.setObject(2,item.getQtyOnHand());
        pstm.setObject(3,item.getUnitPrice());
        pstm.setObject(4,item.getStockId());
        pstm.setObject(5,item.getItemId());

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT itemId FROM item";
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()){
            String id = resultSet.getString(1);
            idList.add(id);

        }
        return idList;
    }

    public static Item searchById(String id) throws SQLException {
        String sql = "SELECT * FROM item WHERE itemId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            String itemId = rs.getString(1);
            String description = rs.getString(2);
            double unitPrice = rs.getDouble(3);
            int qtyOnHand = rs.getInt(4);
            String stockId = rs.getString(5);

            Item item = new Item(itemId, description, unitPrice, qtyOnHand, stockId);
            return item;
        }
        return null;
    }


    public static boolean update1(List<OrderDetails> odList) throws SQLException {
        for (OrderDetails od : odList) {
            boolean isUpdateQty = updateQty(od.getItemId(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String Id, int qty) throws SQLException {
        String sql = "UPDATE item SET qtyOnHand = qtyOnHand - ? WHERE itemId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, Id);

        return pstm.executeUpdate() > 0;
    }
}
